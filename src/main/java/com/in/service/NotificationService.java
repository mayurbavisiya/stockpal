package com.in.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.in.bean.APIResponse;
import com.in.exception.ValidationException;

@Service
public interface NotificationService {

	APIResponse getNotifications(Pageable pageable) throws ValidationException;

}
