package com.jensen.Controller.managers;

import java.sql.*;

public class ConnectionManager {

	private Connection connection;
	private static final String URL = "jdbc:mysql://localhost:3306/company", USER = "root", PW = "";
	
	public ConnectionManager() {
		init();
	}
	
	public void init() {
		try {
			connection = DriverManager.getConnection(URL, USER, PW);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		return this.connection;
	}
	
}
