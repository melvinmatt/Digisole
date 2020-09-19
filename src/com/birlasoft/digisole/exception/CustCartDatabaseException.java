package com.birlasoft.digisole.exception;

/**
 * @author Melvin Mathew
 * 
 * This class governs the exceptions taking place with respect to customer cart(CustCart) operations
 * 
 * It is used in CustCartDAO class
 *
 */
@SuppressWarnings("serial")
public class CustCartDatabaseException extends Exception {

	public CustCartDatabaseException(String msg) {
		
		super(msg);
	}
	
	public CustCartDatabaseException(Throwable e) {
		
		super(e);
	}
	
}
