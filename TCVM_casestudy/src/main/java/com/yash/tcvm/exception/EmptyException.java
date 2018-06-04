package com.yash.tcvm.exception;

/**
 * This exception will be thrown when there are empty objects at required
 * functionality.
 * 
 * @author soumya.gupta
 *
 */
public class EmptyException extends Exception {

	private static final long serialVersionUID = 1L;

	public EmptyException(String message) {
		super(message);
	}

}
