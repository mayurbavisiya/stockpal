package com.in.response.dto;

public class UpcomingScheduledOrderResDTO {

	private String vendorId;
	private String vendorName;
	private String email;
	private String fax;

	public String getVendorId() {
		return vendorId;
	}

	public String getVendorName() {
		return vendorName;
	}

	public String getEmail() {
		return email;
	}

	public String getFax() {
		return fax;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

}
