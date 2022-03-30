package com.in.response.dto;

import java.io.Serializable;

public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private final String jwttoken;
	private final String userName;
	private final String fullName;
	private final String userType;
	private final Long id;

	public JwtResponse(String jwttoken, String userName, String fullName, String userType, Long id) {
		this.jwttoken = jwttoken;
		this.fullName = fullName;
		this.userName = userName;
		this.userType = userType;
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public String getFullName() {
		return fullName;
	}

	public String getUserType() {
		return userType;
	}

	public String getToken() {
		return this.jwttoken;
	}

	public Long getId() {
		return id;
	}

}
