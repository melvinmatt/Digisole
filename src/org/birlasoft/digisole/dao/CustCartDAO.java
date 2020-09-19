package org.birlasoft.digisole.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.birlasoft.digisole.exception.CustCartDatabaseException;
import com.birlasoft.digisole.exception.DatabaseConnectionException;
import com.birlasoft.digisole.model.CustCartVO;
import com.birlasoft.digisole.model.CustomerVO;
import com.birlasoft.digisole.model.ProductVO;
import com.birlasoft.digisole.model.SpecificationVO;
import com.birlasoft.digisole.util.DBConnection;

/**
 * @author Melvin Mathew
 * 
 * This class handles the data flow in the CustomerCart table with functionalities of adding order
 * details and viewing the cart.
 * 
 * Consists of functions:
 * addCart(SpecificationVO, CustomerVO, String): It adds the order details into the Cart table if
 * the user chooses to buy the product.
 * 
 * getOrderID(): It fetches the last order ID from the table. It is used by the addCart() function
 * to add the order ID for each order.
 * 
 * viewCart(CustomerVO): It fetches all the order made by the specific customer. Returns a ProductVO
 * object for further processing.
 *
 */
public class CustCartDAO {

	/**
	 * @param spec
	 * @param customer
	 * @param status
	 * @return
	 * @throws CustCartDatabaseException
	 */
	public static int addCart(SpecificationVO spec, CustomerVO customer, String status) throws CustCartDatabaseException {
		
		int result = 0;
		Connection con = null;
		String query = "INSERT INTO CUSTCART VALUES(? ,?, ?, ?, ?)";
		
		try {
			con= DBConnection.getConnection();
			
			int oid = Integer.parseInt(CustCartDAO.getOrderID().substring(2));
			String orderID = "";
			
			if(oid < 9) {
				orderID = "O-0" + ++oid;
			} else {
				orderID = "O-" + ++oid;
			}
			
			PreparedStatement pstat = con.prepareStatement(query);
		    pstat.setString(1, orderID);
		    pstat.setString(2, customer.getCid());
		    pstat.setString(3, spec.getProdID());
		    pstat.setDouble(4, spec.getCost());
		    pstat.setString(5, status);
		    result = pstat.executeUpdate();
		
		} catch(DatabaseConnectionException e) {
			System.out.println("problem in connection:"+e);
			throw new CustCartDatabaseException(e);
		} catch(SQLException e) {
			System.out.println("problem in connection:"+e);
		} finally {
			DBConnection.closeConnection(con);
		}
		
		return result;
		
	}
	
	/**
	 * @return
	 * @throws CustCartDatabaseException
	 */
	public static String getOrderID() throws CustCartDatabaseException {
		
		Connection con = null;
		String oid = "";
		String query = "SELECT ORDERID FROM CUSTCART";
		
		try {
			con = DBConnection.getConnection();
			
			PreparedStatement pstat = con.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet result = pstat.executeQuery();
			
			if(result.last()){
				
				oid = result.getString(1);
			}
			
		}  catch (DatabaseConnectionException e) {
			
			System.out.println("Problem in fetching connection:" + e);
			throw new CustCartDatabaseException(e);
		}  catch (SQLException e) {
			
			System.out.println("Error in retrieving ID:" + e);
			
		} finally {
			
			DBConnection.closeConnection(con);
		}
		
		if(oid.equals("")) {
			oid = "C-00";
		}
		
		return oid;	
	}
	
	/**
	 * @param customer
	 * @return
	 * @throws CustCartDatabaseException
	 */
	public static List<ProductVO> viewCart(CustomerVO customer) throws CustCartDatabaseException {
		
		List<ProductVO> cartList = new ArrayList<ProductVO>();
		Connection con = null;
		String query = "SELECT DISTINCT O.ORDERID, P.PRODID, P.NAME, P.COST, P.RATING, P.TYPE FROM PRODUCT P, CUSTCART O WHERE O.PRODID=P.PRODID AND CID=? AND STATUS='Cart'";
		
		try {
			con= DBConnection.getConnection();
		
			PreparedStatement pstat = con.prepareStatement(query);
		    pstat.setString(1, customer.getCid());
		    ResultSet result = pstat.executeQuery();
		    
		    while(result.next()) {
		    	
		    	ProductVO cart = new CustCartVO();
		    	cart.setOrderID(result.getString(1));
		    	cart.setProdID(result.getString(2));
		    	cart.setName(result.getString(3));
		    	cart.setCost(result.getDouble(4));
		    	cart.setRating(result.getFloat(5));
		    	cart.setType(result.getString(6));
		    	cartList.add(cart);
		    }
		   
		} catch(DatabaseConnectionException e) {
			System.out.println("problem in connection:"+e);
			throw new CustCartDatabaseException(e);
		} catch(SQLException e) {
			System.out.println("problem in connection:"+e);
		} finally {
			DBConnection.closeConnection(con);
		}
		
		return cartList;
	}
	
public static List<CustCartVO> viewOrders(CustomerVO customer) throws CustCartDatabaseException {
		
		List<CustCartVO> orderList = new ArrayList<CustCartVO>();
		Connection con = null;
		String query = "SELECT DISTINCT O.ORDERID, P.NAME, P.COST, P.RATING, P.TYPE FROM PRODUCT P, CUSTCART O WHERE O.PRODID=P.PRODID AND CID=? AND STATUS='Buy'";
		
		try {
			con= DBConnection.getConnection();
		
			PreparedStatement pstat = con.prepareStatement(query);
		    pstat.setString(1, customer.getCid());
		    ResultSet result = pstat.executeQuery();
		    
		    while(result.next()) {
		    	
		    	CustCartVO order = new CustCartVO();
		    	order.setOrderID(result.getString(1));
		    	order.setName(result.getString(2));
		    	order.setCost(result.getDouble(3));
		    	order.setRating(result.getFloat(4));
		    	order.setType(result.getString(5));
		    	orderList.add(order);
		    }
		   
		} catch(DatabaseConnectionException e) {
			System.out.println("problem in connection:"+e);
			throw new CustCartDatabaseException(e);
		} catch(SQLException e) {
			System.out.println("problem in connection:"+e);
		} finally {
			DBConnection.closeConnection(con);
		}
		
		return orderList;
	}
	
}
