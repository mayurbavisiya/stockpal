package com.in.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity(name = "vendors_days_schedule")
@IdClass(VendorDaysScheduleEmbededEntity.class)
public class VendorDaysScheduleEntity implements Serializable {

	private static final long serialVersionUID = -4293809000165786748L;

	@Id
	@Column(name = "vendor_id")
	private Long vendorId;

	@Id
	@Column(name = "day_id")
	private Long dayId;

	@Column(name = "created_date")
	private Date createdDate;

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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

}
