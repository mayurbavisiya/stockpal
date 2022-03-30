package com.in.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity(name = "product_stock")
public class ProductStockEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "ps_id")
	private Long id;

	@Column(name = "quantity")
	private Integer quantity;

	@Column(name = "reorder_quantity")
	private Integer reorderQuantity;

	@Column(name = "par_level")
	private Integer parLevel;

	@ManyToOne
	private ProductEntity product;

	public Long getId() {
		return id;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public Integer getReorderQuantity() {
		return reorderQuantity;
	}

	public Integer getParLevel() {
		return parLevel;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public void setReorderQuantity(Integer reorderQuantity) {
		this.reorderQuantity = reorderQuantity;
	}

	public void setParLevel(Integer parLevel) {
		this.parLevel = parLevel;
	}

//	public ProductEntity getProduct() {
//		return product;
//	}
//
//	public void setProduct(ProductEntity product) {
//		this.product = product;
//	}

}
