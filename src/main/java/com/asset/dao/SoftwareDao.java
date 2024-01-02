package com.asset.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.asset.MySQLConnection.*;
import com.asset.model.Software;

public class SoftwareDao {
	private Connection con = null;
	public SoftwareDao() {
		try {
			con = JDBCConnector.initializeConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int createSoftware(Software software) throws ClassNotFoundException, SQLException{
		int result = 0;
		
		try {
			PreparedStatement st = con.prepareStatement("insert into software(name, vendor_id, cost) values(?,?,?)");
			st.setString(1, software.getName());
			st.setInt(2, software.getVendor_id());
			st.setInt(3, software.getCost());
			
			result = st.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public JSONArray getAllSoftwares() throws ClassNotFoundException, SQLException{
		JSONArray jsonArray = new JSONArray();
		
		try {
			PreparedStatement st = con.prepareStatement("select * from software");
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				JSONObject json = new JSONObject();
				json.put("id", rs.getInt("id"));
				json.put("name", rs.getString("name"));
				json.put("vendor_id", rs.getInt("vendor_id"));
				json.put("cost", rs.getInt("cost"));
				
				jsonArray.put(json);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return jsonArray;
	}
	
	public JSONArray getAmountSpentForSoftware(int software_id) throws ClassNotFoundException, SQLException {
		JSONArray jsonArray = new JSONArray();
		
		try {
			PreparedStatement st = con.prepareStatement("select sum(cost) as cost \n"
					+ "from license \n"
					+ "where software_id = ?;");
			st.setInt(1, software_id);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				JSONObject json = new JSONObject();
				json.put("license installation cost", rs.getInt("cost"));
				
				jsonArray.put(json);
			}
			
			st = con.prepareStatement("select cost from software where id = ?");
			st.setInt(1, software_id);
			rs = st.executeQuery();
			
			while(rs.next()) {
				JSONObject json = new JSONObject();
				json.put("purchase cost", rs.getInt("cost"));
				
				jsonArray.put(json);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return jsonArray;
	}
	
	public JSONArray getNumberOfInstalltionOfSoftware(int vendor_id) throws ClassNotFoundException, SQLException {
		JSONArray jsonArray = new JSONArray();
		
		try {
			PreparedStatement st = con.prepareStatement("select license.software_id, software.name as name, count(license.id) as count\n"
					+ "from license join software\n"
					+ "on license.software_id = software.id\n"
					+ "where software_id in (\n"
					+ "	select id\n"
					+ "    from software \n"
					+ "    where vendor_id = ?\n"
					+ ")\n"
					+ "group by software_id;");
			st.setInt(1, vendor_id);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				JSONObject json = new JSONObject();
				json.put("software name", rs.getString("name"));
				json.put("installtion count", rs.getInt("count"));
				
				jsonArray.put(json);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return jsonArray;
	}
}
