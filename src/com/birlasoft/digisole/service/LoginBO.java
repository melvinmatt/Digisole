package com.birlasoft.digisole.service;

import com.birlasoft.digisole.model.LoginVO;

/**
 * @author Melvin Mathew
 * 
 * LoginBO deals with the validation that needs to be done for successful login
 * 
 * Consists of function:
 * 
 * validateLoginID(LoginVO): Validates the login ID for spaces and minimum character of 4. Returns
 * true or false depending on the validation.
 * 
 * validatePassword(LoginVO): Validates the password for minimum of 8 characters, at least 1 upper-case,
 * 1 lower-case, 1 digits and 1 special character. Returns true or false depending on the validation.
 *
 */
public class LoginBO {

	/**
	 * @param login
	 * @return
	 */
	public static boolean validateLoginID(LoginVO login) {
		
		if(login.getLoginID() == null) {
			
			return false;
		}
		
		if(login.getLoginID().matches("\\s*\\S+\\s*") && login.getLoginID().length() > 4) {
			
			return true;
		}
		
		return false;
	}
	
	/**
	 * @param login
	 * @return
	 */
	public static boolean validatePassword(LoginVO login) {
		
		if(login.getPassword() == null) {
			
			return false;
		}
		
		if(login.getPassword().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])"
				+ "(?=.*[!@#$%^&_+=()])(?=\\S+$).{8,20}$")) {
			return true;
		}
		
		return false;
	}
	
}
