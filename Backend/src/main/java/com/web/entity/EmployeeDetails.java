package com.web.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "season2_batch2_team3_employee_ems")
public class EmployeeDetails {
	@Id
	private String associateId;//(userId)
	
	private String email;
	
	private String name;
	
	private LocalDate birthDate;
	
	private String managerId;
	
	private String workerId; //(password)
	
	private String designation;
	
	private Long fundsReleased=0L;
	
	private Long fundsAllocated=100000L;

	public EmployeeDetails(String email, String name, LocalDate birthDate, String managerId, String workerId,
			String designation, Long fundsReleased, Long fundsAllocated) {
		super();
		this.email = email;
		this.name = name;
		this.birthDate = birthDate;
		this.managerId = managerId;
		this.workerId = workerId;
		this.designation = designation;
		this.fundsReleased = fundsReleased;
		this.fundsAllocated = fundsAllocated;
	}
		
	
	
}
