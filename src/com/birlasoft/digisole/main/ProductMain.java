package com.birlasoft.digisole.main;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import org.birlasoft.digisole.dao.ProductDAO;
import org.birlasoft.digisole.dao.SpecificationDAO;

import com.birlasoft.digisole.exception.CustCartDatabaseException;
import com.birlasoft.digisole.exception.ProductDatabaseException;
import com.birlasoft.digisole.exception.SpecificationDatabaseException;
import com.birlasoft.digisole.model.CustCartVO;
import com.birlasoft.digisole.model.CustomerVO;
import com.birlasoft.digisole.model.ProductVO;
import com.birlasoft.digisole.model.SpecificationVO;

import org.birlasoft.digisole.dao.CustCartDAO;
import org.birlasoft.digisole.dao.FilterDAO;

/**
 * @author Melvin Mathew
 *
 *This class consist of the page the user enters after the credentials are given right in the
 *CustomerClient class. 
 *
 *It consists of prodWelcome() function which takes one parameter of object CustomerVO class, 
 *from the CustomerClient class.
 *
 *The function takes into the model ProductVO, SpecificationVO, CustCartVO.
 *
 *ProductVO has be inherited by the functions from the classes ProductDAO, SpecificationDAO, 
 *CustCartDAO, FilterDAO.
 *
 */
public class ProductMain {

