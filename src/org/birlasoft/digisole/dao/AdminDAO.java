package org.birlasoft.digisole.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.birlasoft.digisole.exception.AdminDatabaseException;
import com.birlasoft.digisole.exception.DatabaseConnectionException;
import com.birlasoft.digisole.model.AdminVO;
import com.birlasoft.digisole.util.DBConnection;

/**
 * @author Melvin Mathew
 * 
 * Consists of functions:
 * 
 * adminCheck(AdminVO): Verifies if the admin's login and password is correct and flags 1, if not
 * correct then flags 0. The flag is returned back to the CustomerClient.
 * 
 * fetchAdmin(AdminVO): For the given login and password, the function return AdminVO object which
 * consists of the entire admin details to CustomerClient class.
 * 
 * checkPassword(AdminVO): Called by AdminMain class to perform the operation of changing password
 * in which the password is checked before changing it.
 * 
 * addNewPassword(AdminVO): Called by AdminMain class to change the password with a new password.
 *
 */
public class AdminDAO {

	/**
	 * @param login
	 * @return
	 * @throws AdminDatabaseException
	 */
	public static int adminCheck(AdminVO login) throws AdminDatabaseException {
		
		int result = 0;
		Connection con = null;
		String query = "SELECT LOGINID, PASSWORD FROM ADMIN WHERE LOGINID=? AND PASSWORD=?";
		
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
			throw new AdminDatabaseException(e);
		} catch(SQLException e) {
			
			System.out.println();
			System.out.println("Problem in connection:"+e);
		} finally {
			
			DBConnection.closeConnection(con);
		}
		
		return result;
	}
	
	/**
	 * @param login
	 * @return
	 * @throws AdminDatabaseException
	 */
	public static AdminVO fetchAdmin(AdminVO login) throws AdminDatabaseException {
		
		AdminVO admin = new AdminVO();
		Connection con = null;
		String query = "SELECT * FROM ADMIN WHERE LOGINID=? AND PASSWORD=?";
		
		try {
			
			con = DBConnection.getConnection();
			PreparedStatement pstat= con.prepareStatement(query);
			pstat.setString(1, login.getLoginID().trim());
			pstat.setString(2, login.getPassword().trim());
			
			ResultSet adminResult = pstat.executeQuery();
			if(adminResult.next()) {
				
				admin.setAdminID(adminResult.getString(1));
				admin.setName(adminResult.getString(2));
				admin.setRole(adminResult.getString(3));
				admin.setLoginID(adminResult.getString(4));
				admin.setPassword(adminResult.getString(5));
			}
			
		} catch (DatabaseConnectionException e) {
			
			System.out.println("Error fetching Customer details:" + e);
			throw new AdminDatabaseException(e);
		} catch (SQLException e) {
			
			System.out.println("Error in Customer DB:" + e);
		} finally {
			DBConnection.closeConnection(con);
		}
		
		return admin;
	}

	/**
	 * @param admin
	 * @param pwd
	 * @return
	 * @throws AdminDatabaseException
	 */
	public static boolean checkPassword(AdminVO admin, String pwd) throws AdminDatabaseException {
		
		Connection con = null;
		String query = "SELECT PASSWORD FROM ADMIN WHERE ADMINID=?";
		
		try {
			
			con = DBConnection.getConnection();
			
			PreparedStatement pStat = con.prepareStatement(query);
		    pStat.setString(1, admin.getAdminID());
		                  
		    ResultSet adminResult= pStat.executeQuery();
		                
		    if(adminResult.next()) {
		    	
		    	String password = adminResult.getString(1);
		    	if(password.equals(pwd)) {
		    		return true;
		    	}
		    }  
		} catch(DatabaseConnectionException e) {
			
			System.out.println();
			System.out.println("Problem in fetching connection:" + e);
			throw new AdminDatabaseException(e);
		} catch(SQLException e) {
			
			System.out.println();
			System.out.println("Problem in connection:"+e);
		} finally {
			
			DBConnection.closeConnection(con);
		}
		
		return false;
	}
	
	/**
	 * @param admin
	 * @return
	 * @throws AdminDatabaseException
	 */
	public static int addNewPassword(AdminVO admin) throws AdminDatabaseException {
		
		int adminResult = 0;
		Connection con = null;
		String query = "UPDATE ADMIN SET PASSWORD=? WHERE ADMINID=?";
		
		try {
			
			con = DBConnection.getConnection();
			
			PreparedStatement pStat = con.prepareStatement(query);
			pStat.setString(1, admin.getPassword());
		    pStat.setString(2, admin.getAdminID());
		                  
		    adminResult= pStat.executeUpdate();
		                  
		} catch(DatabaseConnectionException e) {
			
			System.out.println();
			System.out.println("Problem in fetching connection:" + e);
			throw new AdminDatabaseException(e);
		} catch(SQLException e) {
			
			System.out.println();
			System.out.println("Problem in connection:"+e);
		} finally {
			
			DBConnection.closeConnection(con);
		}
		
		return adminResult;
		
	}
	
}
