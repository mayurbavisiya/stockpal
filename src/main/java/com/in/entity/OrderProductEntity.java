package com.in.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity(name = "order_product")
public class OrderProductEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "o_p_id")
	private Long id;

//	@Column(name = "product_id")
//	private Integer productId;

	@Column(name = "reorder_qty")
	private Integer reorderQty;

	@Column(name = "sgsted_reorder_qty")
	private Integer suggestedReOrderQty;

	@ManyToOne
	private OrderEntity order;

	@ManyToOne
	private ProductEntity product;

	public Long getId() {
		return id;
	}

//	public Integer getProductId() {
//		return productId;
//	}

	public Integer getReorderQty() {
		return reorderQty;
	}

	public OrderEntity getOrder() {
		return order;
	}

	public void setId(Long id) {
		this.id = id;
	}

//	public void setProductId(Integer productId) {
//		this.productId = productId;
//	}

	public void setReorderQty(Integer reorderQty) {
		this.reorderQty = reorderQty;
	}

	public void setOrder(OrderEntity order) {
		this.order = order;
	}

	public Integer getSuggestedReOrderQty() {
		return suggestedReOrderQty;
	}

	public void setSuggestedReOrderQty(Integer suggestedReOrderQty) {
		this.suggestedReOrderQty = suggestedReOrderQty;
	}

	public ProductEntity getProduct() {
		return product;
	}

	public void setProduct(ProductEntity product) {
		this.product = product;
	}

	
}
