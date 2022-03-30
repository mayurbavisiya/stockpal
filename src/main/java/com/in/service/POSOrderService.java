package com.in.service;

import org.springframework.stereotype.Service;

import com.in.bean.APIResponse;
import com.in.exception.ValidationException;
import com.in.request.dto.POSOrderReqDTO;

@Service
public interface POSOrderService {

	APIResponse savePOSOrder(POSOrderReqDTO dto) throws ValidationException;

}
