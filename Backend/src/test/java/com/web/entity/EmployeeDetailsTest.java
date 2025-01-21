package com.web.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;






class EmployeeDetailsTest {

    private EmployeeDetails employee = null;

    @BeforeEach
    void setUp() throws Exception {
        employee = new EmployeeDetails("phaniId", "phani@web.COM","phanindra",LocalDate.of(2001, 10,10),"manager","password","TM",0L,0L);
    }

    @AfterEach
    void tearDown() throws Exception {
        employee = null;
    }
    
    @Test
    void testEmployeeLongStringLocalDate() {
        EmployeeDetails employee = new EmployeeDetails("phaniId", "phani@web.COM","phanindra",LocalDate.of(2001, 10,10),"manager","password","TM",0L,0L);
        assertNotNull(employee);
    }

    @Test
    void testGetId() {
        assertEquals("phaniId",employee.getAssociateId());
    }

    @Test
    void testGetName() {
        assertEquals("phanindra",employee.getName());
    }

    @Test
    void testGetBirthdate() {
        assertEquals("2001-10-10",employee.getBirthDate().toString());
    }
    
    
   

//    @Test
//    void testSetName() {
//        employee.setName("Ravi Kumar");
//        assertEquals("Ravi Kumar",employee.getName());
//    }
//
//    @Test
//    void testSetBirthdate() {
//        employee.setBirthdate(LocalDate.of(1999, 10, 11));
//        assertEquals("1999-10-11",employee.getBirthdate().toString());
//    }



}
