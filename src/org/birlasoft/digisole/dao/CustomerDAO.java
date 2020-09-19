package org.birlasoft.digisole.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.birlasoft.digisole.exception.CustomerDatabaseException;
import com.birlasoft.digisole.exception.DatabaseConnectionException;
import com.birlasoft.digisole.model.CustomerVO;
import com.birlasoft.digisole.model.LoginVO;
import com.birlasoft.digisole.util.DBConnection;

/**
 * @author Melvin Mathew
 * 
 * This class performs all the data access operations related to customer object.
 * 
 * Consists of functions:
 * getCID(): It fetches the last customer ID found in the customer table. This ID is return for
 * further process to the CustomerClient class.
 * 
 * addCustomer(CustomerVO): It will take the customer object and add the details of a new customer
 * in the database for further retrievals.
 * 
 * fetchCustomer(LoginVO): It will fetch the entire customer details from the database by giving
 * the login details. Used when a customer wants to login to the system.
 * 
 * getAllCustomers(): An exclusive function to the admin user to monitor the users in the system
 * and fetch their details as a List object of CustomerVO class.
 *
 */
public class CustomerDAO {

	/**
	 * @return
	 * @throws CustomerDatabaseException
	 */
	public static String getCID() throws CustomerDatabaseException {
		
		Connection con = null;
		String cid = "";
		String query = "SELECT CID FROM CUSTOMER ORDER BY CID";
		
		try {
			con = DBConnection.getConnection();
			
			PreparedStatement pstat = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet result = pstat.executeQuery();
			
			if(result.last()){
				
				cid = result.getString(1);
			}
			
		}  catch (DatabaseConnectionException e) {
			
			System.out.println();
			System.out.println("Problem in fetching connection:" + e);
			throw new CustomerDatabaseException(e);
		}  catch (SQLException e) {
			
			System.out.println();
			System.out.println("Error in retrieving ID:" + e);
			
		} finally {
			
			DBConnection.closeConnection(con);
		}
		
		if(cid.equals("")) {
			cid = "C-00";
		}
		return cid;	
	}
	
	/**
	 * @param cust
	 * @return
	 */
	public static int addCustomer(CustomerVO cust) {
		int result=0;
		Connection con =null;
		String query = "INSERT INTO CUSTOMER VALUES(?, ?, ?, ?, ?, ?)";
		
		try {
			
			con = DBConnection.getConnection();
			PreparedStatement pStat = con.prepareStatement(query);
			
		    pStat.setString(1, cust.getCid());
		    pStat.setString(2, cust.getName());
		    pStat.setInt(3, cust.getAge());
		    pStat.setString(4, cust.getAddress());
		    pStat.setString(5, cust.getCity());
		    pStat.setString(6, cust.getPhNo());
		    
		    result = pStat.executeUpdate();
		
		}  catch(DatabaseConnectionException e) {
			
			System.out.println();
			System.out.println("Problem in fetching connection:" + e);
		}  catch(SQLException e) {
			
			System.out.println();
			System.out.println("Error adding customer data:" + e);
		} finally {
			
			DBConnection.closeConnection(con);
		}
		
		return result;
	}
	
	/**
	 * @param login
	 * @return
	 * @throws CustomerDatabaseException
	 */
	public static CustomerVO fetchCustomer(LoginVO login) throws CustomerDatabaseException {
		
		CustomerVO customer = new CustomerVO();
		Connection con = null;
		String query = "SELECT * FROM CUSTOMER WHERE CID=(SELECT CID FROM LOGIN WHERE LOGIN=? AND PASSWORD=?)";
		
		try {
			
			con = DBConnection.getConnection();
			PreparedStatement pstat= con.prepareStatement(query);
			pstat.setString(1, login.getLoginID().trim());
			pstat.setString(2, login.getPassword().trim());
			
			ResultSet custResult = pstat.executeQuery();
			if(custResult.next()) {
				
				customer.setCid(custResult.getString(1));
				customer.setName(custResult.getString(2));
				customer.setAge(custResult.getInt(3));
				customer.setAddress(custResult.getString(4));
				customer.setCity(custResult.getString(5));
				customer.setPhNo(custResult.getString(6));
			}
			
		} catch (DatabaseConnectionException e) {
			
			System.out.println("Error fetching Customer details:" + e);
			throw new CustomerDatabaseException(e);
		} catch (SQLException e) {
			
			System.out.println("Error in Customer DB:" + e);
		} finally {
			DBConnection.closeConnection(con);
		}
		
		return customer;
	}

	/**
	 * @return
	 * @throws CustomerDatabaseException
	 */
	public static List<CustomerVO> getAllCustomers() throws CustomerDatabaseException {
		
		List<CustomerVO> custList = new ArrayList<CustomerVO>();
		Connection con = null;
		String query = "SELECT * FROM CUSTOMER";
			
		try {
			
			con = DBConnection.getConnection();
			
			PreparedStatement pstat = con.prepareStatement(query);
			ResultSet custResult = pstat.executeQuery();
			
			while(custResult.next()) {
				
				CustomerVO customer = new CustomerVO();
				customer.setCid(custResult.getString(1));
				customer.setName(custResult.getString(2));
				customer.setAge(custResult.getInt(3));
				customer.setAddress(custResult.getString(4));
				customer.setCity(custResult.getString(5));
				customer.setPhNo(custResult.getString(6));
				custList.add(customer);
			}
			
		} catch (DatabaseConnectionException e) {
			
			System.out.println("Error in fetching connection:" + e);
			throw new CustomerDatabaseException(e);
		} catch (SQLException e) {
			
			System.out.println("Error in fetching laptop details:" + e);
		} finally {
			DBConnection.closeConnection(con);
		}
		
		return custList;
	}
	
}
