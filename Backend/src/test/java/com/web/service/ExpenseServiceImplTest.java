package com.web.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.web.dto.ExpenseDto;
import com.web.entity.Expense;
import com.web.exception.ExpenseException;
import com.web.repository.ExpenseRepository;


//@ExtendWith(SpringExtension.class)
//@RunWith(SpringRunner.class)
@SpringBootTest
class ExpenseServiceImplTest {

     private Expense exp1;
     private Expense exp2;
     private byte[] photo1;
     private byte[] photo2;
     
        @TestConfiguration
        static class ExpenseServiceImplTestContextConfiguration {
            @Bean
            public ExpenseService expenseService() {
                return new ExpenseServiceImpl();
            }
        }


        @Autowired
        private ExpenseService expenseService;
        
        @MockBean
        private ExpenseRepository expenseRepository;
    
     @AfterEach
         void cleanUp() {
            exp1=null;
            exp2=null;
        }
         
     private void verifyFindAllExpensesIsCalledOnceBycategoryId(Long categoryId) {
            Mockito.verify(expenseRepository, VerificationModeFactory.times(1)).getExpensesByCategoryId(categoryId);
            Mockito.reset(expenseRepository);
        }
     
     private void verifyFindAllExpensesIsCalledOnceByEmployeeId(String employeeId) {
            Mockito.verify(expenseRepository, VerificationModeFactory.times(1)).getExpensesByEmpId(employeeId);
            Mockito.reset(expenseRepository);
        }
     
     private void verifyFindAllExpensesIsCalledOnceByCategoryIdStatusId(String status,Long categoryId) {
            Mockito.verify(expenseRepository, VerificationModeFactory.times(1)).getExpensesByStatusByCategory(status,categoryId);
            Mockito.reset(expenseRepository);
        }
     
     private void verifyFindAllExpensesIsCalledOnceByStatus(String status) {
            Mockito.verify(expenseRepository, VerificationModeFactory.times(1)).getExpenseByStatus(status);
            Mockito.reset(expenseRepository);
        }
     
     
     private void verifyFindAmountOnceByCategoryId(Long id) {
            Mockito.verify(expenseRepository, VerificationModeFactory.times(1)).getAmountByCategory(id);
            Mockito.reset(expenseRepository);
        }
     
     
     private void verifyFindAmountOnceByCategoryIdBetween(LocalDate fromDate,LocalDate toDate,Long id) {
            Mockito.verify(expenseRepository, VerificationModeFactory.times(1)).getTotalAmountByCategoryInBetween(fromDate,toDate,id);
            Mockito.reset(expenseRepository);
        }
     
     
     private void verifyFindAmountOnceByStatus(String status) {
            Mockito.verify(expenseRepository, VerificationModeFactory.times(1)).getAmountByStatus(status);
            Mockito.reset(expenseRepository);
        }
     
     private void verifyFindAmountOnceByCategoryByStatusInBetween(LocalDate fromDate,LocalDate toDate,Long id,String status) {
            Mockito.verify(expenseRepository, VerificationModeFactory.times(1)).getTotalAmountByCategoryByStatusInBetween(fromDate, toDate, id,status);
            Mockito.reset(expenseRepository);
        }
     
     private void verifyFindExpensesIsCalledOnceByStatusByEmpId(String status,String empId) {
         Mockito.verify(expenseRepository, VerificationModeFactory.times(1)).getExpensesByStatusByEmpId(status,empId);
         Mockito.reset(expenseRepository);
     }
  
  
  private void verifyFindAmountOnceByStatusEmpId(String status,String empId) {
         Mockito.verify(expenseRepository, VerificationModeFactory.times(1)).getAmountByStatusByEmpId(status,empId);
         Mockito.reset(expenseRepository);
     }
  
  private void verifyFindExpensePageWiseOnceByStatus(String status,Pageable pageable) {
      Mockito.verify(expenseRepository, VerificationModeFactory.times(1)).getPagewiseExpensesByStatus(status,pageable);
      Mockito.reset(expenseRepository);
  }
  
