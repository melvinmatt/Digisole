package com.birlasoft.digisole.model;

public class CustCartVO extends ProductVO {

	public CustCartVO() {
		super();
	}

	public CustCartVO(String orderID, String prodID, double cost, String cid, String status) {
		super(prodID, cost, orderID, cid, status);
	}

	@Override
	public String toString() {
		return "Order ID: " + getOrderID() + "\n" +
				getType() + ": " + getName() + 
				"Cost: " + getCost() + 
				"Ratings: " + getRating();
	}
	
	
	
}
