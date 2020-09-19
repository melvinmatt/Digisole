package com.birlasoft.digisole.exception;

/**
 * @author Melvin Mathew
 * 
 *This class governs the exceptions taking place in the AdminMain class and also in the 
 *AdminDAO class
 */
@SuppressWarnings("serial")
public class AdminDatabaseException extends Exception {

	public AdminDatabaseException(String msg) {
		
		super(msg);
	}
	
	public AdminDatabaseException(Throwable e) {
		
		super(e);
	}
	
}
