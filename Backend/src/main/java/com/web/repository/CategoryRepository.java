package com.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.web.entity.CategoryEntity;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity,Long>{
	@Query("select u from CategoryEntity u where u.status=:catStatus")
	public List<CategoryEntity> getAllCategoriesByStatus(@Param(value = "catStatus") String catStatus);	
}
