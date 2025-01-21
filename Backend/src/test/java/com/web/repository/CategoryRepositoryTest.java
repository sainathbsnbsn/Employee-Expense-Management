package com.web.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.web.entity.CategoryEntity;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
@AutoConfigureTestDatabase(replace = Replace.NONE)
@DataJpaTest
class CategoryRepositoryTest {
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private CategoryRepository repo;
	
	@Test
	void testFindById() {
		CategoryEntity category=new CategoryEntity("travel",5000L,"active");
		entityManager.persistAndFlush(category);
		CategoryEntity foundById=repo.findById(category.getId()).orElse(null);
		assertThat(category.getId()).isEqualTo(foundById.getId());
	}
	
	
	@Test
	void invaildTestFindById() {
		CategoryEntity foundById=repo.findById(-4L).orElse(null);
		assertThat(foundById).isNull();
	}
	
	@Test
	void testFindAll() {
		CategoryEntity category1=new CategoryEntity("travel",5000L,"active");
		CategoryEntity category2=new CategoryEntity("medical",30000L,"active");
		CategoryEntity category3=new CategoryEntity("internet",1000L,"active");
		List<CategoryEntity> listOfCategory=repo.findAll();
		System.out.println(listOfCategory.size());
		entityManager.persist(category1);
		entityManager.persist(category2);
		entityManager.persist(category3);
		System.out.println("before");
		entityManager.flush();
		System.out.println("after");
		List<CategoryEntity> listOfCategory2=repo.findAll();
		System.out.println(listOfCategory2.size());
		assertThat(listOfCategory.size()+3).isEqualTo(listOfCategory2.size());		
	}
	
	@Test
	void testGetAllCategoriesByStatus() {		
	CategoryEntity category1=new CategoryEntity("travel",5000L,"active");
	CategoryEntity category2=new CategoryEntity("medical",30000L,"inactive");
	CategoryEntity category3=new CategoryEntity("internet",1000L,"active");
	entityManager.persist(category1);
	entityManager.persist(category2);
	entityManager.persist(category3);
	entityManager.flush();
	List<CategoryEntity> listOfActiveCategory=repo.getAllCategoriesByStatus("active");
	assertThat(11).isEqualTo(listOfActiveCategory.size());
	}


}
