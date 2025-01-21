package com.web.service;

import java.util.List;

import com.web.dto.CategoryDTO;

public interface CategoryService {
	public CategoryDTO addCategory(CategoryDTO categoryDTO);
	public String deleteCategory(Long id);
	public List<CategoryDTO> getAllCategories();
	CategoryDTO updateCategory(Long id,CategoryDTO categoryDTO);
	public CategoryDTO getCategoryById(Long id);
	public List<CategoryDTO> getAllCatByStatus(String status);
}
