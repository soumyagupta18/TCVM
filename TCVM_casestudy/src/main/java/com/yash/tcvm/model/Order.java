package com.yash.tcvm.model;

import java.util.Date;

import com.yash.tcvm.enumeration.Drink;

/**
 * This class represents the order entity and its primary function is to
 * transfer the data between the layers.
 * 
 * @author soumya.gupta
 *
 */
public class Order {

	private int quantity;

	private Drink drink;

	private Date orderDate;

	private Boolean status;

	public Order() {
		// TODO Auto-generated constructor stub
	}

	public Order(int quantity, Drink drink, Boolean status) {
		super();
		this.quantity = quantity;
		this.drink = drink;
		this.status = status;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Drink getDrink() {
		return drink;
	}

	public void setDrink(Drink drink) {
		this.drink = drink;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

}