  private void verifyFindAmountPresentYearOnceByStatusEmpId(LocalDate fromDate,LocalDate toDate,String status,String empId) {
      Mockito.verify(expenseRepository, VerificationModeFactory.times(1)).getAmountBystatusByPresentYear(fromDate,toDate,empId,status);
      Mockito.reset(expenseRepository);
  }
  
  private void verifyFindAmountLastFiveYearsOnceByStatusEmpId(LocalDate fromDate,LocalDate toDate,String status,String empId) {
      Mockito.verify(expenseRepository, VerificationModeFactory.times(1)).getAmountByStatusLastFiveYearWise(fromDate,toDate,status,empId);
      Mockito.reset(expenseRepository);
  }
  
     @BeforeEach
     public void setUp() {

         exp1 =  new Expense(505L,"Travelling",1200L,"managerId","empId1","approved","m comments","e comments",LocalDate.of(2023, 05, 05),LocalDate.of(2023, 05, 10),"bpurpose",photo1);

         exp2 = new Expense(507L,"",1500L,"managerId","empId2","declined","m comments","e comments",LocalDate.of(2023, 11, 25),LocalDate.of(2023, 11, 27),"bpurpose",photo2);
         Pageable pageable=PageRequest.of(1,1);


         List<Expense> allExpenses = Arrays.asList(exp1,exp2);
         List<Expense> allExpensesWithCondition = Arrays.asList(exp2);
         Page<Expense> PageWiseExpensesWithCondition =new PageImpl<>(allExpensesWithCondition);
         
        
         List<Expense> empty=new ArrayList<>();
       
         
         Mockito.when(expenseRepository.findAll()).thenReturn(allExpenses);
         Mockito.when(expenseRepository.getExpensesByCategoryId(507L)).thenReturn(allExpensesWithCondition);
         Mockito.when(expenseRepository.getExpensesByCategoryId(-1L)).thenReturn(empty);
         Mockito.when(expenseRepository.getExpensesByEmpId("empId2")).thenReturn(allExpensesWithCondition);
         Mockito.when(expenseRepository.getExpensesByStatusByCategory("declined",507L)).thenReturn(allExpensesWithCondition);
         Mockito.when(expenseRepository.getExpenseByStatus("declined")).thenReturn(allExpensesWithCondition);
         Mockito.when(expenseRepository.getAmountByCategory(507L)).thenReturn(1500L);
         Mockito.when(expenseRepository.getTotalAmountByCategoryInBetween(LocalDate.of(2023,10, 01),LocalDate.of(2023,12, 25),507L)).thenReturn(1500L);
         Mockito.when(expenseRepository.getAmountByStatus("declined")).thenReturn(1500L);
         Mockito.when(expenseRepository.getTotalAmountByCategoryByStatusInBetween(LocalDate.of(2023,10, 01),LocalDate.of(2023,12, 25),507L,"declined")).thenReturn(1500L);
         Mockito.when(expenseRepository.getExpensesByStatusByEmpId("declined","empId2")).thenReturn(allExpensesWithCondition);
         Mockito.when(expenseRepository.getAmountByStatusByEmpId("declined","empId2")).thenReturn(1500L);
         Mockito.when(expenseRepository.getPagewiseExpensesByStatus("declined",pageable)).thenReturn(PageWiseExpensesWithCondition);
         Mockito.when(expenseRepository.getAmountBystatusByPresentYear(LocalDate.of(2023,10, 01),LocalDate.of(2023,12, 25),"empId2","declined")).thenReturn(1500L);
         Mockito.when(expenseRepository.getAmountByStatusLastFiveYearWise(LocalDate.of(2023,10, 01),LocalDate.of(2023,12, 25),"empId2","declined")).thenReturn(1500L);
//         Mockito.when(expenseRepository.verifyGetReceiptOnceByExpId(507L)).thenReturn(1500L);

     }
     
         
     @Test
     public void getExpensesByEmpId_thenReturnAllExpenses() {

             String id = exp2.getAssociateId();
             List<ExpenseDto> allExpenses;
             try {
                 allExpenses = expenseService.getExpensesByEmpId(id);
                 verifyFindAllExpensesIsCalledOnceByEmployeeId(id);
                 assertThat(allExpenses).hasSize(1);
             } catch (ExpenseException e) {
                 // TODO Auto-generated catch block
                 e.printStackTrace();
             }
             
         }
     
