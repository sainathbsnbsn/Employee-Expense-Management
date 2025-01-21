package com.web.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.web.entity.Expense;


@ExtendWith(SpringExtension.class)
@ContextConfiguration
@AutoConfigureTestDatabase(replace=Replace.NONE)
@DataJpaTest
class ExpenseRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;


    @Autowired
    private ExpenseRepository expenseRepository;
    
   
    private byte[] photo;
    
    @Test
    public void whenInvalidId_thenReturnNull() {
        Expense fromDb = expenseRepository.findById(-11l).orElse(null);
        assertThat(fromDb).isNull();
    }
    
    @Test
    public void givenSetOfExpenses_whenFindAll_thenReturnAllExpense() {
        Expense exp1 = new Expense(11L,"Travelling",1200L,"managerId","empId1","approved","m comments","e comments",LocalDate.of(2023, 10, 05),LocalDate.of(2023, 10,11 ),"bpurpose",photo);
//        Expense  exp2= new Expense(2L,12L,1500L,"managerId","empId2","declined","declined","e comments",LocalDate.of(2023, 11, 23),LocalDate.of(2023, 11,26),"bpurpose",photo);
//        Expense exp3= new Expense(3L,13L,1000L,"managerId","empId3","approved","m comments","e comments",LocalDate.of(2023, 12, 15),LocalDate.of(2023, 10, 10),"bpurpose",photo);
//
//
//       
        List<Expense> allExpenseBefore = expenseRepository.findAll();
        System.out.println(allExpenseBefore.size());
        
        entityManager.persist(exp1);
//        entityManager.persist(exp2);
//        entityManager.persist(exp3);
        entityManager.flush();


        List<Expense> allExpensesAfter = expenseRepository.findAll();


        assertThat(allExpensesAfter)
        .hasSize(allExpenseBefore.size()+1);


    }
    
    
    @Test
    public void getExpensesByCategoryId_thenReturnAllExpenses() {
        Expense exp1 = new Expense(11L,"Travelling",1200L,"managerId","empId1","approved","m comments","e comments",LocalDate.of(2023, 10, 05),LocalDate.of(2023, 10,11 ),"bpurpose",photo);
        List<Expense> CategoryWiseExpenseBefore = expenseRepository.getExpensesByCategoryId(11L);
        entityManager.persist(exp1);
        entityManager.flush();
        
        List<Expense> CategoryWiseExpenseAfter = expenseRepository.getExpensesByCategoryId(11L);
        
         assertThat(CategoryWiseExpenseAfter)
         .hasSize(CategoryWiseExpenseBefore.size()+1);
        
    }
    
    @Test
    public void getExpensesByStatus_thenReturnAllExpenses() {
        Expense exp1 = new Expense(11L,"Travelling",1200L,"managerId","empId1","approved","m comments","e comments",LocalDate.of(2023, 10, 05),LocalDate.of(2023, 10,11 ),"bpurpose",photo);
        List<Expense> StatusWiseExpenseBefore = expenseRepository.getExpenseByStatus("approved");
        entityManager.persist(exp1);
        entityManager.flush();
        
        List<Expense> StatusWiseExpenseAfter = expenseRepository.getExpenseByStatus("approved");
        
         assertThat(StatusWiseExpenseAfter)
         .hasSize(StatusWiseExpenseBefore.size()+1);
        
    }
    
    @Test
    public void getExpensesByStatusByCategory_thenReturnAllExpenses() {
        Expense exp1 = new Expense(11L,"Travelling",1200L,"managerId","empId1","approved","m comments","e comments",LocalDate.of(2023, 10, 05),LocalDate.of(2023, 10,11 ),"bpurpose",photo);
        List<Expense> CategoryAndStatusWiseExpenseBefore = expenseRepository.getExpensesByStatusByCategory("approved",11L);
        entityManager.persist(exp1);
        entityManager.flush();
        
        List<Expense> CategoryAndStatusWiseExpenseAfter = expenseRepository.getExpensesByStatusByCategory("approved",11L);
        
         assertThat(CategoryAndStatusWiseExpenseAfter)
         .hasSize(CategoryAndStatusWiseExpenseBefore.size()+1);
        
    }
    
    @Test
    public void getAmountByCategory_thenReturnAmount() {
        Expense exp1 = new Expense(11L,"Travelling",1200L,"managerId","empId1","approved","m comments","e comments",LocalDate.of(2023, 10, 05),LocalDate.of(2023, 10,11 ),"bpurpose",photo);
        Long amountBefore = expenseRepository.getAmountByCategory(11L);
        
        entityManager.persist(exp1);
        entityManager.flush();
        
        Long amountAfter = expenseRepository.getAmountByCategory(11L);
        if(amountBefore!= null)
         assertThat(amountAfter).isEqualTo(amountBefore+1200L);
        else {
            assertThat(amountBefore).isNull();
        }
    }

    
    @Test
    public void getTotalAmountByCategoryInBetween_thenReturnAmount() {
        Expense exp1 = new Expense(11L,"Travelling",1200L,"managerId","empId1","approved","m comments","e comments",LocalDate.of(2023, 10, 05),LocalDate.of(2023, 10,11 ),"bpurpose",photo);
        Long amountBefore = expenseRepository.getTotalAmountByCategoryInBetween(LocalDate.of(2023, 02, 05),LocalDate.of(2023, 11, 05),11L);
        entityManager.persist(exp1);
        entityManager.flush();
        
        Long amountAfter = expenseRepository.getTotalAmountByCategoryInBetween(LocalDate.of(2023, 02, 05),LocalDate.of(2023, 11, 05),11L);
        
        if(amountBefore!= null)
            assertThat(amountAfter).isEqualTo(amountBefore+1200L);
           else {
               assertThat(amountBefore).isNull();
           }
        
    }
    
    
    @Test
    public void getAmountByStatus_thenReturnAmount() {
        Expense exp1 = new Expense(11L,"Travelling",1200L,"managerId","empId1","approved","m comments","e comments",LocalDate.of(2023, 10, 05),LocalDate.of(2023, 10,11 ),"bpurpose",photo);
        Long amountBefore = expenseRepository.getAmountByStatus("approved");
        entityManager.persist(exp1);
        entityManager.flush();
        
        Long amountAfter = expenseRepository.getAmountByStatus("approved");
        
        if(amountBefore!= null)
               assertThat(amountAfter).isEqualTo(amountBefore+1200L);
              else {
                  assertThat(amountBefore).isNull();
              }
        
        
    }
    
    @Test
    public void getTotalAmountByCategoryByStatusInBetween_thenReturnAmount() {
        Expense exp1 = new Expense(11L,"Travelling",1200L,"managerId","empId1","approved","m comments","e comments",LocalDate.of(2023, 10, 05),LocalDate.of(2023, 10,11 ),"bpurpose",photo);
        Long amountBefore = expenseRepository.getTotalAmountByCategoryByStatusInBetween(LocalDate.of(2023, 10, 05),LocalDate.of(2023, 10, 05),11L,"approved");
        entityManager.persist(exp1);
        entityManager.flush();
        
        Long amountAfter = expenseRepository.getTotalAmountByCategoryByStatusInBetween(LocalDate.of(2023, 10, 05),LocalDate.of(2023, 10, 05),11L,"approved");
        
        if(amountBefore!= null)
               assertThat(amountAfter).isEqualTo(amountBefore+1200L);
              else {
                  assertThat(amountBefore).isNull();
              }
        
    }
    
    
    @Test
    public void getExpensesByStatusByEmpId_thenReturnAllExpenses() {
        Expense exp1 = new Expense(11L,"Travelling",1200L,"managerId","empId1","approved","m comments","e comments",LocalDate.of(2023, 10, 05),LocalDate.of(2023, 10,11 ),"bpurpose",photo);
        List<Expense> CategoryAndStatusWiseExpenseBefore = expenseRepository.getExpensesByStatusByEmpId("approved","empId1");
        entityManager.persist(exp1);
        entityManager.flush();
        
        List<Expense> CategoryAndStatusWiseExpenseAfter = expenseRepository.getExpensesByStatusByEmpId("approved","empId1");
        
         assertThat(CategoryAndStatusWiseExpenseAfter)
         .hasSize(CategoryAndStatusWiseExpenseBefore.size()+1);
        
    }
    
    @Test
    public void getAmountByStatusByEmpId_thenReturnAmount() {
        Expense exp1 = new Expense(11L,"Travelling",1200L,"managerId","empId1","approved","m comments","e comments",LocalDate.of(2023, 10, 05),LocalDate.of(2023, 10,11 ),"bpurpose",photo);
        Long amountBefore = expenseRepository.getAmountByStatusByEmpId("approved","empId1");
        entityManager.persist(exp1);
        entityManager.flush();
        
        Long amountAfter = expenseRepository.getAmountByStatusByEmpId("approved","empId1");
        
        if(amountBefore!= null) {
              assertThat(amountAfter).isEqualTo(amountBefore+1200L);
        }
             else {
                 assertThat(amountBefore).isNull();
             }
    }

    
    @Test
    public void getPagewiseExpensesByStatus_thenReturnAmount() {
        Expense exp1 = new Expense(11L,"Travelling",1200L,"managerId","empId1","approved","m comments","e comments",LocalDate.of(2023, 10, 05),LocalDate.of(2023, 10,11 ),"bpurpose",photo);
        int pageno=1;
        int rows=1;
        Pageable pageable=PageRequest.of(pageno, rows);
        Page<Expense> CategoryWiseExpense = expenseRepository.getPagewiseExpensesByStatus("approved",pageable);
        entityManager.persist(exp1);
        entityManager.flush();
        
//        List<Expense> CategoryWiseExpenseAfter = expenseRepository.getExpensesByCategoryId(11L);
        
         assertThat(CategoryWiseExpense)
         .hasSize(1);
    }
 
    
    @Test
    public void getAmountBystatusByPresentYear_thenReturnAmount() {
        Expense exp1 = new Expense(11L,"Travelling",1200L,"managerId","empId1","approved","m comments","e comments",LocalDate.of(2023, 10, 05),LocalDate.of(2023, 10,11 ),"bpurpose",photo);
//        Long year=2023L;
        LocalDate fromDate=LocalDate.parse(LocalDate.now().getYear()+"-01-01");
        LocalDate toDate=LocalDate.now();
        Long amountBefore = expenseRepository.getAmountBystatusByPresentYear(fromDate,toDate,"empId1","approved");
        entityManager.persist(exp1);
        entityManager.flush();
        
        Long amountAfter = expenseRepository.getAmountBystatusByPresentYear(fromDate,toDate,"empId1","approved");
        
        if(amountBefore!= null) {
              assertThat(amountAfter).isEqualTo(amountBefore+1200L);
        }
             else {
                 assertThat(amountBefore).isNull();
             }
    }
    
    
    @Test
    public void getAmountByStatusLastFiveYearWise_thenReturnAmount() {
        Expense exp1 = new Expense(11L,"Travelling",1200L,"managerId","empId1","approved","m comments","e comments",LocalDate.of(2023, 10, 05),LocalDate.of(2023, 10,11 ),"bpurpose",photo);
//        Long year=2023L;
        LocalDate fromDate=LocalDate.parse(LocalDate.now().getYear()+"-01-01");
        LocalDate toDate=LocalDate.now();
        Long amountBefore = expenseRepository.getAmountByStatusLastFiveYearWise(fromDate,toDate,"empId1","approved");
        entityManager.persist(exp1);
        entityManager.flush();
        
        Long amountAfter = expenseRepository.getAmountByStatusLastFiveYearWise(fromDate,toDate,"empId1","approved");
        
        if(amountBefore!= null) {
              assertThat(amountAfter).isEqualTo(amountBefore+1200L);
        }
             else {
                 assertThat(amountBefore).isNull();
             }
    }

    
    
}
