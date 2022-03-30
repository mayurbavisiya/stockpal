package com.in.entity;

import java.io.Serializable;

public class VendorDaysScheduleEmbededEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7849910505564322732L;
	private Long vendorId;
	private Long dayId;

	public Long getVendorId() {
		return vendorId;
	}

	public void setVendorId(Long vendorId) {
		this.vendorId = vendorId;
	}

	public Long getDayId() {
		return dayId;
	}

	public void setDayId(Long dayId) {
		this.dayId = dayId;
	}

}
