package com.in.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity(name = "pos_order_items")
public class POSOrderItemsEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "ID")
	private Long id;

	@Column(name = "product_id")
	private Integer productId;

	@Column(name = "promotion_id")
	private Integer promotionId;

	@Column(name = "qty")
	private Integer qty;

	@Column(name = "is_pack")
	private Integer isPack;

	@Column(name = "pack_id")
	private Integer packId;

	@Column(name = "total")
	private Double total;

	@Column(name = "tax")
	private Double tax;

	@Column(name = "profit")
	private Double profit;

	@Column(name = "loss")
	private Double loss;

	@Column(name = "promotion_amount")
	private Double promotionAmount;

	@Column(name = "sale_date")
	private Date saleDate;

	@ManyToOne
	private POSOrderEntity posOrder;

	public Long getId() {
		return id;
	}

	public Integer getProductId() {
		return productId;
	}

	public Integer getPromotionId() {
		return promotionId;
	}

	public Integer getQty() {
		return qty;
	}

	public Integer getIsPack() {
		return isPack;
	}

	public Integer getPackId() {
		return packId;
	}

	public Double getTotal() {
		return total;
	}

	public Double getTax() {
		return tax;
	}

	public Double getProfit() {
		return profit;
	}

	public Double getLoss() {
		return loss;
	}

	public Double getPromotionAmount() {
		return promotionAmount;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public void setPromotionId(Integer promotionId) {
		this.promotionId = promotionId == null ? 0 : promotionId;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public void setIsPack(Integer isPack) {
		this.isPack = isPack;
	}

	public void setPackId(Integer packId) {
		this.packId = packId;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public void setTax(Double tax) {
		this.tax = tax;
	}

	public void setProfit(Double profit) {
		this.profit = profit;
	}

	public void setLoss(Double loss) {
		this.loss = loss;
	}

	public void setPromotionAmount(Double promotionAmount) {
		this.promotionAmount = promotionAmount;
	}

	public POSOrderEntity getPosOrder() {
		return posOrder;
	}

	public void setPosOrder(POSOrderEntity posOrder) {
		this.posOrder = posOrder;
	}

	public Date getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(Date saleDate) {
		this.saleDate = saleDate;
	}

}
