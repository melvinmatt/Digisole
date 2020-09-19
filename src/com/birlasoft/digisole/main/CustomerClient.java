package com.birlasoft.digisole.main;

import java.util.InputMismatchException;
import java.util.Scanner;

import org.birlasoft.digisole.dao.AdminDAO;
import org.birlasoft.digisole.dao.CustomerDAO;
import org.birlasoft.digisole.dao.LoginDAO;

import com.birlasoft.digisole.exception.AdminDatabaseException;
import com.birlasoft.digisole.exception.CustCartDatabaseException;
import com.birlasoft.digisole.exception.CustomerDatabaseException;
import com.birlasoft.digisole.exception.DatabaseConnectionException;
import com.birlasoft.digisole.exception.LoginDatabaseException;
import com.birlasoft.digisole.exception.ProductDatabaseException;
import com.birlasoft.digisole.exception.SpecificationDatabaseException;
import com.birlasoft.digisole.model.AdminVO;
import com.birlasoft.digisole.model.CustomerVO;
import com.birlasoft.digisole.model.LoginVO;
import com.birlasoft.digisole.service.CustomerBO;
import com.birlasoft.digisole.service.LoginBO;
import com.birlasoft.digisole.util.DBConnection;

/**
 * @author Melvin Mathew
 * 
 * This class consists of the main function and is the start to the system itself. 
 * 
 * It consists of the essential function the user needs to use the system which is making a
 * new user ID and also login to the account itself.
 * 
 * This program connects the ProductMain class and the AdminMain class which are essential
 * internals of the system itself. 
 * 
 * Only if all the credentials given by the user is right, only then will the other two connected
 * class will be aided in running.
 * 
 * This class is considered to be present at-most importance for the working of the system.
 * 
 * The CustomerVO and LoginVO are the model used with DAO such as CustomerDAO and LoginDAO.
 *
 */
