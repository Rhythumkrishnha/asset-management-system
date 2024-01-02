package com.asset.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.asset.MySQLConnection.JDBCConnector;
import com.asset.model.License;

public class LicenseDao {
	private Connection con;
	public LicenseDao() {
		try {
			con = JDBCConnector.initializeConnection();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public int createLicense(License license) throws ClassNotFoundException, SQLException {
		int result = 0;
		try {
			PreparedStatement st = con.prepareStatement("insert into license(device_id, cost, software_id) values(?,?,?)");
			st.setInt(1, license.getDevice_id());
			st.setInt(2, license.getCost());
			st.setInt(3, license.getSoftware_id());
			
			result = st.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public JSONArray getAllLicenses() throws ClassNotFoundException, SQLException {
		JSONArray jsonArray = new JSONArray();
		
		try {
			PreparedStatement st = con.prepareStatement("select * from license");
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				JSONObject json = new JSONObject();
				json.put("id", rs.getInt("id"));
				json.put("device_id", rs.getInt("device_id"));
				json.put("cost", rs.getInt("cost"));
				json.put("expiry_date", rs.getString("expiry_date"));
				json.put("software_id", rs.getInt("software_id"));
				json.put("installed_date", rs.getString("installed_date"));
				
				jsonArray.put(json);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return jsonArray;
	}
	
	public JSONArray getLicenseBySoftwareId(int software_id) throws ClassNotFoundException, SQLException {
		JSONArray jsonArray = new JSONArray();
		
		try {
			PreparedStatement st = con.prepareStatement("select * from license where software_id = ?");
			st.setInt(1, software_id);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				JSONObject json = new JSONObject();
				json.put("id", rs.getInt("id"));
				json.put("device_id", rs.getInt("device_id"));
				json.put("cost", rs.getInt("cost"));
				json.put("expiry_date", rs.getString("expiry_date"));
				json.put("installed_date", rs.getString("installed_date"));
				
				jsonArray.put(json);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return jsonArray;
	}
	
	public JSONArray getSoftwareInstalledOnDevice(int device_id) throws ClassNotFoundException, SQLException {
		JSONArray jsonArray = new JSONArray();
		
		try {
			PreparedStatement st = con.prepareStatement("select name\n"
					+ "from software\n"
					+ "where id in (\n"
					+ "	select software_id\n"
					+ "	from license\n"
					+ "	where device_id = ?\n"
					+ ");");
			st.setInt(1, device_id);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				JSONObject json = new JSONObject();
				json.put("software_name", rs.getString("name"));
				
				jsonArray.put(json);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return jsonArray;
	}
	
	public JSONArray getSoftwareInstalledForEmployee(int employee_id) throws ClassNotFoundException, SQLException {
		JSONArray jsonArray = new JSONArray();
		
		try {
			PreparedStatement st = con.prepareStatement("select name\n"
					+ "from software\n"
					+ "where id in (\n"
					+ "	select software_id\n"
					+ "	from license\n"
					+ "	where device_id in (\n"
					+ "		select id\n"
					+ "		from device \n"
					+ "		where employee_id = ?)\n"
					+ ");");
			st.setInt(1, employee_id);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				JSONObject json = new JSONObject();
				json.put("software_name", rs.getString("name"));
				
				jsonArray.put(json);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return jsonArray;
	}
}
