package com.in.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.in.bean.APIResponse;
import com.in.exception.ValidationException;
import com.in.request.dto.InvoiceReqDTO;

@Service
@Transactional
public interface InvoiceService {

	APIResponse saveInvoice(InvoiceReqDTO dto) throws ValidationException;

	APIResponse deleteInvoice(Long id) throws ValidationException;

	APIResponse getAllInvoice(Pageable pageable, String fromDate, String toDate, String invoiceId);

	APIResponse getInvoiceById(Long id) throws ValidationException;

}
