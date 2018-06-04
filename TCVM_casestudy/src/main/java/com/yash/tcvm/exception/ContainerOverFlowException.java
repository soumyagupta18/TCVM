package com.yash.tcvm.exception;

/**
 * This exception will be occur when container will overflow or increase from
 * maximum capacity of the container.
 * 
 * @author soumya.gupta
 *
 */
public class ContainerOverFlowException extends Exception {

	private static final long serialVersionUID = 1L;

	public ContainerOverFlowException() {

	}

	public ContainerOverFlowException(String errMsg) {
		super(errMsg);
	}

}
