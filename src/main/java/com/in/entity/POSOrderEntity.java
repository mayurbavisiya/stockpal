package com.in.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

@Entity(name = "pos_order")
public class POSOrderEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "ID")
	private Long id;

	@Column(name = "cust_id")
	private Integer custId;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "pos_order_id")
	private Set<POSOrderItemsEntity> posOrderItems;

	public Long getId() {
		return id;
	}

	public Integer getCustId() {
		return custId;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCustId(Integer custId) {
		this.custId = custId;
	}

	public Set<POSOrderItemsEntity> getPosOrderItems() {
		return posOrderItems;
	}

	public void setPosOrderItems(Set<POSOrderItemsEntity> posOrderItems) {
		this.posOrderItems = posOrderItems;
	}

}
