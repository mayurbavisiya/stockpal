package com.in.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity(name = "product_tags")
public class ProductTagsEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "p_t_id")
	private Long id;

	@Column(name = "tag_desc")
	private String desc;

	@ManyToOne
	ProductEntity product;

	public Long getId() {
		return id;
	}

	public String getDesc() {
		return desc;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public ProductEntity getProduct() {
		return product;
	}

	public void setProduct(ProductEntity product) {
		this.product = product;
	}

}
