package com.in.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity(name = "tax")
public class TaxEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "ID")
	private Long id;

	@Column(name = "ratename")
	private String rateName;

	@Column(name = "amount")
	private Double amount;

	@Column(name = "created_date")
	private Date createdDate;

	@Column(name = "updated_date")
	private Date updatedDate;

	@Column(name = "flag")
	private Integer flag;

	@Column(name = "is_default")
	private Integer isDefault;

	@ManyToOne
	TaxTypesLookupEntity taxType;

	public Long getId() {
		return id;
	}

	public String getRateName() {
		return rateName;
	}

	public Double getAmount() {
		return amount;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setRateName(String rateName) {
		this.rateName = rateName;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public TaxTypesLookupEntity getTaxType() {
		return taxType;
	}

	public void setTaxType(TaxTypesLookupEntity taxType) {
		this.taxType = taxType;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public Integer getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}

}
