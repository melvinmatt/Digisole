package com.birlasoft.digisole.model;

public class CustomerVO {

	private String cid;
	private String name;
	private int age;
	private String address;
	private String city;
	private String phNo;
	
	public CustomerVO() {
		super();
	}

	public CustomerVO(String cid, String name, int age, String address, String city, String phNo) {
		super();
		this.cid = cid;
		this.name = name;
		this.age = age;
		this.address = address;
		this.city = city;
		this.phNo = phNo;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPhNo() {
		return phNo;
	}

	public void setPhNo(String phNo) {
		this.phNo = phNo;
	}

	@Override
	public String toString() {
		return "Name: " + name +
				"\nAge: " + age +
				"\nContact: " + phNo +
				"\nAddress: " + address + ", " + city;
	}
	
}
