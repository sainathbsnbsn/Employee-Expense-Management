package com.web.service;

import java.util.List;

import com.web.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.dto.CategoryDTO;
import com.web.entity.CategoryEntity;

import jakarta.transaction.Transactional;


@Service
@Transactional
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	private CategoryRepository repo;
	
	private ModelMapper modelMapper = new ModelMapper();

	@Override
	public CategoryDTO addCategory(CategoryDTO categoryDTO) {
		CategoryEntity categoryEntity=modelMapper.map(categoryDTO,CategoryEntity.class);
		categoryEntity.setId(null);
		repo.save(categoryEntity);
		return modelMapper.map(categoryEntity,CategoryDTO.class);
	}
	
	@Override
	public CategoryDTO updateCategory(Long id,CategoryDTO updateCategoryDTO) {
	      CategoryEntity categoryEntity=repo.findById(id).orElse(null);
		if(categoryEntity != null) {
			categoryEntity.setName(updateCategoryDTO.getName());
			categoryEntity.setLimit(updateCategoryDTO.getLimit());
			categoryEntity.setStatus(updateCategoryDTO.getStatus());
			return modelMapper.map(categoryEntity, CategoryDTO.class);
		}else {
			return null;
		}
	}

	@Override
	public String deleteCategory(Long id) {
		repo.deleteById(id);
		return "Category got deleted";
	}

	@Override
	public List<CategoryDTO> getAllCategories() {
		List<CategoryEntity> allcategories=repo.findAll();
		return allcategories.stream().map(u-> modelMapper.map(u,CategoryDTO.class)).toList();
	}
	
	@Override
    public CategoryDTO getCategoryById(Long id) {
        CategoryEntity category=repo.findById(id).orElse(null);
        return modelMapper.map(category, CategoryDTO.class);
    }
	
	@Override
	public List<CategoryDTO> getAllCatByStatus(String status) {
	System.out.println(status);
	List<CategoryEntity> listcategory=repo.getAllCategoriesByStatus(status);
	return listcategory.stream().map(u-> modelMapper.map(u,CategoryDTO.class)).toList();
	}


}
