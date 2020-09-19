package org.birlasoft.digisole.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.birlasoft.digisole.exception.DatabaseConnectionException;
import com.birlasoft.digisole.model.ProductVO;
import com.birlasoft.digisole.util.DBConnection;

/**
 * @author Melvin Mathew
 * 
 * FilterDAO helps in filtering the data of the products according to users choice. This includes:
 * 
 * Choice for filter by cost
 * Choice for filter by processors(This excludes accessories in the product table in DB)
 * Choice for filter by storage types(HDD/SSD for laptops and RAM segregation for mobiles)
 * Choice for filter by OS(This excludes accessories in the product table in DB)
 * Choice for filter by ratings
 * 
 * Called by ProductMain class and return a list of products after filtering.
 *
 */
public class FilterDAO {

	/**
	 * @param prodid
	 * @return
	 */
	@SuppressWarnings("resource")
	public static List<ProductVO> getFilter(String prodid) {
		
		List<ProductVO> filter = new ArrayList<ProductVO>();
		Connection con = null;
		PreparedStatement pstat = null;
		Scanner scan = new Scanner(System.in);
		
		try {
			con = DBConnection.getConnection();
		} catch (DatabaseConnectionException e) {
			
			System.out.println();
			System.out.println("Error in fetching connection:" + e);
		}
			
		System.out.println();
		System.out.println("Choose the filters below");
		System.out.println("1. Cost");
		System.out.println("2. Processor");
		System.out.println("3. Storage type");
		System.out.println("4. Operating system");
		System.out.println("5. Ratings");
		System.out.println("6. Exit filters");
		
		int choice = 0;
		try {
			choice = scan.nextInt();
		} catch (InputMismatchException e1) {

			System.out.println("Please input valid option:" + e1);
		}
		
		switch(choice) {
		case 1: 
			String costQuery = "SELECT PRODID, NAME, COST, RATING, TYPE FROM PRODUCT WHERE PRODID LIKE ? AND COST BETWEEN ? AND ?";
			List<Integer> cost = new ArrayList<Integer>();
			
			cost.add(0, null);
			cost.add(1, 0);
			cost.add(2, 10000);
			cost.add(3, 20000);
			cost.add(4, 30000);
			cost.add(5, 40000);
			cost.add(6, 50000);
			cost.add(7, 60000);
			
			System.out.println();
			System.out.println("Price range in:");
			System.out.println("1. min - 10,000");
			System.out.println("2. 10,000 - 20,000");
			System.out.println("3. 20,000 - 30,000");
			System.out.println("4. 30,000 - 40,000");
			System.out.println("5. 40,000 - 50,000");
			System.out.println("6. 50,000 - 60,000");
			System.out.println("7. 60,000 - 70,000");
			
			int costFilter = 0;
			try {
				costFilter = scan.nextInt();
			} catch (InputMismatchException e1) {
				System.out.println();
				System.out.println("Please enter valid option: " + e1);
				break;
			}
			
			if(costFilter == 0 || costFilter > 7) {
				System.out.println("Please enter valid option.");
				break;
			}
			
			try {
				pstat = con.prepareStatement(costQuery);
				pstat.setString(1, prodid + "%");
				pstat.setDouble(2, cost.get(costFilter));
				pstat.setDouble(3, cost.get(costFilter) + 10000);
				ResultSet costResult = pstat.executeQuery();
				
				while(costResult.next()) {
					ProductVO prod = new ProductVO();
					prod.setProdID(costResult.getString(1));
					prod.setName(costResult.getString(2));
					prod.setCost(costResult.getDouble(3));
					prod.setRating(costResult.getFloat(4));
					prod.setType(costResult.getString(5));
					filter.add(prod);
				}
				
			} catch (SQLException e) {
				System.out.println();
				System.out.println("Error in fetching cost filter:" + e);
			} finally {
				DBConnection.closeConnection(con);
			}
			
			break;
			
		case 2: 
			String processorQuery = "SELECT P.PRODID, P.NAME, P.COST, P.RATING, P.TYPE FROM PRODUCT P, MASTERSPEC M WHERE P.SPECID = M.SPECID AND P.PRODID LIKE ? AND M.PROCESSOR_BRAND=?";
			List<String> processor = new ArrayList<String>();
			
			if(prodid.equalsIgnoreCase("L-")) {
				processor.add(0, "");
				processor.add(1, "Intel");
				processor.add(2, "AMD");
				
				System.out.println();
				System.out.println("Available processors:");
				System.out.println("1. Intel");
				System.out.println("2. AMD");
			} else {
				processor.add(0, "");
				processor.add(1, "Qualcomm");
				processor.add(2, "MediaTek");
				
				System.out.println();
				System.out.println("Available processors:");
				System.out.println("1. Qualcomm");
				System.out.println("2. MediaTek");
			}
			
			
			int processorFilter = 0;
			try {
				processorFilter = scan.nextInt();
			} catch (InputMismatchException e1) {
				
				System.out.println();
				System.out.println("Please enter valid option: " + e1);
			}
			
			if(processorFilter == 0 || processorFilter > 2) {
				System.out.println("Please enter valid option.");
				break;
			}
			
			try {
				pstat = con.prepareStatement(processorQuery);
				pstat.setString(1, prodid + "%");
				pstat.setString(2, processor.get(processorFilter));
				ResultSet processorResult = pstat.executeQuery();
				
				while(processorResult.next()) {
					ProductVO prod = new ProductVO();
					prod.setProdID(processorResult.getString(1));
					prod.setName(processorResult.getString(2));
					prod.setCost(processorResult.getDouble(3));
					prod.setRating(processorResult.getFloat(4));
					prod.setType(processorResult.getString(5));
					filter.add(prod);
				}
				
			} catch (SQLException e) {
			
				System.out.println();
				System.out.println("Error in fetching processor filter:" + e);
			} finally {
				DBConnection.closeConnection(con);
			}
			
			break;
			
		case 3:
			String storageQuery = "";
			List<String> storage = new ArrayList<String>();
			
			if(prodid.equalsIgnoreCase("L-")) {
				
				storageQuery = "SELECT P.PRODID, P.NAME, P.COST, P.RATING, P.TYPE FROM PRODUCT P, MASTERSPEC M WHERE P.SPECID = M.SPECID AND P.PRODID LIKE ? AND M.STORAGE_TYPE=?";

				storage.add(0, "");
				storage.add(1, "HDD");
				storage.add(2, "SSD");
				storage.add(3, "HDD/SDD");
				
				System.out.println("1. HDD");
				System.out.println("2. SSD");
				System.out.println("3. HDD/SSD");
			} else {
				
				storageQuery = "SELECT P.PRODID, P.NAME, P.COST, P.RATING FROM PRODUCT P, MASTERSPEC M WHERE P.SPECID = M.SPECID AND P.PRODID LIKE ? AND M.RAM=?";

				storage.add(0, "");
				storage.add(1, "4GB");
				storage.add(2, "6GB");
				storage.add(3, "8GB");
				
				System.out.println("1. 4GB");
				System.out.println("2. 6GB");
				System.out.println("3. 8GB");
			}
			
			int storageFilter = 0;
			try {
				storageFilter = scan.nextInt();
			} catch (InputMismatchException e1) {
				System.out.println();
				System.out.println("Please enter valid option: " + e1);
				break;
			}
			
			if(storageFilter == 0 || storageFilter > 3) {
				System.out.println("Please enter valid option.");
				break;
			}
			
			try {
				pstat = con.prepareStatement(storageQuery);
				pstat.setString(1, prodid + "%");
				pstat.setString(2, storage.get(storageFilter));
				ResultSet storageResult = pstat.executeQuery();
				
				while(storageResult.next()) {
					ProductVO prod = new ProductVO();
					prod.setProdID(storageResult.getString(1));
					prod.setName(storageResult.getString(2));
					prod.setCost(storageResult.getDouble(3));
					prod.setRating(storageResult.getFloat(4));
					prod.setType(storageResult.getString(5));
					filter.add(prod);
				}
				
			} catch (SQLException e) {
				System.out.println();
				System.out.println("Error in fetching storage filter:" + e);
			} finally {
				DBConnection.closeConnection(con);
			}
			
			break;
			
		case 4:
			String osQuery = "SELECT P.PRODID, P.NAME, P.COST, P.RATING, P.TYPE FROM PRODUCT P, MASTERSPEC M WHERE P.SPECID = M.SPECID AND P.PRODID LIKE ? AND M.OS=?";
			List<String> os = new ArrayList<String>();
			
			if(prodid.equalsIgnoreCase("L-")) {
				os.add(0, "");
				os.add(1, "Windows 10");
				os.add(2, "Mac OS Catalina");
				os.add(3, "Windows 8");
				os.add(4, "Linus");
				
				System.out.println("1. Windows 10");
				System.out.println("2. Mac OS Catalina");
				System.out.println("3. Windows 8");
				System.out.println("4. Linus");
			} else {
				os.add(0, "");
				os.add(1, "Android");
				os.add(2, "iOS");
				
				System.out.println("1. Android");
				System.out.println("2. iOS");
			}
			
			
			int osFilter = 0;
			try {
				osFilter = scan.nextInt();
			} catch (InputMismatchException e1) {
				System.out.println();
				System.out.println("Please enter valid option: " + e1);
			}
			
			if((osFilter == 0 || osFilter > 4) && prodid.equalsIgnoreCase("L-")) {
				System.out.println("Please enter valid option.");
				break;
			} else if((osFilter == 0 || osFilter > 2) && prodid.equalsIgnoreCase("M-")) {
				System.out.println("Please enter valid option.");
				break;
			}
			
			try {
				pstat = con.prepareStatement(osQuery);
				pstat.setString(1, prodid + "%");
				pstat.setString(2, os.get(osFilter));
				ResultSet osResult = pstat.executeQuery();
				
				while(osResult.next()) {
					ProductVO prod = new ProductVO();
					prod.setProdID(osResult.getString(1));
					prod.setName(osResult.getString(2));
					prod.setCost(osResult.getDouble(3));
					prod.setRating(osResult.getFloat(4));
					prod.setType(osResult.getString(5));
					filter.add(prod);
				}
				
			} catch (SQLException e) {
				System.out.println();
				System.out.println("Error in fetching storage filter:" + e);
			} finally {
				DBConnection.closeConnection(con);
			}
			
			break;
		
		case 5:
			
			String rateQuery = "SELECT PRODID, NAME, COST, RATING, TYPE FROM PRODUCT WHERE PRODID LIKE ? AND RATING >= ?";
			List<Float> rating = new ArrayList<Float>();
			
			rating.add(0, null);
			rating.add(1, (float) 1.0);
			rating.add(2, (float) 2.0);
			rating.add(3, (float) 3.0);
			rating.add(4, (float) 4.0);
			rating.add(5, (float) 4.5);
			
			System.out.println();
			System.out.println("Ratings:");
			System.out.println("1. 1 star and above");
			System.out.println("2. 2 star and above");
			System.out.println("3. 3 star and above");
			System.out.println("4. 4 star and above");
			System.out.println("5. 4.5 star and above");
			
			int rateFilter = 0;
			try {
				rateFilter = scan.nextInt();
			} catch (InputMismatchException e1) {
				System.out.println();
				System.out.println("Please enter valid option: " + e1);
			}
			
			if(rateFilter == 0 || rateFilter > 5) {
				System.out.println("Please enter valid option.");
				break;
			}
			
			try {
				pstat = con.prepareStatement(rateQuery);
				pstat.setString(1, prodid + "%");
				pstat.setFloat(2, rating.get(rateFilter));
				ResultSet rateResult = pstat.executeQuery();
				
				while(rateResult.next()) {
					ProductVO prod = new ProductVO();
					prod.setProdID(rateResult.getString(1));
					prod.setName(rateResult.getString(2));
					prod.setCost(rateResult.getDouble(3));
					prod.setRating(rateResult.getFloat(4));
					prod.setType(rateResult.getString(5));
					filter.add(prod);
				}
				
			} catch (SQLException e) {
				System.out.println();
				System.out.println("Error in fetching ratings filter:" + e);
			} finally {
				DBConnection.closeConnection(con);
			}
			
			break;
			
		case 6:
			
			return filter;
			
		default: 
			System.out.println("Please enter a valid option!");
			break;
			
		} 
			
		return filter;
	}

}
