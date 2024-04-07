package com.blog.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {

	private int id;
	
	@NotEmpty
	@Size(min=4, message = "User name must be min 4 charecters!!!")
	private String name;
	
	@Email(message = "Email address not valid!!!")
	private String email;
	
	@NotNull(message = "Must Enter password")
	private String password;
	
	@NotNull(message = "Must Enter something about")
	private String about;
	
}
