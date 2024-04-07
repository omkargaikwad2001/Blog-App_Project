package com.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.UserDto;
import com.blog.services.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	//post - create user
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
		UserDto createUserDto = this.userService.createUser(userDto);
		return new ResponseEntity<>(createUserDto,HttpStatus.CREATED);
	}
	
	//put - update user
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable("userId") Integer uid){
		UserDto updatedUser = this.userService.updateUser(userDto,uid);
		return ResponseEntity.ok(updatedUser);
	}
	
	//delete
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer uid){
		this.userService.deleteUser(uid);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted successfully",true),HttpStatus.OK);
	}
	
	//get all users
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUsers(){
		return ResponseEntity.ok(this.userService.getAllUser());
	}
	
//	get user by id
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getSingleUser(@PathVariable("userId") Integer uid){
		return ResponseEntity.ok(this.userService.getUserById(uid));
	}
}
