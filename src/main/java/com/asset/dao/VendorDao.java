package com.asset.dao;

import com.asset.MySQLConnection.JDBCConnector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.asset.model.Vendor;

public class VendorDao {
	private Connection con;
	public VendorDao() {
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
	
	public int createVendor(Vendor vendor) throws ClassNotFoundException, SQLException {
		int result = 0;
		
		try {
			PreparedStatement st = con.prepareStatement("insert into vendor(name) values(?)");
			st.setString(1, vendor.getName());
			result = st.executeUpdate();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public JSONArray getAllVendors() throws Exception, SQLException {
			JSONArray jsonArray = new JSONArray();
			
			try {
				PreparedStatement st = con.prepareStatement("select * from vendor");
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
	
	public JSONArray getAmountSpentOnVendor(int vendor_id) throws ClassNotFoundException, SQLException {
		JSONArray jsonArray = new JSONArray();
		
		try {
			PreparedStatement st = con.prepareStatement("select sum(cost) as amount\n"
					+ "from license\n"
					+ "where software_id in (\n"
					+ "	select id\n"
					+ "    from software \n"
					+ "    where vendor_id = ?\n"
					+ ");");
			st.setInt(1, vendor_id);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				JSONObject json = new JSONObject();
				json.put("amount spent on license", rs.getInt("amount"));
				
				jsonArray.put(json);
			}
			
			st = con.prepareStatement("select cost from software where id = ?");
			st.setInt(1, vendor_id);
			rs = st.executeQuery();
			
			while(rs.next()) {
				JSONObject json = new JSONObject();
				json.put("amount spent on purchase", rs.getInt("cost"));
				
				jsonArray.put(json);
			}
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return jsonArray;

	}
}
