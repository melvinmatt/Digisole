package com.birlasoft.digisole.model;

public class AdminVO {

	private String adminID;
	private String name;
	private String role;
	private String loginID;
	private String password;
	
	public AdminVO() {
		super();
	}

	public AdminVO(String adminID, String name, String role, String loginID, String password) {
		super();
		this.adminID = adminID;
		this.name = name;
		this.role = role;
		this.loginID = loginID;
		this.password = password;
	}

	public String getAdminID() {
		return adminID;
	}

	public void setAdminID(String adminID) {
		this.adminID = adminID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getLoginID() {
		return loginID;
	}

	public void setLoginID(String loginID) {
		this.loginID = loginID;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "*********Profile*********" +
				"\nAdmin ID: " + adminID +
				"\nName: " + name +
				"\nRole: " + role;
	}
	
}
