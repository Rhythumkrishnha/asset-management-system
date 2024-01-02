package com.asset.MySQLConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestConnection {
	public static void main(String[] args) throws Exception{
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/zoho", "root", "Krish@2002");
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("select * from vendor");
		
		while(rs.next()) {
			System.out.println(rs.getInt("id") + rs.getString("name"));
		}
	}
}
