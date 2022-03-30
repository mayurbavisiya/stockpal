package com.in.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.in.bean.APIResponse;
import com.in.exception.ValidationException;
import com.in.request.dto.VendorReqDTO;

@Service
@Transactional
public interface VendorService {

	APIResponse getAllVendor(Pageable pageable, String name);

	APIResponse getVendorContainsName(String vendorName);

	APIResponse createVendor(VendorReqDTO dto) throws ValidationException;

	APIResponse updateVendor(Long id, VendorReqDTO dto) throws ValidationException;

	APIResponse deleteVendor(Long id) throws ValidationException;

	APIResponse getvendorById(Long id) throws ValidationException;

}
