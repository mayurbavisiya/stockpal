package com.in.request.dto;

public class UserReqDTO {
	private String userName;
	private String fullName;
	private String password;
	private String userType;

	public String getUserName() {
		return userName;
	}

	public String getFullName() {
		return fullName;
	}

	public String getPassword() {
		return password;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

}
