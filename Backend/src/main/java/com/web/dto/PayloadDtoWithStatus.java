
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

public class PayloadDtoWithStatus {
	private LocalDate fromDate;
	
	private LocalDate toDate;
	
	private Long categoryId;

	private String status;

	public PayloadDtoWithStatus(LocalDate fromDate, LocalDate toDate, Long categoryId, String status) {
		super();
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.categoryId = categoryId;
		this.status = status;
	}
	
	
	
}
