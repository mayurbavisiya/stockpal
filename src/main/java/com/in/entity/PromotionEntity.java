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

@Entity(name = "promotions")
public class PromotionEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "ID")
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "status")
	private Integer statusId;

	@Column(name = "duration")
	private Integer durationId;

	@Column(name = "start_date")
	private Date startDate;

	@Column(name = "end_date")
	private Date endDate;

	@Column(name = "promotion_description")
	private String promotionDesc;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "promotion_id")
	private Set<PromotionTypeEntity> promotionType;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "promotion_id")
	private Set<PromotionTypeTimePeriodEntity> promotionTypeTimePeriod;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "promotion_id")
	private Set<PromotionTypeProductMapEntity> promotionTypeProductMap;

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Integer getStatusId() {
		return statusId;
	}

	public Integer getDurationId() {
		return durationId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	public void setDurationId(Integer durationId) {
		this.durationId = durationId;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getPromotionDesc() {
		return promotionDesc;
	}

	public void setPromotionDesc(String promotionDesc) {
		this.promotionDesc = promotionDesc;
	}

	public Set<PromotionTypeEntity> getPromotionType() {
		return promotionType;
	}

	public Set<PromotionTypeTimePeriodEntity> getPromotionTypeTimePeriod() {
		return promotionTypeTimePeriod;
	}

	public Set<PromotionTypeProductMapEntity> getPromotionTypeProductMap() {
		return promotionTypeProductMap;
	}

	public void setPromotionType(Set<PromotionTypeEntity> promotionType) {
		this.promotionType = promotionType;
	}

	public void setPromotionTypeTimePeriod(Set<PromotionTypeTimePeriodEntity> promotionTypeTimePeriod) {
		this.promotionTypeTimePeriod = promotionTypeTimePeriod;
	}

	public void setPromotionTypeProductMap(Set<PromotionTypeProductMapEntity> promotionTypeProductMap) {
		this.promotionTypeProductMap = promotionTypeProductMap;
	}

}
