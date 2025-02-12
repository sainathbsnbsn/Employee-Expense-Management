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
    @Column(name = "expense_id")  // Maps field to DB column
    private Long expenseId;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "category")
    private String category;

    @Column(name = "amount")
    private Long amount;

    @Column(name = "manager_id")
    private String managerId;

    @Column(name = "associate_id")
    private String associateId;

    @Column(name = "status")
    private String status;

    @Column(name = "manager_comments")
    private String managerComments;

    @Column(name = "employee_comments")
    private String employeeComments;

    @Column(name = "submission_date")
    private LocalDate submissionDate;

    @Column(name = "approved_date")
    private LocalDate approvedDate;

    @Column(name = "business_purpose")
    private String businessPurpose;

    @Lob
    @Column(name = "receipt")
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
