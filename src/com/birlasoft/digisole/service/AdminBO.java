package com.birlasoft.digisole.service;

/**
 * @author Melvin Mathew
 * 
 * validateAdminPassword(String): Takes a password of type String as input and checks if the 
 * requirement is satisfied. Returns a boolean value depending on the input.
 *
 */
public class AdminBO {

	/**
	 * @param password
	 * @return
	 */
	public static boolean validateAdminPassword(String password) {
		
		if(password == null) {
			
			return false;
		}
		
		if(password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])"
				+ "(?=.*[!@#$%^&_+=()])(?=\\S+$).{8,20}$")) {
			return true;
		}
		
		return false;
	}
	
}
