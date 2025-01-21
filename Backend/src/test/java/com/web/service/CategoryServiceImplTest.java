package com.web.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.web.ExpenseManagementApplication;
import com.web.dto.CategoryDTO;
import com.web.entity.CategoryEntity;
import com.web.repository.CategoryRepository;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = ExpenseManagementApplication.class)
@AutoConfigureMockMvc 
class CategoryServiceImplTest {


    private CategoryEntity category1;
    private CategoryEntity category2;
    private CategoryEntity category3;
//    private List<CategoryEntity> listOfCategory;
//    private List<CategoryEntity> listOfExpectedCategory;
    

    @Mock
    private CategoryRepository repo;
    
    @InjectMocks
    private CategoryServiceImpl service;
    
     @Autowired
     private CategoryService catService;
    
    
    private ModelMapper modelMapper=new ModelMapper();
    
//    private void verifyGetCatByStatus(String status) {
//         Mockito.verify(repo, VerificationModeFactory.times(1)).getAllCategoriesByStatus(status);
//         Mockito.reset(repo);
//        
//    }

    
    
    
    
    @BeforeEach
    public void setUp() {

         category1=new CategoryEntity(1L,"travel",50000L,"active");
         category2=new CategoryEntity(2L,"food",4000L,"inactive");
         category3=new CategoryEntity(3L,"internet",1000L,"active");
        List<CategoryEntity> listOfCategory=new ArrayList<CategoryEntity>();
        listOfCategory.add(category3);
        listOfCategory.add(category2);
        listOfCategory.add(category1);

        List<CategoryEntity> listOfExpectedCategory=new ArrayList<CategoryEntity>();
        listOfExpectedCategory.add(category2);
        
        Mockito.when(repo.getAllCategoriesByStatus("inactive")).thenReturn(listOfExpectedCategory);
        
    }
    
    @AfterEach
        public void tearDown() {
        category1=null;
        category2=null;
        category3=null;
        
    }
    
    
//    @Test 
//    void testAddCategory() {
////        CategoryEntity category2=new CategoryEntity(1L,"travel",50000L,"active");
//        Mockito.when(repo.save(category1)).thenReturn(category1);
//        CategoryDTO catDTO =modelMapper.map(category1, CategoryDTO.class);
//        System.out.println(service.addCategory(catDTO).getId()+"_____________");
//        System.out.println(catDTO.getId()+"--------------------------");
//        assertThat(catDTO).usingRecursiveComparison().isEqualTo(service.addCategory(catDTO));
//    }
    
    @Test
    void testUpdateCategory() {
//        CategoryEntity category1=new CategoryEntity(1L,"travel",50000L,"active");
        Mockito.when(repo.findById(1L)).thenReturn(Optional.of(category1));
        category1.setName("internet");
        CategoryDTO catDTO=modelMapper.map(category1,CategoryDTO.class);
        assertThat(catDTO).usingRecursiveComparison().isEqualTo(service.updateCategory(1L, catDTO));
        
    }
    
    @Test
    void testDeleteCategory() {
        service.deleteCategory(1L);
        verify(repo,times(1)).deleteById(1L);
        
    }
    
    @Test
    void testGetCategoryById() {

        Mockito.when(repo.findById(1L)).thenReturn(Optional.of(category1));
        CategoryDTO catDTO=modelMapper.map(category1,CategoryDTO.class);
        assertThat(catDTO).usingRecursiveComparison().isEqualTo(service.getCategoryById(1L));
        
    }
    
    @Test
    void testGetAllCategory() {

        List<CategoryEntity> listOfCategory=new ArrayList<CategoryEntity>();
        listOfCategory.add(category1);
        listOfCategory.add(category2);
        listOfCategory.add(category3);
        Mockito.when(repo.findAll()).thenReturn(listOfCategory);
        assertThat(listOfCategory.size()).usingRecursiveComparison().isEqualTo(service.getAllCategories().size());
    }    
     
    @Test
    public void getCatByStatus() {
            List<CategoryDTO> activeCategories;
            String status="inactive"; 
    
            activeCategories = catService.getAllCatByStatus(status);
            

            assertThat(activeCategories).hasSize(0);
        }
}
