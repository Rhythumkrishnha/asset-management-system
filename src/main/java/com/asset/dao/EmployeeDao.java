package com.asset.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.asset.MySQLConnection.JDBCConnector;
import com.asset.model.Employee;

public class EmployeeDao {
	private Connection con;
	public EmployeeDao() {
		try {
			con = JDBCConnector.initializeConnection();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public int createEmployee(Employee employee) throws ClassNotFoundException, SQLException {
		int result = 0;
		
		try {
			PreparedStatement st = con.prepareStatement("insert into employee(name) values(?)");
			st.setString(1, employee.getName());
			result = st.executeUpdate();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public JSONArray getAllEmployees() throws ClassNotFoundException, SQLException{
		JSONArray jsonArray = new JSONArray();
		
		try {
			PreparedStatement st = con.prepareStatement("select * from employee");
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				JSONObject json = new JSONObject();
				json.put("id", rs.getInt("id"));
				json.put("name", rs.getString("name"));
				
				jsonArray.put(json);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return jsonArray;
	}
	
	public JSONArray getAmountSpentForEmployee(int employee_id) throws ClassNotFoundException, SQLException {
		JSONArray jsonArray = new JSONArray();
		
		try {
			PreparedStatement st = con.prepareStatement("select sum(cost) as amount_spent\n"
					+ "from license\n"
					+ "where device_id in (\n"
					+ "	select id\n"
					+ "    from device\n"
					+ "    where employee_id in (\n"
					+ "		select id\n"
					+ "        from employee\n"
					+ "        where id = ?\n"
					+ "	)\n"
					+ ");");
			st.setInt(1, employee_id);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				JSONObject json = new JSONObject();
				json.put("amount spent", rs.getInt("amount_spent"));
				
				jsonArray.put(json);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return jsonArray;
	}
}
