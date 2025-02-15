package com.web.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Entity
@Table(name="category_table")
public class CategoryEntity {
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private Long limit;
	private String status;
	public CategoryEntity(String name, Long limit, String status) {
		super();
		this.name = name;
		this.limit = limit;
		this.status = status;
	}
	
	
}
