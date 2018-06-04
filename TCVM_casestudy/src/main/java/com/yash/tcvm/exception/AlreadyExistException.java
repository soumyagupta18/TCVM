package com.yash.tcvm.exception;

/**
 * This exception will be thrown when object already exists.
 * 
 * @author soumya.gupta
 *
 */
public class AlreadyExistException extends Exception {

	private static final long serialVersionUID = 1L;

	public AlreadyExistException(String message) {
		super(message);
	}
}
