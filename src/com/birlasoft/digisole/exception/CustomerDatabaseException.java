package com.birlasoft.digisole.exception;

/**
 * @author Melvin Mathew
 * 
 * This class governs the CustomerClient class exception and also in the CustomerDAO class
 *
 */
@SuppressWarnings("serial")
public class CustomerDatabaseException extends Exception {

	public CustomerDatabaseException(String msg) {
		
		super(msg);		
	}
	
	public CustomerDatabaseException(Throwable e) {
		
		super(e);
	}
	
}
