package com.in.response.dto;

public class ForgotPasswordResDTO {
	private Long id;
	private String userName;
	private Integer tokenExpired;
	private String token;
	private String tokenRequestTime;
	private String passwordUpdateTime;

	public Long getId() {
		return id;
	}

	public String getUserName() {
		return userName;
	}

	public Integer getTokenExpired() {
		return tokenExpired;
	}

	public String getToken() {
		return token;
	}

	public String getTokenRequestTime() {
		return tokenRequestTime;
	}

	public String getPasswordUpdateTime() {
		return passwordUpdateTime;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setTokenExpired(Integer tokenExpired) {
		this.tokenExpired = tokenExpired;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setTokenRequestTime(String tokenRequestTime) {
		this.tokenRequestTime = tokenRequestTime;
	}

	public void setPasswordUpdateTime(String passwordUpdateTime) {
		this.passwordUpdateTime = passwordUpdateTime;
	}

}
