package com.in.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.in.bean.APIResponse;
import com.in.entity.NotificationEntity;
import com.in.repository.NotificationRepository;
import com.in.response.dto.NotificationResDTO;
import com.in.service.NotificationService;
import com.in.util.Utility;

@Service
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	NotificationRepository notificationRepository;

	@Override
	public APIResponse getNotifications(Pageable pageable) {
		Page<NotificationEntity> notificationsPages = notificationRepository.findAll(pageable);
		List<NotificationEntity> enList = notificationsPages.getContent();
		Integer totalPages = notificationsPages.getTotalPages();
		Integer pageRecords = notificationsPages.getNumberOfElements();
		Integer totalRecords = (int) notificationsPages.getTotalElements();

		List<NotificationResDTO> notificationList = enList.stream().map(x -> {
			NotificationResDTO dto = new NotificationResDTO();
			BeanUtils.copyProperties(x, dto);
			dto.setCreatedDate(Utility.getStringFromDate(x.getCreatedDate(), true));
			return dto;
		}).collect(Collectors.toList());

		APIResponse response = new APIResponse("successCode", "successMsg", "successBooleanTrue");
		response.setNotifications(notificationList);
		response.setTotalPages(totalPages);
		response.setTotalRecords(totalRecords);
		response.setPageRecords(pageRecords);
		return response;
	}
}
