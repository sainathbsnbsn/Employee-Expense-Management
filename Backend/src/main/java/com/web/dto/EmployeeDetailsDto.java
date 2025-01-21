package com.web.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmployeeDetailsDto {
	
   private String associateId;//(userId)
	
	
	private String email;
	
	
	private String name;
	
	
	private LocalDate birthDate;
	
	
	private String managerId;
	
	
	private String workerId; //(password)
	
	
	private String designation;
	
private Long fundsReleased=0L;
	
	private Long fundsAllocated=100000L;
}
