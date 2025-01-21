package com.web.dto;


import java.time.LocalDate;

import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
public class ExpenseDto {
	
    private Long expenseId;
	
	private String associateId;
	
	private Long categoryId;
	
	private String category;
	
	private Long amount;
	
	private String managerId;
	
	private String status;
	
	private String managerComments;
	
	private String employeeComments;
	
	private LocalDate submissionDate;
	
	private String businessPurpose;
	
	private LocalDate approvedDate;
	
	
	@Lob
	private byte[] receipt;
	
}

