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
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

@Entity(name = "product")
public class ProductEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "ID")
	private Long id;

	@Column(name = "pos_flag")
	private Integer posFlag;

	@Column(name = "image_url")
	private String imageURL;

	@Column(name = "name")
	private String name;

	@Column(name = "category")
	private Integer category;

	@Column(name = "barcode")
	private String barCode;

	@Column(name = "SKU")
	private String SKU;

	@Column(name = "product_desc")
	private String description;

	@Column(name = "size")
	private Integer size;

	@Column(name = "volume")
	private Integer volume;

	@Column(name = "created_date")
	private Date createdDate;

	@Column(name = "is_active")
	private Integer isActive;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Set<ProductPriceEntity> productPriceEntity;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Set<ProductStockEntity> productStockEntity;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Set<ProductOtherDetailsEntity> productOtherDetailsEntity;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "product_id")
	private Set<ProductPackEntity> productPackEntity;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "product_id")
	private Set<ProductTagsEntity> productTags;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "product_id")
	private Set<OrderProductEntity> orderProductEntity;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "product_id")
	private Set<InvoiceProductEntity> invoiceProductEntity;

	public Long getId() {
		return id;
	}

	public Integer getPosFlag() {
		return posFlag;
	}

	public String getName() {
		return name;
	}

	public Integer getCategory() {
		return category;
	}

	public String getBarCode() {
		return barCode;
	}

	public String getSKU() {
		return SKU;
	}

	public String getDesc() {
		return description;
	}

	public Integer getSize() {
		return size;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setPosFlag(Integer posFlag) {
		this.posFlag = posFlag;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public void setSKU(String sKU) {
		SKU = sKU;
	}

	public void setDesc(String desc) {
		this.description = desc;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Set<ProductPriceEntity> getProductPriceEntity() {
		return productPriceEntity;
	}

	public Set<ProductStockEntity> getProductStockEntity() {
		return productStockEntity;
	}

	public Set<ProductOtherDetailsEntity> getProductOtherDetailsEntity() {
		return productOtherDetailsEntity;
	}

	public Set<ProductPackEntity> getProductPackEntity() {
		return productPackEntity;
	}

	public Set<ProductTagsEntity> getProductTags() {
		return productTags;
	}

	public void setProductPriceEntity(Set<ProductPriceEntity> productPriceEntity) {
		this.productPriceEntity = productPriceEntity;
	}

	public void setProductStockEntity(Set<ProductStockEntity> productStockEntity) {
		this.productStockEntity = productStockEntity;
	}

	public void setProductOtherDetailsEntity(Set<ProductOtherDetailsEntity> productOtherDetailsEntity) {
		this.productOtherDetailsEntity = productOtherDetailsEntity;
	}

	public void setProductPackEntity(Set<ProductPackEntity> productPackEntity) {
		this.productPackEntity = productPackEntity;
	}

	public void setProductTags(Set<ProductTagsEntity> productTags) {
		this.productTags = productTags;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public Integer getVolume() {
		return volume;
	}

	public void setVolume(Integer volume) {
		this.volume = volume;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getDescription() {
		return description;
	}

	public Integer getIsActive() {
		return isActive;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}

}
