package com.in.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

@Entity(name = "invoice")
public class InvoiceEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "ID")
	private Long id;

	@Column(name = "invoice_number")
	private String invoiceNumber;

//	@Column(name = "vendor_id")
//	private Integer vendorId;

	@Column(name = "order_id")
	private Integer orderId;

	@Column(name = "invoice_date")
	private Date invoiceDate;

	@Column(name = "due_date")
	private Date dueDate;

	@Column(name = "invoice_photo_url")
	private String invoicePhotoUrl;

	@Column(name = "created_date")
	private Date createdDate;

	@Column(name = "is_active")
	private Integer isActive;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "invoice_id")
	private Set<InvoiceProductEntity> invoiceProductEntity;

	@ManyToOne
	VendorEntity vendor;

	public Long getId() {
		return id;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

//	public Integer getVendorId() {
//		return vendorId;
//	}

	public Integer getOrderId() {
		return orderId;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public String getInvoicePhotoUrl() {
		return invoicePhotoUrl;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	// public void setVendorId(Integer vendorId) {
	// this.vendorId = vendorId;
	// }

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public void setInvoicePhotoUrl(String invoicePhotoUrl) {
		this.invoicePhotoUrl = invoicePhotoUrl;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Set<InvoiceProductEntity> getInvoiceProductEntity() {
		return invoiceProductEntity;
	}

	public void setInvoiceProductEntity(Set<InvoiceProductEntity> invoiceProductEntity) {
		this.invoiceProductEntity = invoiceProductEntity;
	}

	public Integer getIsActive() {
		return isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}

	public VendorEntity getVendor() {
		return vendor;
	}

	public void setVendor(VendorEntity vendor) {
		this.vendor = vendor;
	}

}
