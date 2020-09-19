package org.birlasoft.digisole.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.birlasoft.digisole.exception.DatabaseConnectionException;
import com.birlasoft.digisole.exception.LoginDatabaseException;
import com.birlasoft.digisole.model.LoginVO;
import com.birlasoft.digisole.util.DBConnection;

/**
 * @author Melvin Mathew
 * 
 * This class takes care of the functionalities related to login of the customer.
 * Functionality:
 * addLogin(LoginVO): Adds the login details of the customer to the database while registering
 * as new user. 
 * loginCheck(LoginVO): Checks if the given login details of the customer is present in the
 * database.
 * 
 */
public class LoginDAO {

	/**
	 * @param login
	 * @return
	 * @throws LoginDatabaseException
	 */
	public static int addLogin(LoginVO login) throws LoginDatabaseException {
		int result=0;
		Connection con =null;
		String query = "INSERT INTO LOGIN VALUES(?, ?, ?)";
		
		try {
			
			con = DBConnection.getConnection();
			PreparedStatement pStat = con.prepareStatement(query);
			
		    pStat.setString(1, login.getCid());
		    pStat.setString(2, login.getLoginID());
		    pStat.setString(3, login.getPassword());
		   
		    result = pStat.executeUpdate();
		
		}  catch(DatabaseConnectionException e) {
			
			System.out.println();
			System.out.println("Problem in fetching connection:" + e);
			throw new LoginDatabaseException(e);
		}  catch(SQLException e) {
			
			System.out.println();
			System.out.println("Error uploading login data:" + e);
		} finally {
			
			DBConnection.closeConnection(con);
		}
		
		return result;
	}
	
	/**
	 * @param login
	 * @return
	 * @throws LoginDatabaseException
	 */
	public static int loginCheck(LoginVO login) throws LoginDatabaseException {
		
		int result = 0;
		Connection con = null;
		String query = "SELECT LOGIN, PASSWORD FROM LOGIN WHERE LOGIN=? AND PASSWORD=?";
		
		try {
			
			con = DBConnection.getConnection();
			
			PreparedStatement pStat = con.prepareStatement(query);
		    pStat.setString(1, login.getLoginID().trim());
		    pStat.setString(2, login.getPassword().trim());
		                  
		    ResultSet loginResult= pStat.executeQuery();
		                
		    if(loginResult.next()) {
		    	result = 1;
		    }
		         
		} catch(DatabaseConnectionException e) {
			
			System.out.println();
			System.out.println("Problem in fetching connection:" + e);
			throw new LoginDatabaseException(e);
		} catch(SQLException e) {
			
			System.out.println();
			System.out.println("Problem in connection:"+e);
		} finally {
			
			DBConnection.closeConnection(con);
		}
		
		return result;
	}
	
}
