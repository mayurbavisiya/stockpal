package com.in.restapis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.in.bean.APIResponse;
import com.in.exception.ValidationException;
import com.in.service.DashBoardService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class DashBoardAPI {

	@Autowired
	DashBoardService dashBoardService;

	@GetMapping("/dashboard")
	public ResponseEntity<APIResponse> getDashBoard(@RequestParam(required = false) String filter,
			@RequestParam(required = false) String fromDate, @RequestParam(required = false) String toDate)
			throws ValidationException {
		return ResponseEntity.ok().body(dashBoardService.getDashBoard(filter, fromDate, toDate));
	}

	@GetMapping("/dashboard/graph/sale")
	public ResponseEntity<APIResponse> getSaleDataInGraph(@RequestParam(required = false) String filter,
			@RequestParam(required = false) String fromDate, @RequestParam(required = false) String toDate,
			@RequestParam(required = false) Integer vendorId) throws ValidationException {
		return ResponseEntity.ok().body(dashBoardService.getSaleDataInGraph(filter, fromDate, toDate, vendorId));
	}

	@GetMapping("/dashboard/graph/transaction")
	public ResponseEntity<APIResponse> getTransactionDataInGraph(@RequestParam(required = false) String filter)
			throws ValidationException {
		return ResponseEntity.ok().body(dashBoardService.getTransactionDataInGraph(filter));
	}

}
