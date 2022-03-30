package com.in.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.in.bean.APIResponse;
import com.in.exception.ValidationException;
import com.in.request.dto.InventoryReqDTO;

@Service
@Transactional
public interface InventoryService {

	APIResponse receiveInventory(InventoryReqDTO dto) throws ValidationException;

	APIResponse updateInventory(InventoryReqDTO dto) throws ValidationException;

}
