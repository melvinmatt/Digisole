package org.birlasoft.digisole.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.birlasoft.digisole.exception.DatabaseConnectionException;
import com.birlasoft.digisole.exception.ProductDatabaseException;
import com.birlasoft.digisole.model.ProductVO;
import com.birlasoft.digisole.model.SpecificationVO;
import com.birlasoft.digisole.util.DBConnection;

/**
 * @author Melvin Mathew
 * 
 * Used for performing DB operations related to product table.
 * 
 * Consists of function:
 * getProduct(String): It is used by the ProductMain class to retrieve the products for the relevant
 * item ID. The String consists of 'L-' for laptop, 'M-' for mobile, 'A-' for accessories. The 
 * return type is a list of products.
 * 
 * addProduct(ProductVO, SpecificationVO): Used to add a product into the database with information
 * given from AdminMain. Returns an acknowledgement for the insertion operation.
 * 
 * getAllProduct(): Retrieves all the products from the database irrespective of their ID by the
 * AdminMain class. The return type is a List object of ProductVO.
 * 
 * getOneProduct(String): Retrieves a single product from the database for the given prodID as a 
 * String. It returns a ProductVO object to the AdminMain class.
 * 
 * deleteProduct(String): Deletes the complete information from the database relevant to the specsID
 * passed as the String parameter. The product details from the MasterSpec table and the Product
 * table are deleted with this parameter.
 *
 */
public class ProductDAO {

	/**
	 * @param prodID
	 * @return
	 * @throws ProductDatabaseException
	 */
	public static List<ProductVO> getProduct(String prodID) throws ProductDatabaseException {
		
		List<ProductVO> prodList = new ArrayList<ProductVO>();
		Connection con = null;
		String query = "SELECT PRODID, NAME, COST, RATING, TYPE, FORTYPE FROM PRODUCT WHERE PRODID LIKE ?";
			
		try {
			
			con = DBConnection.getConnection();
			
			PreparedStatement pstat = con.prepareStatement(query);
			pstat.setString(1, prodID + "%");
			ResultSet prodResult = pstat.executeQuery();
			
			while(prodResult.next()) {
				
				ProductVO prod = new ProductVO();
				prod.setProdID(prodResult.getString(1));
				prod.setName(prodResult.getString(2));
				prod.setCost(prodResult.getDouble(3));
				prod.setRating(prodResult.getFloat(4));
				prod.setType(prodResult.getString(5));
				prod.setForType(prodResult.getString(6));
				prodList.add(prod);
			}
			
		} catch (DatabaseConnectionException e) {
			
			System.out.println("Error in fetching connection:" + e);
			throw new ProductDatabaseException(e);
		} catch (SQLException e) {
			
			System.out.println("Error in fetching laptop details:" + e);
		}
		
		return prodList;
	}
	
	/**
	 * @param product
	 * @param specs
	 * @return
	 * @throws ProductDatabaseException
	 */
	public static boolean addProduct(ProductVO product, SpecificationVO specs) throws ProductDatabaseException {
		
		Connection con = null;
		String prodQuery = "INSERT INTO PRODUCT VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
		String specQuery = "INSERT INTO MASTERSPEC VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		int specResult = 0, prodResult = 0;
			
		try {
			
			con = DBConnection.getConnection();
			
			PreparedStatement pstat = con.prepareStatement(specQuery);
			pstat.setString(1, specs.getSpecID());
			pstat.setString(2, specs.getProcessorBrand());
			pstat.setString(3, specs.getProcessor());
			pstat.setString(4, specs.getStorageType());
			pstat.setString(5, specs.getRam());
			pstat.setString(6, specs.getRom());
			pstat.setString(7, specs.getOs());
			pstat.setString(8, specs.getColor());
			pstat.setString(9, specs.getSeller());
			pstat.setString(10, specs.getOther());
			
			specResult = pstat.executeUpdate();
			
			pstat = con.prepareStatement(prodQuery);
			pstat.setString(1, product.getProdID());
			pstat.setString(2, product.getName());
			pstat.setString(3, specs.getSpecID());
			pstat.setDouble(4, product.getCost());
			pstat.setFloat(5, product.getRating());
			pstat.setString(6, product.getOffer());
			pstat.setString(7, product.getType());
			pstat.setString(8, product.getForType());
			
			prodResult = pstat.executeUpdate();
			
		} catch (DatabaseConnectionException e) {
			
			System.out.println("Error in fetching connection:" + e);
			throw new ProductDatabaseException(e);
		} catch (SQLException e) {
			
			System.out.println("Error in fetching laptop details:" + e);
		} finally {
			DBConnection.closeConnection(con);
		}
		
		if(specResult >= 1 && prodResult >= 1) {
			
			return true;
			
		}
		
		return false;
	}
	
