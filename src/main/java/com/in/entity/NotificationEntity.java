package com.in.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity(name = "notification")
public class NotificationEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "ID")
	private Long id;

	@Column(name = "notification_type")
	private Integer notificationType;

	@Column(name = "title")
	private String title;

	@Column(name = "notification_desc")
	private String notificationDesc;

	@Column(name = "created_date")
	private Date createdDate;

	public Long getId() {
		return id;
	}

	public Integer getNotificationType() {
		return notificationType;
	}

	public String getTitle() {
		return title;
	}

	public String getNotificationDesc() {
		return notificationDesc;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setNotificationType(Integer notificationType) {
		this.notificationType = notificationType;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setNotificationDesc(String notificationDesc) {
		this.notificationDesc = notificationDesc;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

}