public class CustomerClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	
		Scanner sc = new Scanner(System.in);
		try {
			DBConnection.loadDrivers();
		} catch (DatabaseConnectionException e) {

			System.out.println("Problem in loading connection: " + e);
		}
		
		System.out.println("			Welcome to Digisole Mart!");
		System.out.println("		       Solution to your digital life");
		
		while(true) {
			System.out.println();
			System.out.println("1. New User? Sign Up Now!");
			System.out.println("2. Login");
			System.out.println("3. Exit");
			
			int choice = 0;
			try {
				choice = sc.nextInt();
			} catch (InputMismatchException e3) {
				System.out.println("Please enter a valid option: " + e3);
			}
			
			switch(choice) {
			
			case 1:
				
				CustomerVO newcust = new CustomerVO();
				System.out.println("Loading...");
				
				int cid = 0;
				try {
					cid = Integer.parseInt(CustomerDAO.getCID().substring(2));
				} catch (NumberFormatException e3) {
					System.out.println("Error in loading Customer ID");
				} catch (CustomerDatabaseException e3) {
					
					System.out.println("Error in customer CID: " + e3);
				}
				
				String custID = "";
				if(cid < 9) {
					custID = "C-0" + ++cid;
				} else {
					custID = "C-" + ++cid;
				}
				
				System.out.println();
				System.out.println("Please enter your details in the format [Name, Age, Address, City, Contact] to sign up");
				
				
				while(true) {
					
					try {
						System.out.print("Name: ");
						newcust.setName(sc.next() + sc.nextLine());
						System.out.print("Age: ");
						newcust.setAge(Integer.parseInt(sc.nextLine()));
						System.out.print("Address: ");
						newcust.setAddress(sc.nextLine());
						System.out.print("City: ");
						newcust.setCity(sc.nextLine());
						System.out.print("Phone number: ");
						newcust.setPhNo(sc.nextLine());
						newcust.setCid(custID);
						break;
					} catch (NumberFormatException e2) {
						
						System.out.println();
						System.out.println("Error in input data. Please try again" + e2);
					}
				
				}
				
				if(CustomerBO.validateAge(newcust) && CustomerBO.validatePhNo(newcust)) {
					
					int custresult = CustomerDAO.addCustomer(newcust);
					if(custresult >= 1) {
						
						LoginVO newlogin = new LoginVO();
						
						System.out.println();
						System.out.println("Please give your login ID:");
						System.out.println("Note: The login you give will be used to sign in your account.");
						
						while(!LoginBO.validateLoginID(newlogin)) {
							String newloginID = sc.nextLine();
							newlogin.setLoginID(newloginID);
							if(!LoginBO.validateLoginID(newlogin))
								
								System.out.println("Invalid login ID. Try again without spaces and ID should be minimum of 4 characters");
						}
						
						System.out.println();
						System.out.println("Enter your password:");
						System.out.println("Note: \n1) It should be of atleast 8 characters.");
						System.out.println("2) It should contain atleast 1 special character.");
						System.out.println("3) It should contain atleast 1 uppercase alphabet.");
						System.out.println("4) It should contain atleast 1 lowercase alphabet.");
						System.out.println("5) It should contain atleast 1 digit.");
						System.out.println("6) It should not have any space, tab.");
						
						while(!LoginBO.validatePassword(newlogin)) {
							String newpassword = sc.nextLine();
							newlogin.setPassword(newpassword);
							if(!LoginBO.validatePassword(newlogin))
							
								System.out.println("Invalid password format. Try again");
						}
						
						newlogin.setCid(newcust.getCid());
	
						int loginresult = 0;
						try {
							
							loginresult = LoginDAO.addLogin(newlogin);
						} catch (LoginDatabaseException e1) {
							System.out.println();
							System.out.println("Problem in login:" + e1);
						}
						if(loginresult >= 1) {
							System.out.println();
							System.out.println("Login details added successfully");
						} else {
							System.out.println();
							System.out.println("Error in adding login details.");
						}
						
					} else {
						System.out.println();
						System.out.println("Error in adding customer details ");
					}
				} else {
					System.out.println();
					System.out.println("Invalid Customer details");
				}
				
				break;
				
			case 2:
				
				LoginVO login = new LoginVO();
				
				System.out.println();
				System.out.print("Enter login ID:");
				login.setLoginID(sc.next());
				System.out.print("Enter password:");
				login.setPassword(sc.next());
				
				if(LoginBO.validateLoginID(login) && LoginBO.validatePassword(login)) {
					
					try {
						
						int logresult = LoginDAO.loginCheck(login);
						if(logresult >= 1) {
							
							ProductMain prodMain = new ProductMain();
							CustomerVO customer = null;
							try {
								customer = CustomerDAO.fetchCustomer(login);
							} catch (CustomerDatabaseException e) {
								
								System.out.println("Error in fetching customer: " + e);
							}
							
							try {
								prodMain.prodWelcome(customer);
							} catch (CustCartDatabaseException e) {

								System.out.println("Error in customer cart: " + e);
							} catch (ProductDatabaseException e) {
								
								System.out.println("Error in fetching products: " + e);
							} catch (SpecificationDatabaseException e) {

								System.out.println("Error in fetching specification: " + e);
							}
							
						} else
							try {
								AdminVO admin = new AdminVO();
								admin.setLoginID(login.getLoginID());
								admin.setPassword(login.getPassword());
								
								if(AdminDAO.adminCheck(admin) >= 1) {
									
									AdminVO admin1 = AdminDAO.fetchAdmin(admin);
									
									AdminMain adminPage = new AdminMain();
									try {
										adminPage.adminWelcome(admin1);
									} catch (ProductDatabaseException e) {
										
										System.out.println("Error in fetching product for admin: " + e);
									}
									
								} else {
									System.out.println("Error getting login details");
								}
							} catch (AdminDatabaseException e) {
								System.out.println("Error in fetching admin: " + e);
							}
					} catch (LoginDatabaseException e) {
						
						System.out.println();
						System.out.println("Error in loging into account:" + e);
					}
				} else {
					
					System.out.println();
					System.out.println("Invalid login ID and password.");
				}
				 
				break;
				
			case 3:
				
				sc.close();
				System.exit(0);
				break;
				
			default: 
				
				System.out.println("Invalid option!");
			
			}
			
		}
		
	}

}
