package com.in.response.dto;

public class NotificationResDTO {

	private Long id;
	private Integer notificationType;
	private String title;
	private String notificationDesc;
	private String createdDate;

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

	public String getCreatedDate() {
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

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

}
