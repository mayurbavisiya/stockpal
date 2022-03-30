package com.in.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.in.bean.APIResponse;
import com.in.exception.ValidationException;
import com.in.request.dto.UserReqDTO;

@Service
@Transactional
public interface UserService {

	APIResponse getAllUsers(Pageable pageable);

	APIResponse updateUser(Long id, UserReqDTO dto) throws ValidationException;

	APIResponse saveUser(UserReqDTO user) throws ValidationException;

	APIResponse forgotPassword(String userName) throws ValidationException;

	APIResponse updatePassword(String token, String newPassword) throws ValidationException;

	APIResponse deleteUser(Long id) throws ValidationException;

}
