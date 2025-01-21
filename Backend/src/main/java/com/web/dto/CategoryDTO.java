package com.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CategoryDTO {
	private Long id;
	private String name;
	private Long limit;
	private String status;
	
	public CategoryDTO(String name, Long limit, String status) {
		super();
		this.name = name;
		this.limit = limit;
		this.status = status;
	}
	
	
}