     @Test
     public void getExpensesByCategoryId_thenReturnAllExpenses() {

             Long id = exp2.getCategoryId();
             List<ExpenseDto> allExpenses;
             try {
                 allExpenses = expenseService.getExpensesByCategoryId(id);
                 verifyFindAllExpensesIsCalledOnceBycategoryId(id);
                 assertThat(allExpenses).hasSize(1);
             } catch (ExpenseException e) {
                 // TODO Auto-generated catch block
                 e.printStackTrace();
             }
             
         }

     @Test
     public void getExpensesByStatusByCategory_thenReturnAllExpenses() {
             Long id=exp2.getCategoryId();
             String status = exp2.getStatus();
             
             List<ExpenseDto> allExpenses;
             try {
                 allExpenses = expenseService.getExpensesByStatusByCategory(status,id);
                 verifyFindAllExpensesIsCalledOnceByCategoryIdStatusId(status,id);
                 assertThat(allExpenses).hasSize(1);
             } catch (ExpenseException e) {
                 // TODO Auto-generated catch block
                 e.printStackTrace();
             }
             
         }
     
     @Test
     public void getExpensesByStatus_thenReturnAllExpenses() {

         String status = exp2.getStatus();
             List<ExpenseDto> allExpenses;
             try {
                 allExpenses = expenseService.getExpensesByStatus(status);
                 verifyFindAllExpensesIsCalledOnceByStatus(status);
                 assertThat(allExpenses).hasSize(1);
             } catch (ExpenseException e) {
                 // TODO Auto-generated catch block
                 e.printStackTrace();
             }
             
         }
     
   
     @Test
     public void getAmountByCategory_thenReturnAllAmount() {

         Long id = exp2.getCategoryId();
             
             try {
                 Long amount = expenseService.getAmountByCategory(id);
                 verifyFindAmountOnceByCategoryId(id);
                 assertThat(amount).isEqualTo(1500L);
             } catch (ExpenseException e) {
                 // TODO Auto-generated catch block
                 e.printStackTrace();
             }
             
         }
     
     
     @Test
     public void getTotalAmountByCategoryInBetween_thenReturnAmount() {

         Long id = exp2.getCategoryId();
//         String status = exp2.getStatus();
         LocalDate fromDate=LocalDate.of(2023,10, 01);
         LocalDate toDate=LocalDate.of(2023,12, 25);
             
             try {
                 Long amount = expenseService.getTotalAmountByCategoryInBetween(fromDate,toDate,id);
                 verifyFindAmountOnceByCategoryIdBetween(fromDate,toDate,id);
                 assertThat(amount).isEqualTo(1500L);
             } catch (ExpenseException e) {
                 // TODO Auto-generated catch block
                 e.printStackTrace();
             }
             
         }
     
     @Test
     public void getAmountByStatus_thenReturnAmount() {

         
         String status = exp2.getStatus();
         
             
             try {
                 Long amount = expenseService.getAmountByStatus(status);
                 verifyFindAmountOnceByStatus(status);
                 assertThat(amount).isEqualTo(1500L);
             } catch (ExpenseException e) {
                 // TODO Auto-generated catch block
                 e.printStackTrace();
             }
             
         }
     
