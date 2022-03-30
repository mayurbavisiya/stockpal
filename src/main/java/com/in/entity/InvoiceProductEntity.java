package com.in.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity(name = "invoice_product")
public class InvoiceProductEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "i_p_id")
	private Long id;

	// @Column(name = "product_id")
	// private Integer productId;

	@Column(name = "received_qty")
	private Integer receivedQty;

	@Column(name = "cost_price")
	private Double costPrice;

	@Column(name = "margin")
	private Double margin;

	@Column(name = "selling_price")
	private Double sellingPrice;

	@ManyToOne
	InvoiceEntity invoice;

	@ManyToOne
	ProductEntity product;

	public Long getId() {
		return id;
	}

	// public Integer getProductId() {
	// return productId;
	// }

	public Integer getReceivedQty() {
		return receivedQty;
	}

	public Double getCostPrice() {
		return costPrice;
	}

	public Double getMargin() {
		return margin;
	}

	public Double getSellingPrice() {
		return sellingPrice;
	}

	public void setId(Long id) {
		this.id = id;
	}

	// public void setProductId(Integer productId) {
	// this.productId = productId;
	// }

	public void setReceivedQty(Integer receivedQty) {
		this.receivedQty = receivedQty;
	}

	public void setCostPrice(Double costPrice) {
		this.costPrice = costPrice;
	}

	public void setMargin(Double margin) {
		this.margin = margin;
	}

	public void setSellingPrice(Double sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	public InvoiceEntity getInvoice() {
		return invoice;
	}

	public void setInvoice(InvoiceEntity invoice) {
		this.invoice = invoice;
	}

	public ProductEntity getProduct() {
		return product;
	}

	public void setProduct(ProductEntity product) {
		this.product = product;
	}

}
