package com.web.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



class ExpenseDtoTest {

    private ExpenseDto expense = null;

//    InputStream inputStream=this.getClass().getClassLoader().getResourceAsStream("profile.png");
    private byte[] photo;
//    if(inputStream==null) {
//        fail("Unable to get resources");
//    }
     
    @BeforeEach
    void setUp() throws Exception {
        expense = new ExpenseDto(1L,"empId",11L,"Travel",1200L,"managerId","approved","m comments","e comments",LocalDate.of(2023, 10, 05),"bpurpose",LocalDate.of(2023, 10, 10),photo);
    }

    @AfterEach
    void tearDown() throws Exception {
        expense = null;
    }
    
    
    @Test
    void testGetExpenseId() {
        assertEquals(1L,expense.getExpenseId());
    }
    
    @Test
    void testGetCategoryId() {
        assertEquals(11L,expense.getCategoryId());
    }
    
    @Test
    void testGetAmount() {
        assertEquals(1200L,expense.getAmount());
    }
    
    @Test
    void testGetManagerId() {
        assertEquals("managerId",expense.getManagerId());
    }
    
    @Test
    void testGetAssociateId() {
        assertEquals("empId",expense.getAssociateId());
    }

    @Test
    void testGetStatus() {
        assertEquals("approved",expense.getStatus());
    }
    
    @Test
    void testGetManagerComments() {
        assertEquals("m comments",expense.getManagerComments());
    }
    
    @Test
    void testGetEmployeeComments() {
        assertEquals("e comments",expense.getEmployeeComments());
    }
    
    @Test
    void testGetSubmissionDate() {
        assertEquals(LocalDate.of(2023, 10, 05),expense.getSubmissionDate());
    }
    
    @Test
    void testGetApprovedDate() {
        assertEquals(LocalDate.of(2023, 10, 10),expense.getApprovedDate());
    }
    
    @Test
    void testGetBusineesPurpose() {
        assertEquals("bpurpose",expense.getBusinessPurpose());
    }
    
    
    @Test
    void testGetReceipt() {
        assertEquals(photo,expense.getReceipt());
    }
    
    // SET methods testing
    @Test
    void testSetExpenseId() {
        expense.setExpenseId(1L);
        assertEquals(1L,expense.getExpenseId());
    }
    
    @Test
    void testSetCategoryId() {
        expense.setCategoryId(11L);
        assertEquals(11L,expense.getCategoryId());
    }
    
    @Test
    void testSetAmount() {
        expense.setAmount(1200L);
        assertEquals(1200L,expense.getAmount());
    }
    
    @Test
    void testSetManagerId() {
        expense.setManagerId("managerId");
        assertEquals("managerId",expense.getManagerId());
    }
    
    @Test
    void testSetAssociateId() {
        expense.setAssociateId("empId");
        assertEquals("empId",expense.getAssociateId());
    }

    @Test
    void testSetStatus() {
        expense.setStatus("approved");
        assertEquals("approved",expense.getStatus());
    }
    
    @Test
    void testSetManagerComments() {
        expense.setManagerComments("m comments");
        assertEquals("m comments",expense.getManagerComments());
    }
    
    @Test
    void testSetEmployeeComments() {
        expense.setEmployeeComments("e comments");
        assertEquals("e comments",expense.getEmployeeComments());
    }
    
    @Test
    void testSetSubmissionDate() {
        expense.setSubmissionDate(LocalDate.of(2023, 10, 05));
        assertEquals(LocalDate.of(2023, 10, 05),expense.getSubmissionDate());
    }
    
    @Test
    void testSetApprovedDate() {
        expense.setApprovedDate(LocalDate.of(2023, 10, 10));
        assertEquals(LocalDate.of(2023, 10, 10),expense.getApprovedDate());
    }
    
    @Test
    void testSetBusineesPurpose() {
        expense.setBusinessPurpose("bpurpose");
        assertEquals("bpurpose",expense.getBusinessPurpose());
    }
    
    
    @Test
    void testSetReceipt() {
        expense.setReceipt(photo);
        assertEquals(photo,expense.getReceipt());
    }
    
 
}