	/**
	 * @return
	 * @throws ProductDatabaseException
	 */
	public static List<ProductVO> getAllProduct() throws ProductDatabaseException {
		
		List<ProductVO> prodList = new ArrayList<ProductVO>();
		Connection con = null;
		String query = "SELECT * FROM PRODUCT";
			
		try {
			
			con = DBConnection.getConnection();
			
			PreparedStatement pstat = con.prepareStatement(query);
			ResultSet prodResult = pstat.executeQuery();
			
			while(prodResult.next()) {
				
				ProductVO prod = new ProductVO();
				prod.setProdID(prodResult.getString(1));
				prod.setName(prodResult.getString(2));
				prod.setSpecID(prodResult.getString(3));
				prod.setCost(prodResult.getDouble(4));
				prod.setRating(prodResult.getFloat(5));
				prod.setOffer(prodResult.getString(6));
				prod.setType(prodResult.getString(7));
				prod.setForType(prodResult.getString(8));
				prodList.add(prod);
			}
			
		} catch (DatabaseConnectionException e) {
			
			System.out.println("Error in fetching connection:" + e);
			throw new ProductDatabaseException(e);
		} catch (SQLException e) {
			
			System.out.println("Error in fetching laptop details:" + e);
		} finally {
			DBConnection.closeConnection(con);
		}
		
		return prodList;
	}
	
	/**
	 * @param prodID
	 * @return
	 * @throws ProductDatabaseException
	 */
	public static ProductVO getOneProduct(String prodID) throws ProductDatabaseException {
		
		ProductVO product = new ProductVO();
		Connection con = null;
		String query = "SELECT PRODID, NAME, COST, RATING, TYPE, FORTYPE FROM PRODUCT WHERE PRODID = ?";
			
		try {
			
			con = DBConnection.getConnection();
			
			PreparedStatement pstat = con.prepareStatement(query);
			pstat.setString(1, prodID);
			ResultSet prodResult = pstat.executeQuery();
			
			while(prodResult.next()) {
				
				product.setProdID(prodResult.getString(1));
				product.setName(prodResult.getString(2));
				product.setCost(prodResult.getDouble(3));
				product.setRating(prodResult.getFloat(4));
				product.setType(prodResult.getString(5));
				product.setForType(prodResult.getString(6));
			}
			
		} catch (DatabaseConnectionException e) {
			
			System.out.println("Error in fetching connection:" + e);
			throw new ProductDatabaseException(e);
		} catch (SQLException e) {
			
			System.out.println("Error in fetching laptop details:" + e);
		} finally {
			DBConnection.closeConnection(con);
		}
		
		return product;
	}
	
	/**
	 * @param specID
	 * @return
	 * @throws ProductDatabaseException
	 */
	public static boolean deleteProduct(String specID) throws ProductDatabaseException {
		
		Connection con = null;
		String deleteQuery = "DELETE * FROM MASTERSPEC WHERE SPECID = ?";
		int result = 0;
			
		try {
			
			con = DBConnection.getConnection();
			
			PreparedStatement pstat = con.prepareStatement(deleteQuery);
			pstat.setString(1, specID);
			
			result = pstat.executeUpdate();
			
		} catch (DatabaseConnectionException e) {
			
			System.out.println("Error in fetching connection:" + e);
			throw new ProductDatabaseException(e);
		} catch (SQLException e) {
			
			System.out.println("Error in fetching laptop details:" + e);
		} finally {
			DBConnection.closeConnection(con);
		}
		
		if(result >= 1) {
			
			return true;
			
		}
		
		return false;
	}

}
