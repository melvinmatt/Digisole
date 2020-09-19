package org.birlasoft.digisole.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.birlasoft.digisole.exception.DatabaseConnectionException;
import com.birlasoft.digisole.exception.SpecificationDatabaseException;
import com.birlasoft.digisole.model.SpecificationVO;
import com.birlasoft.digisole.util.DBConnection;

/**
 * @author Melvin Mathew
 * 
 * Consist of function to retrieve all the product details including the specification.
 * getProdSpecs(String): Returns a SpecificationVO object consisting of all the product details
 *
 */
public class SpecificationDAO {

	/**
	 * @param prodID
	 * @return
	 * @throws SpecificationDatabaseException
	 */
	public static SpecificationVO getProdSpecs(String prodID) throws SpecificationDatabaseException {
		
		SpecificationVO chosen = new SpecificationVO();
		Connection con = null;
		String query1 = "SELECT * FROM PRODUCT WHERE PRODID=?";
		String query2 = "SELECT * FROM MASTERSPEC WHERE SPECID=?";
		
		try {
			con = DBConnection.getConnection();
			PreparedStatement pstat = con.prepareStatement(query1);
			pstat.setString(1, prodID);
			ResultSet result = pstat.executeQuery();
			
			if(result.next()) {
				chosen.setProdID(result.getString(1));
				chosen.setName(result.getString(2));
				chosen.setSpecID(result.getString(3));
				chosen.setCost(result.getDouble(4));
				chosen.setRating(result.getFloat(5));
				chosen.setOffer(result.getString(6));
				chosen.setType(result.getString(7));
				chosen.setForType(result.getString(8));
			}
				
			pstat = con.prepareStatement(query2);
			pstat.setString(1, chosen.getSpecID());
			result = pstat.executeQuery();
			
			while(result.next()) {
				chosen.setSpecID(result.getString(1));
				chosen.setProcessorBrand(result.getString(2));
				chosen.setProcessor(result.getString(3));
				chosen.setStorageType(result.getString(4));
				chosen.setRam(result.getString(5));
				chosen.setRom(result.getString(6));
				chosen.setOs(result.getString(7));
				chosen.setColor(result.getString(8));
				chosen.setSeller(result.getString(9));
				chosen.setOther(result.getString(10));
			}
			
		} catch (DatabaseConnectionException e) {
			
			System.out.println("Error in fetching connection:" + e);
			throw new SpecificationDatabaseException(e);
		} catch (SQLException e) {

			System.out.println("Error in fetching spec data:" + e);
		} finally {
			DBConnection.closeConnection(con);
		}
		
		return chosen;
	}
	
}
