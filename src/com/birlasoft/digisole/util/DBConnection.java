package com.birlasoft.digisole.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.birlasoft.digisole.exception.DatabaseConnectionException;

/**
 * @author Melvin Mathew
 * 
 * It is a resource class which consists of methods that will load the driver and get a valid
 * connection for every DAO in the project.
 * 
 * The functions are:
 * loadDrivers(): It loads the drivers needed for the DB connections.
 * 
 * getConnection(): Returns a new connection which the loaded drivers to perform DAO operations.
 * 
 * closeConnection(Connection): Takes a Connection object as parameters and close the active 
 * connection.
 *
 */
public class DBConnection {

	private static Connection con;
	
	/**
	 * @throws DatabaseConnectionException
	 */
	public static void loadDrivers() throws DatabaseConnectionException {
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			
			throw new DatabaseConnectionException("Problem in loading drivers");
		}
		
	}
	
	/**
	 * @return
	 * @throws DatabaseConnectionException
	 */
	public static Connection getConnection() throws DatabaseConnectionException {
		
		String url = "jdbc:oracle:thin:@localhost:1521/orclpdb";
		
		try {
			con = DriverManager.getConnection(url, "Digisole", "proj123");
		} catch (SQLException e) {
			
			throw new DatabaseConnectionException("Problem in loading connection");
		}
		
		return con;
	}
	
	/**
	 * @param con
	 */
	public static void closeConnection(Connection con) {
		
		try {
			if(con != null) {
				
				con.close();
			}
			
		} catch (SQLException e) {
			
			System.out.println("Error in closing connection");
		}
		
	}
	
}
