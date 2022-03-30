package com.in.scheduler;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.in.entity.NotificationEntity;
import com.in.repository.NotificationRepository;
import com.in.repository.OrderRepository;
import com.in.serviceimpl.PushNotificationServiceImpl;
import com.in.util.Utility;

@Service
public class PlaceOrderToVendorSchedulerImpl {

	private static final Logger logger = LogManager.getLogger(PlaceOrderToVendorSchedulerImpl.class);

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	NotificationRepository notificationRepository;

	@Autowired
	PushNotificationServiceImpl pushNotification;

	public void execute() {
		try {

			List<Object[]> orderList = orderRepository.getOrderDetailsTobePlacedtoVendor();
			if (!orderList.isEmpty()) {
				for (Object[] obj : orderList) {
					if (obj[0] != null) {
						saveNotification(String.valueOf(obj[0]), obj[1] != null ? String.valueOf(obj[1]) : "");
					}
				}
			}
		} catch (Exception e) {
			logger.error(e);
		}

	}

	private void saveNotification(String notificationDesc, String vendorName) {
		notificationDesc = notificationDesc.replaceAll("-", ", ");
		NotificationEntity notification = new NotificationEntity();
		notification.setCreatedDate(new Date());
		notification.setNotificationType(Integer.parseInt(Utility.getFCMProperty("place.order.to.vendor.noti.type")));
		notification.setTitle(Utility.getFCMProperty("place.order.to.vendor.noti.title") + " " + vendorName);
		notification.setNotificationDesc(notificationDesc);
		notificationRepository.save(notification);

		// Push Notification
		pushNotification.pushNotification(
				Integer.parseInt(String.valueOf(Utility.getFCMProperty("place.order.to.vendor.noti.type"))),
				String.valueOf(Utility.getFCMProperty("place.order.to.vendor.noti.title") + " " + vendorName),
				notificationDesc);

	}
}
