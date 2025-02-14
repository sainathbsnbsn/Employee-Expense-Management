package com.web.service;

import java.time.LocalDate;
import java.util.List;

import com.web.dto.ExpenseDto;
import com.web.exception.ExpenseException;
import org.springframework.web.multipart.MultipartFile;

public interface ExpenseService {
	
	public ExpenseDto addExpense(ExpenseDto expense)throws ExpenseException;
	public ExpenseDto getExpenseById(Long expenseId) throws ExpenseException;
	public ExpenseDto updateExpense(ExpenseDto expenseDto ) throws ExpenseException;
	public  ExpenseDto deleteExpenseById(Long id)throws ExpenseException;
	public List<ExpenseDto> getAllExpenses() throws ExpenseException;
	
	public List<ExpenseDto> getExpensesByEmpId(String associateId)  throws ExpenseException;
	
    public Boolean rejectExpense(Long expId) throws ExpenseException ;
    public Boolean acceptExpense(Long expId)  throws ExpenseException;
	
    public List<ExpenseDto> getExpensesByCategoryId (Long categoryId) throws ExpenseException;//pagination
	
    public List<ExpenseDto> getExpensesByStatus (String status) throws ExpenseException;//order by date
    
    public List<ExpenseDto> getExpensesByStatusByCategory (String status,Long categoryId) throws ExpenseException;

    public Long getAmountByCategory (Long categoryId) throws ExpenseException;//filter by month
   
    public Long getTotalAmountByCategoryInBetween(LocalDate fromDate,LocalDate toDate,Long categoryId) throws ExpenseException;
    
    public Long getAmountByStatus(String status)  throws ExpenseException;
    
    public Long getTotalAmountByCategoryByStatusInBetween(LocalDate fromDate,LocalDate toDate,Long categoryId,String status) throws ExpenseException;

   
    List<ExpenseDto> getPagewiseExpensesByStatus(int pageno, int rows,String status) throws ExpenseException;

    public String addExpenseById(String file, Long id)throws ExpenseException;
    
    //get receipt by expense Id
    public abstract String getReceiptByExpId(Long id)throws ExpenseException;
 

    // for paging
//    List<ExpenseDto> getPagewiseExpensesByCategoryId(int pageno, int rows,Long categoryId) throws ExpenseException;

    public List<ExpenseDto> getExpensesByStatusByEmpId (String status,String associateId) throws ExpenseException;//order by date

    public Long getAmountByStatusByEmpId(String status,String associateId)  throws ExpenseException; 
    
    public Long getAmountBystatusByPresentYear(LocalDate fromDate,LocalDate toDate,String status,String associateId) throws ExpenseException;

    
    public Long getAmountByStatusLastFiveYearWise(LocalDate fromDate,LocalDate toDate,String status,String empId) throws ExpenseException;

    List<ExpenseDto> getExpensesByStatusByCategoryByEmployeeId(String status, Long categoryId, String associateId) throws ExpenseException;
// no need  public boolean setStatusofExpenseRequest(Expense e, String status) throws ExpenseException;
}
