package com.in.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.in.bean.APIResponse;

@Service
@Transactional
public interface LookupService {

	APIResponse getAllScheduleDays();
}
