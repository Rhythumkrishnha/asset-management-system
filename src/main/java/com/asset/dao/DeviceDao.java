package com.asset.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.asset.MySQLConnection.JDBCConnector;
import com.asset.model.Device;

public class DeviceDao {
	private Connection con;
	public DeviceDao() {
		try {
			con = JDBCConnector.initializeConnection();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public int createDevice(Device device) throws ClassNotFoundException, SQLException{
		int result = 0;
		try {
			PreparedStatement st = con.prepareStatement("insert into device(employee_id, category, variant) values(?,?,?)");
			st.setInt(1, device.getEmployee_id());
			st.setString(2, device.getCategory());
			st.setString(3,  device.getVariant());
			
			result = st.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public JSONArray getAllDevices() throws ClassNotFoundException, SQLException {
		JSONArray jsonArray = new JSONArray();
		
		try {
			PreparedStatement st = con.prepareStatement("select * from device");
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				JSONObject json = new JSONObject();
				json.put("id", rs.getInt("id"));
				json.put("employee_id", rs.getInt("employee_id"));
				json.put("category", rs.getString("category"));
				json.put("variant", rs.getString("variant"));
				
				jsonArray.put(json);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return jsonArray;
	}
}
