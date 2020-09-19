package com.birlasoft.digisole.service;

import com.birlasoft.digisole.model.CustomerVO;

/**
 * @author Melvin Mathew
 * 
 * validates the business requirement for a customer entity.
 * 
 * validateAge(CustomerVO): Validates the age of the customer, where the customer age should be equal
 * or above the age of 15. Return a boolean value depending on the input.
 * 
 * validatePhNo(CustomerVO): Validates the phone number of the customer. The customer phone number 
 * should be equal to 10 digit character. Return a boolean value depending on the input.
 *
 */
public class CustomerBO {

	/**
	 * @param cust
	 * @return
	 */
	public static boolean validateAge(CustomerVO cust) {
		
		if(cust.getAge() >= 15) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * @param cust
	 * @return
	 */
	public static boolean validatePhNo(CustomerVO cust) {
		
		if(cust.getPhNo().length() == 10) {
			return true;
		}
		
		return false;	
	}
	
}
