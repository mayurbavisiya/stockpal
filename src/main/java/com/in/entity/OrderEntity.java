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

@Entity(name = "order_")
public class OrderEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "ID")
	private Long id;

	// @Column(name = "vendor_id")
	// private Integer vendorId;

	@Column(name = "order_date")
	private Date orderDate;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id")
	private Set<OrderProductEntity> orderProducts;

	@ManyToOne
	VendorEntity vendor;

	public Long getId() {
		return id;
	}

	// public Integer getVendorId() {
	// return vendorId;
	// }

	public Date getOrderDate() {
		return orderDate;
	}

	public void setId(Long id) {
		this.id = id;
	}

	// public void setVendorId(Integer vendorId) {
	// this.vendorId = vendorId;
	// }

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Set<OrderProductEntity> getOrderProducts() {
		return orderProducts;
	}

	public void setOrderProducts(Set<OrderProductEntity> orderProducts) {
		this.orderProducts = orderProducts;
	}

	public VendorEntity getVendor() {
		return vendor;
	}

	public void setVendor(VendorEntity vendor) {
		this.vendor = vendor;
	}

}
