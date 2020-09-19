package com.birlasoft.digisole.model;

public class LoginVO {

	private String cid;
	private String loginID;
	private String password;
	
	public LoginVO() {
		super();
	}

	public LoginVO(String cid, String loginID, String password) {
		super();
		this.cid = cid;
		this.loginID = loginID;
		this.password = password;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
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
	
}
