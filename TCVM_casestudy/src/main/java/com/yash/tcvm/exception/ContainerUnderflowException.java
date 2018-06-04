package com.yash.tcvm.exception;

/**
 * This exception will be thrown when the container will be in underflow
 * condition when the required material for one cup of drink will not meet the
 * expectation.
 * 
 * @author soumya.gupta
 *
 */
public class ContainerUnderflowException extends Exception {

	private static final long serialVersionUID = 1L;

	public ContainerUnderflowException() {

	}

	public ContainerUnderflowException(String errMsg) {
		super(errMsg);

	}
}
