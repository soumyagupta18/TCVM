package com.yash.tcvm.dao;

import java.io.FileNotFoundException;
import java.util.List;

import com.yash.tcvm.enumeration.Drink;
import com.yash.tcvm.exception.EmptyException;
import com.yash.tcvm.model.Order;

/**
 * This interface defines the order functionality that needs to be implemented
 * into the TCVM.
 * 
 * @author soumya.gupta
 *
 */
public interface OrderDao {

	List<Order> getOrders() throws FileNotFoundException, EmptyException;

	int insertOrder(Order order) throws EmptyException, FileNotFoundException;

	List<Order> getOrdersByDrink(Drink drink) throws FileNotFoundException;

}
