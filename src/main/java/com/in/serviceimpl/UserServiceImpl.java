package com.in.serviceimpl;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.in.bean.APIResponse;
import com.in.entity.ForgotPasswordEntity;
import com.in.entity.UserEntity;
import com.in.exception.ValidationException;
import com.in.repository.ForgotPasswordRepository;
import com.in.repository.UserRepository;
import com.in.request.dto.UserReqDTO;
import com.in.response.dto.ForgotPasswordResDTO;
import com.in.response.dto.JwtResponse;
import com.in.response.dto.UserResDTO;
import com.in.service.UserService;
import com.in.util.Utility;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Autowired
	ForgotPasswordRepository forgotPasswordRepository;

	@Override
	public APIResponse getAllUsers(Pageable pageable) {

		Page<UserEntity> userPages = userRepository.findAll(pageable);
		List<UserEntity> enList = userPages.getContent();
		Integer totalPages = userPages.getTotalPages();
		Integer pageRecords = userPages.getNumberOfElements();
		Integer totalRecords = (int) userPages.getTotalElements();

		List<UserResDTO> userList = enList.stream().map(x -> {
			UserResDTO dto = new UserResDTO();
			BeanUtils.copyProperties(x, dto);
			return dto;
		}).collect(Collectors.toList());
		APIResponse response = new APIResponse("successCode", "successMsg", "successBooleanTrue");
		response.setUsers(userList);
		response.setTotalPages(totalPages);
		response.setTotalRecords(totalRecords);
		response.setPageRecords(pageRecords);
		return response;
	}

	@Override
	public APIResponse updateUser(Long id, UserReqDTO user) throws ValidationException {
		UserEntity userEn = userRepository.findById(id).orElseThrow(
				() -> new ValidationException("dataNotFoundCode", "UserNotFoundDesc", "successBooleanFalse"));

		if (user.getUserName() != null) {
			UserEntity users = userRepository.findByUsername(user.getUserName().trim());
			if (users != null && users.getId() != id)
				throw new ValidationException("dataAlreadyExistCode", "userNameAlreadyExistDesc",
						"successBooleanFalse");
		}

		if (user.getFullName() != null)
			userEn.setFullName(user.getFullName());
		if (user.getUserName() != null)
			userEn.setUsername(user.getUserName());
		if (user.getUserType() != null)
			userEn.setUsertype(user.getUserType());
		if (user.getPassword() != null)
			userEn.setPassword(bcryptEncoder.encode(user.getPassword()));
		// if (user.getPassword() != null)
		// userEn.setPassword((user.getPassword()));
		userRepository.save(userEn);

		UserResDTO userDTO = new UserResDTO();
		BeanUtils.copyProperties(userEn, userDTO);
		JwtResponse JwtResponse = new JwtResponse("",userEn.getUsername(),userEn.getFullName(),userEn.getUsertype(),userEn.getId());
		APIResponse response = new APIResponse("successCode", "successMsg", "successBooleanTrue");
		response.setJwtDetails(JwtResponse);
		return response;
	}

	@Override
	public APIResponse saveUser(UserReqDTO user) throws ValidationException {

		UserEntity entity = userRepository.findByUsername(user.getUserName());
		if (entity != null)
			throw new ValidationException("dataAlreadyExistCode", "userNameAlreadyExistDesc", "successBooleanFalse");

		UserEntity newUser = new UserEntity();
		newUser.setFullName(user.getFullName());
		newUser.setUsername(user.getUserName());
		newUser.setUsertype(user.getUserType());
		newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		// newUser.setPassword((user.getPassword()));
		userRepository.save(newUser);
		APIResponse response = new APIResponse("successCode", "successMsg", "successBooleanTrue");
		return response;
	}

	@Override
	public APIResponse forgotPassword(String userName) throws ValidationException {
		UserEntity entity = userRepository.findByUsername(userName.trim());
		if (entity == null)
			throw new ValidationException("dataAlreadyExistCode", "userNameNotExistDesc", "successBooleanFalse");

		String token = Utility.uniqueCurrentTimeMS();
		if (token == null) {
			throw new ValidationException("dataAlreadyExistCode", "TokenGenerationError", "successBooleanFalse");
		}

		// Update all the previous token to expired
		forgotPasswordRepository.updateAllPreviousToken(userName.trim());
		ForgotPasswordEntity en = new ForgotPasswordEntity();
		en.setTokenExpired(0);
		en.setToken(token);
		en.setUserName(userName);
		en.setTokenRequestTime(new Date());
		forgotPasswordRepository.save(en);

		ForgotPasswordResDTO responseDTO = new ForgotPasswordResDTO();
		BeanUtils.copyProperties(en, responseDTO);
		responseDTO.setTokenRequestTime(Utility.getStringFromDate(en.getTokenRequestTime(), true));
		responseDTO.setPasswordUpdateTime(Utility.getStringFromDate(en.getPasswordUpdateTime(), true));

		APIResponse response = new APIResponse("successCode", "successMsg", "successBooleanTrue");
		response.setForgotPassword(responseDTO);
		return response;
	}

	@Override
	public APIResponse updatePassword(String token, String newPassword) throws ValidationException {
		ForgotPasswordEntity entity = forgotPasswordRepository.findDataByToken(token);
		if (entity == null) {
			throw new ValidationException("dataNotFoundCode", "inValidTokenDesc", "successBooleanTrue");
		}

		Date tokenGenerateTime = entity.getTokenRequestTime();
		long diffInMillies = Math.abs(new Date().getTime() - tokenGenerateTime.getTime());
		long diff = TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);
		if (diff > 5 || entity.getTokenExpired() > 0) {
			entity.setTokenExpired(1);
			forgotPasswordRepository.save(entity);
			throw new ValidationException("errorCode", "tokenExpiredDesc", "successBooleanTrue");
		}
		UserEntity user = userRepository.findByUsername(entity.getUserName());
		if (user == null)
			throw new ValidationException("dataAlreadyExistCode", "userNameNotExistDesc", "successBooleanFalse");

		user.setPassword(bcryptEncoder.encode(user.getPassword()));
		// user.setPassword(newPasssword);
		userRepository.save(user);

		entity.setTokenExpired(1);
		entity.setPasswordUpdateTime(new Date());
		APIResponse response = new APIResponse("successCode", "successMsg", "successBooleanTrue");
		return response;
	}

	@Override
	public APIResponse deleteUser(Long id) throws ValidationException {
		UserEntity userEn = userRepository.findById(id).orElseThrow(
				() -> new ValidationException("dataNotFoundCode", "UserNotFoundDesc", "successBooleanFalse"));
		userRepository.delete(userEn);
		APIResponse response = new APIResponse("successCode", "successMsg", "successBooleanTrue");
		return response;
	}
}
