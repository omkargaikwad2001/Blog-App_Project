package com.blog.services.impl;

import java.lang.module.ResolutionException;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blog.entities.User;
import com.blog.payloads.UserDto;
import com.blog.repository.UserRepo;
import com.blog.services.UserService;
import com.blog.exception.*;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDto createUser(UserDto userDto) {

		User user = this.dtoToUser(userDto);

		User savedUser = this.userRepo.save(user);

		return this.userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {

		User user1 = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

		user1.setName(userDto.getName());
		user1.setEmail(userDto.getEmail());
		user1.setPassword(userDto.getPassword());
		user1.setAbout(userDto.getAbout());

		User updatedUser = this.userRepo.save(user1);
		UserDto userDto1 = this.userToDto(updatedUser);

		return userDto1;
	}

	@Override
	public UserDto getUserById(Integer userId) {

		User user1 = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

		return this.userToDto(user1);
	}

	@Override
	public List<UserDto> getAllUser() {

		List<User> users = this.userRepo.findAll();

		List<UserDto> userDtos = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());

		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {

		User user1 = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
		this.userRepo.delete(user1);
	}

//	public User dtoToUser(UserDto userDto) {
//
//		User user = new User();
//
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setPassword(userDto.getPassword());
//		user.setAbout(userDto.getAbout());
//
//		return user;
//	}
	
	public User dtoToUser(UserDto userDto) {

		User user = this.modelMapper.map(userDto, User.class);
		return user;
	}

//	public UserDto userToDto(User user) {
//
//		UserDto userDto = new UserDto();
//
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setEmail(user.getEmail());
//		userDto.setPassword(user.getPassword());
//		userDto.setAbout(user.getAbout());
//
//		return userDto;
//	}
	
	public UserDto userToDto(User user) {

		UserDto userDto = this.modelMapper.map(user, UserDto.class);
		return userDto;
	}


}
