package com.web.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.web.ExpenseManagementApplication;
import com.web.dto.CategoryDTO;
import com.web.service.CategoryServiceImpl;







@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = ExpenseManagementApplication.class)
@AutoConfigureMockMvc 
class CategoryControllerTest {

    
    @Mock
    private CategoryServiceImpl service;
    
    @InjectMocks
    private CategoryController controller;
    
    CategoryDTO category;
    
    @BeforeEach
    void setUp() {
        CategoryDTO category=new CategoryDTO(1L,"travel",5000L,"active");
    }
    
    @AfterEach
    void tearDown() {
        category=null;
    }

    
    @Test
    void testControllerAddCategory() {
        when(service.addCategory(category)).thenReturn(category);
        ResponseEntity<CategoryDTO> addCat= controller.addCategory(category);
        assertEquals(category,addCat.getBody());
    }
    
    @Test
    void testControllerUpdateCategory() {
        CategoryDTO category=new CategoryDTO(1L,"travel",5000L,"active");
        category.setName("internet");
        when(service.updateCategory(1L,category)).thenReturn(category);
        ResponseEntity<CategoryDTO> updateCat=controller.updateCategory(1L, category);
        assertEquals(category,updateCat.getBody());
    }
    
    @Test
    void testControllerDeleteCategory() {
        CategoryDTO category=new CategoryDTO(1L,"travel",5000L,"active");
        when(service.deleteCategory(1L)).thenReturn("category got deleted");
        ResponseEntity<String> deleteCat=controller.deleteCategory(1L);
        assertEquals(HttpStatus.OK,deleteCat.getStatusCode());
    }
    
    @Test
    void testControllerGetAllCategory() {
        List<CategoryDTO> categoryList=new ArrayList<>();
        CategoryDTO category1=new CategoryDTO(1L,"internet",1000L,"active");
        CategoryDTO category2=new CategoryDTO(2L,"travel",5000L,"active");
        CategoryDTO category3=new CategoryDTO(3L,"food",5000L,"active");
        categoryList.add(category1);
        categoryList.add(category2);
        categoryList.add(category3);
        when(service.getAllCategories()).thenReturn(categoryList);
        ResponseEntity<List<CategoryDTO>> res=controller.getAllCategory();
        assertEquals(categoryList,res.getBody());
    }
    
    @Test
    void testControllerGetCatById() {
        when(service.getCategoryById(1L)).thenReturn(category);
        ResponseEntity<CategoryDTO> cat=controller.getCatById(1L);
        assertEquals(category,cat.getBody());
    }
    
    @Test
    void testControllerGetCatByStatus() {
        List<CategoryDTO> categoryList=new ArrayList<>();
        CategoryDTO category1=new CategoryDTO(1L,"internet",1000L,"active");
        CategoryDTO category2=new CategoryDTO(2L,"travel",5000L,"inactive");
        CategoryDTO category3=new CategoryDTO(3L,"food",5000L,"active");
        categoryList.add(category1);
        categoryList.add(category2);
        categoryList.add(category3);
        List<CategoryDTO> activeCategories=new ArrayList<>();
        activeCategories.add(category1);
        activeCategories.add(category3);
        when(service.getAllCatByStatus("active")).thenReturn(activeCategories);
        ResponseEntity<List<CategoryDTO>> res=controller.getCatByStatus("active");
        assertEquals(activeCategories,res.getBody());
    }
    
    
}