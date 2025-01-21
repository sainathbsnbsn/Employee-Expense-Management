package com.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.dto.CategoryDTO;
import com.web.service.CategoryServiceImpl;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value="/category")
public class CategoryController {
	
	@Autowired
	CategoryServiceImpl service;
	
	//http://localhost:8888/category/add
	@PostMapping("/add")
	public ResponseEntity<CategoryDTO> addCategory(@RequestBody CategoryDTO newcategory){
	CategoryDTO cateDTO=service.addCategory(newcategory);
	return new ResponseEntity<>(cateDTO,HttpStatus.OK);
	}
	
	//http://localhost:8888/category/update/id
	@PutMapping("/update/{id}")
	public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id,@RequestBody CategoryDTO updatedcategory){
		CategoryDTO newCateDTO=service.updateCategory(id, updatedcategory);
		return new ResponseEntity<>(newCateDTO,HttpStatus.OK);
	}
	
	//http://localhost:8888/category/delete/id
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteCategory(@PathVariable Long id){
		String message=service.deleteCategory(id);
		return new ResponseEntity<>(message,HttpStatus.OK);
	}
	
	//http://localhost:8888/category/getall
	@GetMapping("/getall")
	public ResponseEntity<List<CategoryDTO>> getAllCategory(){
		List<CategoryDTO> categorylist=service.getAllCategories();
		return new ResponseEntity<>(categorylist,HttpStatus.OK);
	}

	 //http://localhost:8888/category/get/455
    @GetMapping("/get/{id}")
    public ResponseEntity<CategoryDTO> getCatById(@PathVariable Long id){
        CategoryDTO category=service.getCategoryById(id);
        return new ResponseEntity<>(category,HttpStatus.OK);
    }
    
    //http://localhost:8081/category/status/{status}
    @GetMapping("/status/{status}")
    public ResponseEntity<List<CategoryDTO>> getCatByStatus(@PathVariable("status") String status){
    System.out.println(status);
    List<CategoryDTO> catStatusList=service.getAllCatByStatus(status);
    return ResponseEntity.ok(catStatusList);

    }
	
	
}
