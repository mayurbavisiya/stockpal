package com.in.service;

import org.springframework.stereotype.Service;

import com.in.bean.APIResponse;

@Service
public interface DashBoardService {

	APIResponse getDashBoard(String filter, String fromDate, String toDate);

	APIResponse getSaleDataInGraph(String filter, String fromDate, String toDate, Integer vendorId);

	APIResponse getTransactionDataInGraph(String filter);

}
