package com.web.entity;

import java.time.LocalDate;

import jakarta.annotation.Generated;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "season2_batch2_team3_expense_ems_test")
public class Expense {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long expenseId;
	
	//private String associateId;
	
	private Long categoryId;
	
	private String category;
	
	private Long amount;
	
	private String managerId;
	
	private String associateId;
	
	private String status;
	
	private String managerComments;
	
	private String employeeComments;
	
	private LocalDate submissionDate;
	
	private LocalDate approvedDate;
	
	private String businessPurpose;
	
	
	
	
	@Lob
	private byte[] receipt;




	public Expense(Long categoryId, String category, Long amount, String managerId, String associateId, String status,
			String managerComments, String employeeComments, LocalDate submissionDate, LocalDate approvedDate,
			String businessPurpose, byte[] receipt) {
		super();
		this.categoryId = categoryId;
		this.category = category;
		this.amount = amount;
		this.managerId = managerId;
		this.associateId = associateId;
		this.status = status;
		this.managerComments = managerComments;
		this.employeeComments = employeeComments;
		this.submissionDate = submissionDate;
		this.approvedDate = approvedDate;
		this.businessPurpose = businessPurpose;
		this.receipt = receipt;
	}


	
}
