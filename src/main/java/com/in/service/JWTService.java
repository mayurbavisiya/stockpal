package com.in.service;

import org.springframework.stereotype.Service;

import com.in.bean.APIResponse;
import com.in.request.dto.JwtRequest;

@Service
public interface JWTService {

	APIResponse createAuthenticationToken(JwtRequest authenticationRequest) throws Exception;

}
