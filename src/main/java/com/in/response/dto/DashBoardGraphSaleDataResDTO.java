package com.in.response.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class DashBoardGraphSaleDataResDTO {
	@JsonInclude(Include.NON_NULL)
	private String[] hour;

	@JsonInclude(Include.NON_NULL)
	private Object[] data;

	public String[] getHour() {
		return hour;
	}

	public Object[] getData() {
		return data;
	}

	public void setHour(String[] hour) {
		this.hour = hour;
	}

	public void setData(Object[] data) {
		this.data = data;
	}

}
