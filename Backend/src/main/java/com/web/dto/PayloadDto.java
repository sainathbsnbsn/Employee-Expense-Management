package com.web.dto;

import java.time.LocalDate;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@NoArgsConstructor
@ToString
@Setter
@Getter

public class PayloadDto {
	private LocalDate fromDate;
	
	private LocalDate toDate;
	
	private Long categoryId;

	public PayloadDto(LocalDate fromDate, LocalDate toDate, Long categoryId) {
		super();
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.categoryId = categoryId;
	}
	
	
}
