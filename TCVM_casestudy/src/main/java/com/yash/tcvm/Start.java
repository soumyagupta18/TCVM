package com.yash.tcvm;

import com.yash.tcvm.exception.EmptyException;
/**
 * This class is the Application Startup class.
 * @author soumya.gupta
 *
 */
public class Start {
	
	public static void main(String[] args) throws EmptyException {
		TCVM.getTCVM().start();
	}

}
