package com.yash.tcvm.service;

import java.io.FileNotFoundException;
import java.util.List;

import com.yash.tcvm.enumeration.Ingredient;
import com.yash.tcvm.exception.EmptyException;
import com.yash.tcvm.model.Container;

/**
 * This interface defines the business logic that needs to be implemented in the
 * TCVM.
 * 
 * @author soumya.gupta
 *
 */
public interface ContainerService {

	List<Container> getContainers() throws FileNotFoundException, EmptyException;

	Container getContainer(Ingredient ingredient) throws FileNotFoundException;

	int updateContainer(Container container) throws FileNotFoundException, EmptyException;

}
