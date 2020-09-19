package com.birlasoft.digisole.main;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import org.birlasoft.digisole.dao.AdminDAO;
import org.birlasoft.digisole.dao.CustomerDAO;
import org.birlasoft.digisole.dao.ProductDAO;

import com.birlasoft.digisole.exception.AdminDatabaseException;
import com.birlasoft.digisole.exception.CustomerDatabaseException;
import com.birlasoft.digisole.exception.ProductDatabaseException;
import com.birlasoft.digisole.model.AdminVO;
import com.birlasoft.digisole.model.CustomerVO;
import com.birlasoft.digisole.model.ProductVO;
import com.birlasoft.digisole.model.SpecificationVO;
import com.birlasoft.digisole.service.AdminBO;
import com.birlasoft.digisole.service.ProductBO;
import com.birlasoft.digisole.service.SpecificationBO;

/**
 * @author Melvin Mathew
 * 
 * AdminMain is an exclusive class for the admin users. This class consists of 
 * 
 *  adminWelcome() class which takes one parameter called admin of model AdminVO.
 *  
 *  It consists of operation for the admin such as adding products details, deleting product
 *  details, viewing the products present. 
 *  
 *  Admin related operations like changing password and viewing profile is also available.
 *
 */
public class AdminMain {

	@SuppressWarnings("resource")
	public int adminWelcome(AdminVO admin) throws ProductDatabaseException {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println();
		System.out.println("*********Admin Mode*********");
		System.out.println("Welcome " + admin.getName() + "!");
		System.out.println("What would you like to do?");
		
		String adminReq = "1";
		
		while(adminReq.equalsIgnoreCase("1")) {
			
			System.out.println();
			System.out.println("Available options for you are:");
			System.out.println("1. Add products");
			System.out.println("2. View all products");
			System.out.println("3. View all customers");
			System.out.println("4. Delete product");
			System.out.println("5. Change your password");
			System.out.println("6. View profile");
			System.out.println("7. Logout");
			
			int choice = 0;
			try {
				choice = Integer.parseInt(sc.nextLine());
			} catch (NumberFormatException e2) {
				System.out.println("Please enter a numeric value.");
			}
			
			switch(choice) {
			
			case 1:
				
				ProductVO product = new ProductVO();
				SpecificationVO specs = new SpecificationVO();
				
				System.out.print("Enter productID:");
				while(!ProductBO.validateProdID(product)) {
					String prodID = sc.nextLine();
					product.setProdID(prodID);
					if(!ProductBO.validateProdID(product))
					
						System.out.println("Invalid product ID format. Try again");
				}
				
				System.out.print("Enter product name:");
				product.setName(sc.nextLine());
				
				System.out.print("Enter product specificationID:");
				while(!SpecificationBO.validateSpecID(specs)) {
					String specID = sc.nextLine();
					specs.setSpecID(specID);
					if(!SpecificationBO.validateSpecID(specs))
					
						System.out.println("Invalid specification ID format. Try again");
				}
				
				System.out.print("Enter cost:");
				try {
					product.setCost(sc.nextDouble());
				} catch (InputMismatchException e2) {
					System.out.println("Please enter a valid cost.");
					break;
				}
				
				System.out.print("Enter rating:"); 
				while(!ProductBO.validateRating(product)) {
					float rate = sc.nextFloat();
					product.setRating(rate);
					if(!ProductBO.validateRating(product))
					
						System.out.println("Ratings should be less than 5.0. Try again");
				}
				
				sc.nextLine();
				System.out.print("Enter available offer:");
				product.setOffer(sc.nextLine());
				
				System.out.print("Type:");
				product.setType(sc.nextLine());
				
				System.out.print("ForType (This can be avoided if type=Accessories):");
				product.setForType(sc.next());
				
				System.out.print("Enter processor brand(Can be avoided if type=Accessories):");
				specs.setProcessorBrand(sc.nextLine());
				
				System.out.print("Enter processor(Can be avoided if type=Accessories):");
				specs.setProcessor(sc.nextLine());
				
				System.out.print("Enter storage-type(Can be avoided if type is other than laptop, mobile and external memeory):");
				specs.setStorageType(sc.nextLine());
				
				System.out.print("Enter RAM of device(Can be avoided if type=Accessories):");
				specs.setRam(sc.nextLine());
				
				System.out.print("Enter ROM of device(Can be avoided if type=Accessories):");
				specs.setRom(sc.nextLine());
				
				System.out.print("Enter OS(Can be avoided if type=Accessories):");
				specs.setOs(sc.nextLine());
				
				System.out.print("Enter color:");
				specs.setColor(sc.nextLine());
				
				System.out.print("Enter seller:");
				specs.setSeller(sc.nextLine());
				
				System.out.print("Enter Other facts to be known:");
				specs.setOther(sc.nextLine());
				
				if(ProductDAO.addProduct(product, specs)) {
					
					System.out.println("Item added successfully!");
				} else {
					System.out.println("There was an error adding item, please try again");
				}
				
				break;
				
			case 2:
				
				List<ProductVO> products = new ArrayList<ProductVO>();
				
				products.addAll(ProductDAO.getAllProduct());
				
				products.forEach((prod)->System.out.println(prod));
				
				break;
				
			case 3:
				
				List<CustomerVO> customers = new ArrayList<CustomerVO>();
				
				try {
					customers.addAll(CustomerDAO.getAllCustomers());
				} catch (CustomerDatabaseException e1) {
					
					System.out.println("Error in displaying customer: " + e1);
				}
				
				customers.forEach((customer)->System.out.println(customer));;
				
				break;
				
			case 4:
				
				System.out.print("Enter the product ID to be deleted:");
				String prodID = sc.nextLine();
				
				System.out.println("The product that you have chosen to delete is:");
				System.out.println(ProductDAO.getOneProduct(prodID));
				
				System.out.println("Do you want to delete the product?");
				System.out.println("1. Yes");
				System.out.println("2. No");
				
				int confirm = 0;
				try {
					confirm = sc.nextInt();
				} catch (InputMismatchException e1) {
					System.out.println("Please enter a valid numeric value.");
					break;
				}
				
				if(confirm == 1) {
					boolean result = ProductDAO.deleteProduct(prodID);
					if(result) {
						System.out.println("Delete successful!");
					} else {
						System.out.println("Delete was unsuccessful!");
					}
				} else if(confirm == 2){
					System.out.println("No delete taking place.");
				} else {
					System.out.println("Please enter a valid option.");
				}
				
				break;
				
			case 5:
				
				System.out.print("Enter your old password:");
				String oldPwd = sc.nextLine();
				
				try {
					if(AdminDAO.checkPassword(admin, oldPwd)) {
						System.out.print("Enter new password:");
						String newPassword = "";
						
						while(!AdminBO.validateAdminPassword(newPassword)) {
							newPassword = sc.nextLine();
							if(!AdminBO.validateAdminPassword(newPassword))
								System.out.println("Invalid password format. Try again");
						}
						
						admin.setPassword(newPassword);
						int passResult = AdminDAO.addNewPassword(admin);
						
						if(passResult >= 1) {
							System.out.println("Password changed successfully!");
						} else {
							System.out.println("Password could not be changed.");
						}
					} else {
						System.out.println("Entered password is incorrect!");
					}
				} catch (AdminDatabaseException e) {
					System.out.println("Error in changing password from database: " + e);
				}
				
				break;
				
			case 6:
				
				System.out.println(admin);
				
				break;
				
			case 7:
				
				adminReq = "0";
				break;
				
			default:
				System.out.println("Enter a valid option.");
			
			}
			
		}
		
		return 1;
		
	}
	
}
