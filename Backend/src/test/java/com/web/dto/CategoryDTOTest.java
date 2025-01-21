package com.web.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CategoryDTOTest {
    CategoryDTO category=null;

    @BeforeEach
    void setUp() throws Exception {
        category=new CategoryDTO(1L,"travel",5000L,"active");
    }
 
    @AfterEach
    void tearDown() throws Exception {
        category=null;
    }

    @Test
    void testConstructorsStringLongString() {
        category =new CategoryDTO(1L,"internet",1000L,"active");
        assertNotNull(category);
    }
    
    @Test
    void testAllArgsConstructor() {
        category=new CategoryDTO(2L,"internet",1000L,"active");
        assertNotNull(category);
    }
    
    @Test 
    void testGetId(){
        Long id=category.getId();
        assertEquals(1L,id);
    }
    
    @Test
    void testGetName() {
        assertEquals("travel",category.getName());
    }
    
    @Test
    void testGetLimit() {
        assertEquals(5000L,category.getLimit());
    }
    
    @Test
    void testGetStatus() {
        assertEquals("active",category.getStatus());
    }
    
    @Test
    void testSetName() {
        category.setName("health");
        assertEquals("health",category.getName());
    }
    
    @Test
    void testSetLimit() {
        category.setLimit(3000L);
        assertEquals(3000L,category.getLimit());
    }
    
    @Test
    void testSetStatus() {
        category.setStatus("inactive");
        assertEquals("inactive",category.getStatus());
    }
    

}
