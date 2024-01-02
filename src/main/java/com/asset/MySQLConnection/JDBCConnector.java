package com.asset.MySQLConnection;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;

public class JDBCConnector {
	public static Connection initializeConnection() throws Exception, SQLException {
		Connection con;
		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/zoho","root","Krish@2002");
		
		return con;
	}
}
