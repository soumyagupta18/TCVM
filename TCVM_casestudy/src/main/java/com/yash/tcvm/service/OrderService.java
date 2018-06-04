package com.yash.tcvm.service;

import java.io.FileNotFoundException;
import java.util.List;

import com.yash.tcvm.enumeration.Drink;
import com.yash.tcvm.exception.EmptyException;
import com.yash.tcvm.model.Order;
/**
 * This interface defines the business logic that needs to be implemented in the
 * TCVM.
 * @author soumya.gupta
 *
 */
public interface OrderService {

	List<Order> getOrders() throws FileNotFoundException, EmptyException;

	List<Order> getOrdersByDrink(Drink drink) throws FileNotFoundException, EmptyException;

	int addOrder(Order order) throws FileNotFoundException, EmptyException;

}