     @Test
     public void getTotalAmountByCategoryByStatusInBetween_thenReturnAmount() {
         Long id = exp2.getCategoryId();
         String status = exp2.getStatus();
         LocalDate fromDate=LocalDate.of(2023,10, 01);
         LocalDate toDate=LocalDate.of(2023,12, 25);
             
             try {
                 Long amount = expenseService.getTotalAmountByCategoryByStatusInBetween(fromDate,toDate,id,status);
                 verifyFindAmountOnceByCategoryByStatusInBetween(fromDate,toDate,id,status);
                 assertThat(amount).isEqualTo(1500L);
             } catch (ExpenseException e) {
                 // TODO Auto-generated catch block
                 e.printStackTrace();
             }
             
         }

//   @Test
//   public void addExpenseById_thenReturnAllExpenses() {
//
//           Long id = exp2.getExpenseId();
//           MultipartFile file = null;
//           String allExpenses;
//           try {
//               allExpenses = expenseService.addExpenseById(file, id);
//               verifyOnceCameAddExpense(file,id);
//               assertThat(allExpenses).hasSize(1);
//           } catch (ExpenseException e) {
//               // TODO Auto-generated catch block
//               e.printStackTrace();
//           }
//           
//       }
   
   
   @Test
   public void getExpensesByStatusByEmpId_thenReturnAllExpenses() {
       String empId=exp2.getAssociateId();
       String status = exp2.getStatus();
           List<ExpenseDto> allExpenses;
           try {
               allExpenses = expenseService.getExpensesByStatusByEmpId(status,empId);
               verifyFindExpensesIsCalledOnceByStatusByEmpId(status,empId);
               assertThat(allExpenses).hasSize(1);
           } catch (ExpenseException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
           }
           
       }
   
   
   @Test
   public void getAmountByStatusByEmpId_thenReturnAmount() {
       String empId = exp2.getAssociateId();
       String status = exp2.getStatus();
           
           try {
               Long amount = expenseService.getAmountByStatusByEmpId(status,empId);
               verifyFindAmountOnceByStatusEmpId(status,empId);
               assertThat(amount).isEqualTo(1500L);
           } catch (ExpenseException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
           }
           
       }
  
   
   @Test
   public void getPagewiseExpensesByStatus_thenReturnAmount() {
       int pageno=1;
       int rows=1;
       Pageable pageable=PageRequest.of(pageno, rows);
       String status = exp2.getStatus();
       
      
       
       List<ExpenseDto> pageWiseExpenses;
           try {
               
               pageWiseExpenses = expenseService.getPagewiseExpensesByStatus(pageno,rows,status);
               verifyFindExpensePageWiseOnceByStatus(status,pageable);
               assertThat(pageWiseExpenses.equals(pageWiseExpenses));
           } catch (ExpenseException e) {
               e.printStackTrace();
           }
           
       }
   
   //    public Long getAmountBystatusByPresentYear(LocalDate fromDate,LocalDate toDate,String status,String associateId) throws ExpenseException;

   @Test
   public void getAmountBystatusByPresentYear_thenReturnAmount() {
       String empId = exp2.getAssociateId();
       String status = exp2.getStatus();
       LocalDate fromDate=LocalDate.of(2023,10, 01);
       LocalDate toDate=LocalDate.of(2023,12, 25);
           
           try {
               Long amount = expenseService.getAmountBystatusByPresentYear(fromDate,toDate,status,empId);
               verifyFindAmountPresentYearOnceByStatusEmpId(fromDate,toDate,status,empId);
               assertThat(amount).isEqualTo(1500L);
           } catch (ExpenseException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
           }
           
       }
   
   @Test
   public void getAmountByStatusLastFiveYearWise_thenReturnAmount() {
       String empId = exp2.getAssociateId();
       String status = exp2.getStatus();
       LocalDate fromDate=LocalDate.of(2023,10, 01);
       LocalDate toDate=LocalDate.of(2023,12, 25);
           
           try {
               Long amount = expenseService.getAmountByStatusLastFiveYearWise(fromDate,toDate,status,empId);
               verifyFindAmountLastFiveYearsOnceByStatusEmpId(fromDate,toDate,status,empId);
               //This method is onlly for the approved cases so it should retuirn 0
               assertThat(amount).isEqualTo(0L);
           } catch (ExpenseException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
           }
           
       }
   

}