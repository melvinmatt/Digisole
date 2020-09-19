package com.birlasoft.digisole.model;

public class ProductVO implements Comparable<ProductVO>{
	
	private String prodID;
	private String name;
	private String specID;
	private double cost;
	private float rating;
	private String offer;
	private String type;
	private String forType;
	private String orderID;
	private String cid;
	private String status;

	public ProductVO() {
		super();
	}

	public ProductVO(String prodID, String name, String specID, double cost, float rating, String offer, String type,
			String forType, String orderID, String cid, String status) {
		super();
		this.prodID = prodID;
		this.name = name;
		this.specID = specID;
		this.cost = cost;
		this.rating = rating;
		this.offer = offer;
		this.type = type;
		this.forType = forType;
		this.orderID = orderID;
		this.cid = cid;
		this.status = status;
	}

	public ProductVO(String prodID, String name, String specID, double cost, float rating, String offer, String type,
			String forType) {
		super();
		this.prodID = prodID;
		this.name = name;
		this.specID = specID;
		this.cost = cost;
		this.rating = rating;
		this.offer = offer;
		this.type = type;
		this.forType = forType;
	}
	
	public ProductVO(String prodID, double cost, String orderID, String cid, String status) {
		super();
		this.prodID = prodID;
		this.cost = cost;
		this.orderID = orderID;
		this.cid = cid;
		this.status = status;
	}

	public String getProdID() {
		return prodID;
	}

	public void setProdID(String prodID) {
		this.prodID = prodID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSpecID() {
		return specID;
	}

	public void setSpecID(String specID) {
		this.specID = specID;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}
	
	public String getOffer() {
		return offer;
	}

	public void setOffer(String offer) {
		this.offer = offer;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getForType() {
		return forType;
	}

	public void setForType(String forType) {
		this.forType = forType;
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		return Integer.parseInt(this.prodID.substring(2));
	}

	@Override
	public boolean equals(Object obj) {
		ProductVO prod = (ProductVO) obj;
		return this.prodID == prod.prodID;
	}
	
	@Override
	public int compareTo(ProductVO prod) {
		return Integer.parseInt(prod.prodID.substring(2)) - Integer.parseInt(this.prodID.substring(2));
	}
	
	public String toLaptop() {
		return type + ": " + name +
				"\nCost: " + cost +
				"\nRating: " + rating + "/5.0\n";
	}
	
	public String toMobile() {
		return type + ": " + name +
				"\nCost: " + cost +
				"\nRating: " + rating + "/5.0\n";
	}
	
	public String toAcessories() {
		return type + ": " + name +
				"\nFor: " + forType +
				"\nCost: " + cost +
				"\nRating: " + rating + "/5.0\n";
	}

	public String toCart() {
		return type + ": " + name +
				"\nCost: " + cost +
				"\nRating: " + rating + "/5.0\n";
	}
	
	@Override
	public String toString() {
		return "Product [prodID=" + prodID + ", name=" + name + ", specID=" + specID + ", cost=" + cost + ", rating="
				+ rating + ", offer=" + offer + ", type=" + type + ", forType=" + forType + "]";
	}
	
	
	
}
