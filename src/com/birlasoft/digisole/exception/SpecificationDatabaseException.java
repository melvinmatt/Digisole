package com.birlasoft.digisole.exception;

/**
 * @author Melvin Mathew
 *
 *This class governs the exception in SpecificationDAO class and also in ProductMain class
 *
 */
@SuppressWarnings("serial")
public class SpecificationDatabaseException extends Exception {

	public SpecificationDatabaseException(String msg) {
		
		super(msg);
	}
	
	public SpecificationDatabaseException(Throwable e) {
		
		super(e);
	}
	
}
