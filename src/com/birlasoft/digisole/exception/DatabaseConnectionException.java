package com.birlasoft.digisole.exception;

/**
 * @author Melvin Mathew
 * 
 * This class governs the exception thrown by the Connection interface of the Oracle Database
 * 
 * driver. This exception is thrown to all the connection access modules.
 *
 */
@SuppressWarnings("serial")
public class DatabaseConnectionException extends Exception {

	public DatabaseConnectionException(String msg) {
		
		super(msg);
	}
	
	public DatabaseConnectionException(Throwable e) {
		
		super(e);
	}
	
}
