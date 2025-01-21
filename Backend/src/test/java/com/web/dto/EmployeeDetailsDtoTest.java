package com.web.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EmployeeDetailsDtoTest {

    EmployeeDetailsDto employee=null;

    @BeforeEach
    void setUp() throws Exception {
        employee=new EmployeeDetailsDto("Emp500001","tom@gmail.com","tom",LocalDate.of(2001,11,19),"Man5000001","fsgthgjh","employee",0L,0L);
    }

    @AfterEach
    void tearDown() throws Exception {
        employee=null;
    } 

    @Test
    void testAllArgsConstructor() {
        EmployeeDetailsDto employee1=new EmployeeDetailsDto("Man500001","jerry@gmail.com","jerry",LocalDate.of(2001,11,19),"Man5000002","fsghgjh","Manager",0L,0L);
        assertNotNull(employee1);
    }
    
    @Test
    void testGetAssociateId() {
        assertEquals("Emp500001",employee.getAssociateId());
    }
    
    @Test
    void testSetAssociateId() {
        employee.setAssociateId("Emp600001");
        assertEquals("Emp600001",employee.getAssociateId());
    }
    
    @Test
    void testGetEmail() {
        assertEquals("tom@gmail.com",employee.getEmail());
    }

    @Test
    void testSetEmail() {
        employee.setEmail("tom123@gmail.com");
        assertEquals("tom123@gmail.com",employee.getEmail());
    }
    
    @Test
    void testGetName() {
        assertEquals("tom",employee.getName());
    }
    
    @Test
    void testSetName() {
        employee.setName("tommy");
        assertEquals("tommy",employee.getName());
    }
    
    @Test
    void testGetDob() {
        assertEquals(LocalDate.of(2001, 11, 19), employee.getBirthDate());
    }
    
    @Test
    void testSetDob() {
        employee.setBirthDate(LocalDate.of(2001, 12, 13));
        assertEquals(LocalDate.of(2001, 12, 13), employee.getBirthDate());
    }
    
    @Test
    void testGetManagerId() {
        assertEquals("Man5000001",employee.getManagerId());
    }
    
    @Test
    void testSetManagerId() {
        employee.setManagerId("Man600001");
        assertEquals("Man600001",employee.getManagerId());
    }
    
    @Test
    void testGetWorkerId() {
        assertEquals("fsgthgjh",employee.getWorkerId());
    }
    
    @Test
    void testSetWorkerId() {
        employee.setWorkerId("WorkingId123");
        assertEquals("WorkingId123",employee.getWorkerId());
    }
    
    @Test
    void testGetDesignation() {
        assertEquals("employee",employee.getDesignation());
    }
    
    @Test
    void testSetDesignation() {
        employee.setDesignation("Manager");
        assertEquals("Manager",employee.getDesignation());
    }
    
    @Test
    void testGetFundsReleased() {
        assertEquals(0L,employee.getFundsReleased());
    }
    
    @Test
    void testSetFundsReleased() {
        employee.setFundsReleased(60L);
        assertEquals(60L,employee.getFundsReleased());
        
    }
    
    @Test
    void testGetFundsAllocated() {
        assertEquals(0L,employee.getFundsAllocated());
    }
    
    @Test
    void testSetFundsAllocated() {
        employee.setFundsAllocated(80000000L);
        assertEquals(80000000L,employee.getFundsAllocated());
    }
}
