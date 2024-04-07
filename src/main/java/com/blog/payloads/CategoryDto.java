package com.blog.payloads;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {
	
	
	private Integer categoryId;
	
	@NotBlank(message = "It should not blank")
	private String categoryTitle;
	
	@NotBlank(message = "It should not blank")
	private String categoryDescription;

}