	/**
	 * @param customer
	 * @return
	 * @throws CustCartDatabaseException
	 * @throws ProductDatabaseException
	 * @throws SpecificationDatabaseException
	 */
	@SuppressWarnings("resource")
	public int prodWelcome(CustomerVO customer) throws CustCartDatabaseException, ProductDatabaseException, SpecificationDatabaseException {

		Scanner sc = new Scanner(System.in);
		String clientContinue = "yes";
		System.out.println();
		System.out.println("Welcome " + customer.getName());
		
		while(clientContinue.equalsIgnoreCase("yes")){
			
			System.out.println();
			System.out.println("What are you looking for:");
			System.out.println("1. Laptop");
			System.out.println("2. Mobile");
			System.out.println("3. Accessories");
			System.out.println("4. View Profile");
			System.out.println("5. View Cart");
			System.out.println("6. View Orders");
			System.out.println("7. Logout");
			
			int choice = 0;
			try {
				choice = sc.nextInt();
			} catch (InputMismatchException e) {
				
				System.out.println();
				System.out.println("Please enter a valid option: " + e);
			}
			
			switch(choice) {
			case 1:
				
				String preProdID = "L-";
				System.out.println();
				System.out.println("Would you want to add filters before you go ahead with viewing laptops?");
				System.out.println("Note: Filters will provide you the products of your need");
				System.out.println("1. Yes\n2. No");
				
				List<ProductVO> lapList = new ArrayList<ProductVO>();
				int filter = 0;
				try {
					filter = sc.nextInt();
				} catch (InputMismatchException e) {
					System.out.println("Please enter a numeric value.");
					break;
				}
				int req = 1;
				int i = 0;
				
				if(filter == 1) {
					
					while(req == 1) {
						
						lapList.addAll(FilterDAO.getFilter(preProdID));
						System.out.println("Products given after filter: " + lapList.size());
						System.out.println();
						
						System.out.println("Laptops with filters are:");
						
						for(ProductVO laptop: lapList) {
							
							i = Integer.parseInt(laptop.getProdID().substring(2));
							System.out.println(i + ". " + laptop.toLaptop());
						}
						
						System.out.println("Do you want to add other filters?");
						System.out.println("1. Yes\n2. No");
						try {
							req = sc.nextInt();
						} catch (InputMismatchException e) {
							System.out.println("Please enter a numeric value.");
						}
						
					}
						
				} else if(req == 2 || filter == 2){
				
					lapList.addAll(ProductDAO.getProduct(preProdID));
					System.out.println("All laptops in store now:");
					
					for(ProductVO laptop: lapList) {
					
						i = Integer.parseInt(laptop.getProdID().substring(2));
						System.out.println(i + ". " + laptop.toLaptop());
					}
					
				} else {
					System.out.println("Please enter a valid option.");
					break;
				}
				
				System.out.println("Which laptop would you want.");
				int prodChoice = 0;
				try {
					prodChoice = sc.nextInt();
				} catch (InputMismatchException e1) {
					System.out.println("Please enter a numeric value.");
					break;
				}
				String prodID = "";
				
				if(prodChoice < 9) {
					prodID = preProdID + "0" + prodChoice;
				} else {
					prodID = preProdID + prodChoice;
				}
				
				SpecificationVO specs = SpecificationDAO.getProdSpecs(prodID);
				System.out.println(specs);
				
				System.out.println("1. Buy now!");
				System.out.println("2. Add to cart");
				System.out.println("3. Exit");
				int buyChoice = 0;
				try {
					buyChoice = sc.nextInt();
				} catch (InputMismatchException e) {
					System.out.println("Please enter a numeric value!");
				}
				int cartResult = 0;
				
				if(buyChoice == 1) {
					
					cartResult = CustCartDAO.addCart(specs, customer, "Buy");
					if(cartResult >= 1) {
						
						System.out.println("Thank you for shopping!");
					} else {
						System.out.println("Sorry the order could not be placed");
					}
				} else if(buyChoice == 2) {
					
					cartResult = CustCartDAO.addCart(specs, customer, "Cart");
					if(cartResult >= 1) {
						System.out.println("Item added to cart.");
					} else {
						System.out.println("Sorry the order could not be placed");
					}
				} else if(buyChoice == 3) {
					
				} else {
					System.out.println("Invalid option!");
				}
				
				break;
				
			case 2:
				
				preProdID = "M-";
				System.out.println("Would you want to add filters before you go ahead with viewing mobiles?");
				System.out.println("Note: Filters will provide you the products of your need");
				System.out.println("1. Yes\n2. No");
				
				List<ProductVO> mobList = new ArrayList<ProductVO>();
				
				filter = 0;
				try {
					filter = sc.nextInt();
				} catch (InputMismatchException e1) {
					System.out.println("Please enter a numeric value.");
					 break;
				}
				req = 1;
				i = 0;
				
				if(filter == 1) {
					
					while(req == 1) {
						
						mobList.addAll(FilterDAO.getFilter(preProdID));
						System.out.println("Products given after filter: " + mobList.size());
						System.out.println();
						
						System.out.println("Mobiles with filters are:");
						for(ProductVO mobile: mobList) {
							
							i = Integer.parseInt(mobile.getProdID().substring(2));
							System.out.println(i + ". " + mobile.toMobile());
						}
						
						System.out.println("Do you want to add other filters?");
						System.out.println("1. Yes\n2. No");
						try {
							req = sc.nextInt();
						} catch (InputMismatchException e) {
							System.out.println("Please enter a numeric value.");
						}
						
					}
						
				} else if(req == 2 || filter == 2){
					
					mobList.addAll(ProductDAO.getProduct(preProdID));
					System.out.println("All mobiles in store now:");
					
					for(ProductVO mobile: mobList) {
						
						i = Integer.parseInt(mobile.getProdID().substring(2));
						System.out.println(i + ". " + mobile.toMobile());
					}
					
				} else {
					System.out.println("Please enter a valid option.");
					break;
				}
				
				System.out.println("Which mobile would you want.");
				
				prodChoice = 0;
				try {
					prodChoice = sc.nextInt();
				} catch (InputMismatchException e1) {
					System.out.println("Please enter a numeric value.");
					break;
				}
				
				if(prodChoice < 9) {
					prodID = preProdID + "0" + prodChoice;
				} else {
					prodID = preProdID + prodChoice;
				}
				
				specs = SpecificationDAO.getProdSpecs(prodID);
				System.out.println(specs);
				
				System.out.println("1. Buy now!");
				System.out.println("2. Add to cart");
				System.out.println("3. Exit");
				
				buyChoice = 0;
				try {
					buyChoice = sc.nextInt();
				} catch (InputMismatchException e) {
					System.out.println("Please enter a number value!");
					break;
				}
				cartResult = 0;
				
				if(buyChoice == 1) {
					
					cartResult = CustCartDAO.addCart(specs, customer, "Buy");
					if(cartResult >= 1) {
						
						System.out.println("Thank you for shopping!");
					} else {
						System.out.println("Sorry the order could not be placed");
					}
				} else if(buyChoice == 2) {
					
					cartResult = CustCartDAO.addCart(specs, customer, "Cart");
					if(cartResult >= 1) {
						System.out.println("Item added to cart.");
					} else {
						System.out.println("Sorry the order could not be placed");
					}
				} else if(buyChoice == 3) {
					
				} else {
					System.out.println("Invalid option!");
				}
				
				break;
				
			case 3:
				preProdID = "A-";
				
				List<ProductVO> accList = new ArrayList<ProductVO>();
				
				i = 0;
				
				accList.addAll(ProductDAO.getProduct(preProdID));
				System.out.println("All Accessories in store now:");
				
				for(ProductVO accessories: accList) {
					
					i = Integer.parseInt(accessories.getProdID().substring(2));
					System.out.println(i + ". " + accessories.toAcessories());
					
				}	
				
				System.out.println("Which Accessories would you want.");
				prodChoice = sc.nextInt();
				
				if(prodChoice < 9) {
					prodID = preProdID + "0" + prodChoice;
				} else {
					prodID = preProdID + prodChoice;
				}

				specs = SpecificationDAO.getProdSpecs(prodID);
				System.out.println(specs);
				
				System.out.println("1. Buy now!");
				System.out.println("2. Add to cart");
				System.out.println("3. Exit");
				
				buyChoice = 0;
				try {
					buyChoice = sc.nextInt();
				} catch (InputMismatchException e) {
					System.out.println("Please enter a numeric value.");
					break;
				}
				cartResult = 0;
				
				if(buyChoice == 1) {
					
					cartResult = CustCartDAO.addCart(specs, customer, "Buy");
					if(cartResult >= 1) {
						
						System.out.println("Thank you for shopping!");
					} else {
						System.out.println("Sorry the order could not be placed");
					}
					
				} else if(buyChoice == 2) {
					
					cartResult = CustCartDAO.addCart(specs, customer, "Cart");
					if(cartResult >= 1) {
						System.out.println("Item added to cart.");
					} else {
						System.out.println("Sorry the order could not be placed");
					}
				} else if(buyChoice == 3) {
					
				} else {
					System.out.println("Invalid option!");
				}
				
				break;
				
			case 4:
				
				System.out.println("*********Profile*********");
				System.out.println(customer);
				
				System.out.println("1. Exit?");
				int exit = 0;
				try {
					exit = sc.nextInt();
				} catch (InputMismatchException e) {
					System.out.println("Please enter a numeric value.");
					break;
				}
				if(exit == 1) {
					break;
				} else {
					System.out.println("Please enter a valid option.");
				}
				
			case 5:
				
				System.out.println("*********My Cart*********");
				List<ProductVO> custcart = new ArrayList<ProductVO>();
				custcart.addAll(CustCartDAO.viewCart(customer));
				double total = 0;
				
				for(ProductVO cart: custcart) {
					
					System.out.println(cart.toCart());	
					total += cart.getCost();
				}
				
				System.out.println("Total amount added in cart: " + total);
				
				System.out.println("1. Continue shopping");
				System.out.println("2. Place order");
				System.out.println("3. Exit");
				int orderChoice = 0;
				try {
					orderChoice = sc.nextInt();
				} catch (InputMismatchException e) {
					System.out.println("Please enter a numeric value");
					break;
				}
				
				if(orderChoice == 1) {
					break;
				} else if(orderChoice == 2) {
					System.out.println("Total amount to be paid: " + total);
					System.out.println("Thank you for shopping!");
				} else {	
					System.out.println("Please enter a valid option.");
				}
				
				break;
				
			case 6:
				
				System.out.println("*********My Orders*********");
				List<CustCartVO> orders = new ArrayList<CustCartVO>();
				orders.addAll(CustCartDAO.viewOrders(customer));
				int count = 0;
				double ordertotal = 0;
				
				for(CustCartVO order: orders) {
					
					count++;
					System.out.println(order);	
					ordertotal += order.getCost();
				}
				
				System.out.println("Total items bought: " + count);
				System.out.println("Total amount spent: " + ordertotal);
				
				break;
				
			case 7: 
				clientContinue = "No";
				break;
				
			default: 
				System.out.println("Please enter a valid option!");
				break;
			}
			
		}	
		
		return 1;		
	}
	
}
