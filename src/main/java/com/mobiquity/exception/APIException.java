package com.mobiquity.exception;

/**
 * @author Kiran Kumar Samala
 * 
 *  Any exception in ItemPackage Module is wrapped by APIException 
 */
public class APIException extends RuntimeException {

	public APIException(String message) {
		super(message);
	}
	
	public APIException(Throwable cause) {
        super(cause);
    }
}
