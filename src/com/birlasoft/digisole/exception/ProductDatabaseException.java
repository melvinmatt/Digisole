package com.birlasoft.digisole.exception;

/**
 * @author Melvin Mathew
 * 
 * This class governs the ProductDAO class exceptions and the ProductMain class exceptions
 *
 */
@SuppressWarnings("serial")
public class ProductDatabaseException extends Exception{

	public ProductDatabaseException(String msg) {
		
		super(msg);
	}
	
	public ProductDatabaseException(Throwable e) {
		
		super(e);
	}
	
}
