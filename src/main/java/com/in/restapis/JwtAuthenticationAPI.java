package com.in.restapis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.in.bean.APIResponse;
import com.in.request.dto.JwtRequest;
import com.in.service.JWTService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class JwtAuthenticationAPI {

	@Autowired
	JWTService jWTService;

	@PostMapping(value = "/authenticate")
	public ResponseEntity<APIResponse> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest)
			throws Exception {

		// String jwtToken =
		// "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqaWdzQGdtYWlsLmNvbSIsImlkIjoxLCJ0eXBlIjoic3VwZXJhZG1pbiIsImV4cCI6MTY0NDIwMTk0MywiZnVsbGFtZSI6ImFkbWluIiwiaWF0IjoxNjQzMTkzOTQzLCJ1c2VybmFtZSI6ImppZ3NAZ21haWwuY29tIn0._TeCadHMSh774AUzyf3SGj-LJKi8-W2oHF3QhQCEB64ZRAVn043xZ5ipvli_vLBUAAOYa5_NyLVdeLuA_CyfkA";
		// JwtResponse jwtRes = new JwtResponse(jwtToken, "jigs@gmail.com",
		// "admin", "superadmin", Long.valueOf(String.valueOf(1)));
		// APIResponse response = new APIResponse("successCode", "successMsg",
		// "successBooleanTrue");
		// response.setJwtDetails(jwtRes);
		// return ResponseEntity.ok(response);
		return ResponseEntity.ok(jWTService.createAuthenticationToken(authenticationRequest));
	}

}
