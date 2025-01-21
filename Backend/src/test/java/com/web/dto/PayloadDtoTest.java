package com.web.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PayloadDtoTest {

    private PayloadDto payload = null;


    
    @BeforeEach
    void setUp() throws Exception {
        payload = new PayloadDto(LocalDate.of(2023, 10, 05),LocalDate.of(2023, 10, 10),1L);
    }

    @AfterEach
    void tearDown() throws Exception {
        payload = null;
    } 
    
    
    @Test
    void testGetFromDate() {
        assertEquals(LocalDate.of(2023, 10, 05),payload.getFromDate());
    }
    
    @Test
    void testGetToDate() {
        assertEquals(LocalDate.of(2023, 10, 10),payload.getToDate());
    }
    
    @Test
    void testGetCategoryId() {
        assertEquals(1L,payload.getCategoryId());
    }
    
    @Test
    void testSetFrom() {
        payload.setFromDate(LocalDate.of(2023, 11, 25));
        assertEquals(LocalDate.of(2023, 11, 25),payload.getFromDate());
    }
    
    @Test
    void testSetToDate() {
        payload.setToDate(LocalDate.of(2023, 11, 26));
        assertEquals(LocalDate.of(2023, 11, 26),payload.getToDate());
    }
    
    @Test
    void testSetCategoryId() {
        payload.setCategoryId(2L);
        assertEquals(2L,payload.getCategoryId());
    }


}
