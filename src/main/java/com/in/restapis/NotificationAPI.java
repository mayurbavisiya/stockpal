package com.in.restapis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.in.bean.APIResponse;
import com.in.exception.ValidationException;
import com.in.service.NotificationService;
import com.in.service.OrderService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class NotificationAPI {

	@Autowired
	OrderService orderService;

	@Autowired
	NotificationService notificationService;

	@GetMapping("/upcomingOrderScheduled")
	public ResponseEntity<APIResponse> getUpcomingOrderScheduled() throws ValidationException {
		return ResponseEntity.ok().body(orderService.getUpcomingOrderScheduled());
	}

	@GetMapping("/getNotifications")
	public ResponseEntity<APIResponse> getNotifications(Pageable pageable) throws ValidationException {
		return ResponseEntity.ok().body(notificationService.getNotifications(pageable));
	}
}
