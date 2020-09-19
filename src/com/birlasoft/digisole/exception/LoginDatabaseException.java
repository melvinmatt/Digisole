package com.birlasoft.digisole.exception;

/**
 * @author Melvin Mathew
 *
 *This class governs the exception related to the LoginDAO class.
 */
@SuppressWarnings("serial")
public class LoginDatabaseException extends Exception{

	public LoginDatabaseException(String msg) {
		
		super(msg);
	}

	public LoginDatabaseException(Throwable e) {
		
		super(e);
	}
		
}
