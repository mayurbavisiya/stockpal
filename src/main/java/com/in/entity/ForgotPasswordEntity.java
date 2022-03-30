package com.in.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity(name = "forgot_password")
public class ForgotPasswordEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "ID")
	private Long id;

	@Column(name = "username")
	private String userName;

	@Column(name = "token_expired")
	private Integer tokenExpired;

	@Column(name = "token")
	private String token;

	@Column(name = "token_request_time")
	private Date tokenRequestTime;

	@Column(name = "password_update_time")
	private Date passwordUpdateTime;

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

	public Date getTokenRequestTime() {
		return tokenRequestTime;
	}

	public Date getPasswordUpdateTime() {
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

	public void setTokenRequestTime(Date tokenRequestTime) {
		this.tokenRequestTime = tokenRequestTime;
	}

	public void setPasswordUpdateTime(Date passwordUpdateTime) {
		this.passwordUpdateTime = passwordUpdateTime;
	}

}
