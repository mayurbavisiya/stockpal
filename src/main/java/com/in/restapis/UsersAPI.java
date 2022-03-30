package com.in.restapis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.in.bean.APIResponse;
import com.in.exception.ValidationException;
import com.in.request.dto.UserReqDTO;
import com.in.service.UserService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class UsersAPI {

	@Autowired
	UserService userService;

	@GetMapping("/users")
	public ResponseEntity<APIResponse> getAllUsers(Pageable pageable) throws ValidationException {
		return ResponseEntity.ok().body(userService.getAllUsers(pageable));
	}

	@PutMapping("/user/{id}")
	public ResponseEntity<APIResponse> updateUser(@PathVariable(value = "id") Long id, @RequestBody UserReqDTO dto)
			throws ValidationException {
		return ResponseEntity.ok().body(userService.updateUser(id, dto));
	}

	@DeleteMapping(value = "/user/{id}")
	public ResponseEntity<APIResponse> deleteUser(@PathVariable(value = "id") Long id) throws Exception {
		return ResponseEntity.ok(userService.deleteUser(id));
	}

	@PostMapping(value = "/register")
	public ResponseEntity<APIResponse> saveUser(@RequestBody UserReqDTO user) throws Exception {
		return ResponseEntity.ok(userService.saveUser(user));
	}

	@GetMapping(value = "/forgotpassword")
	public ResponseEntity<APIResponse> forgotPassword(@RequestParam String userName) throws Exception {
		return ResponseEntity.ok(userService.forgotPassword(userName));
	}

	@PutMapping(value = "/updatepassword")
	public ResponseEntity<APIResponse> updatePassword(@RequestParam String token, @RequestParam String newPassword)
			throws Exception {
		return ResponseEntity.ok(userService.updatePassword(token, newPassword));
	}

}
