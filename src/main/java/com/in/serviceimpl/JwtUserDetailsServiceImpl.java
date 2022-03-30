package com.in.serviceimpl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.in.bean.APIResponse;
import com.in.entity.UserEntity;
import com.in.exception.ValidationException;
import com.in.jwt.JwtTokenUtil;
import com.in.repository.UserRepository;
import com.in.request.dto.JwtRequest;
import com.in.response.dto.JwtResponse;
import com.in.service.JWTService;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService, JWTService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				new ArrayList<>());
	}

	@Override
	public APIResponse createAuthenticationToken(JwtRequest authenticationRequest) throws Exception {
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		final UserDetails userDetails = loadUserByUsername(authenticationRequest.getUsername());
		final JwtResponse jwtRes = jwtTokenUtil.generateToken(userDetails);
		// Update device Id for user
		if (authenticationRequest.getDevice_token() != null && !"".equals(authenticationRequest.getDevice_token())) {
			UserEntity user = userRepository.findByUsername(authenticationRequest.getUsername().trim());
			user.setDeviceToken(authenticationRequest.getDevice_token());
			userRepository.save(user);
		}
		APIResponse response = new APIResponse("successCode", "successMsg", "successBooleanTrue");
		response.setJwtDetails(jwtRes);
		return response;
	}

	private void authenticate(String username, String password) throws ValidationException, Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new ValidationException("userDisabledCode", "userDisabledDesc", "successBooleanFalse");
		} catch (BadCredentialsException e) {
			throw new ValidationException("invalidCredentialsCode", "invalidCredentialsDesc", "successBooleanFalse");
		}
	}

}
