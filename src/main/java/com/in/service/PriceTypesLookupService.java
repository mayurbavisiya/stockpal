package com.in.service;

import org.springframework.stereotype.Service;

import com.in.bean.APIResponse;

@Service
public interface PriceTypesLookupService {

	APIResponse getPriceTypes();

}
