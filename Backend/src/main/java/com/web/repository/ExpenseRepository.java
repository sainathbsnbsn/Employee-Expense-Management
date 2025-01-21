package com.web.repository;


import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.web.entity.Expense;

@Repository
public interface ExpenseRepository  extends JpaRepository<Expense,Long>{

	@Query(value="Select * from season2_batch2_team3_expense_ems_test e where e.associate_id=:empId",nativeQuery=true)
	List<Expense> getExpensesByEmpId (@Param ("empId") String empId);
	
	@Query(value="Select * from season2_batch2_team3_expense_ems_test e where e.category_id=:categoryId order by  e.submission_date desc",nativeQuery=true)
	List<Expense> getExpensesByCategoryId(@Param("categoryId") Long categoryId);
	
	@Query(value="Select * from season2_batch2_team3_expense_ems_test e where e.status=:status order by  e.submission_date desc",nativeQuery=true)
	List<Expense> getExpenseByStatus(@Param("status") String status);
	
    @Query(value="Select * from season2_batch2_team3_expense_ems_test e where e.status=:status and e.category_id=:categoryId order by e.submission_date desc",nativeQuery=true)
    List<Expense> getExpensesByStatusByCategory(@Param ("status")String status, @Param ("categoryId")Long categoryId);
	
	@Query(value="Select sum(amount) from season2_batch2_team3_expense_ems_test e where e.category_id=:categoryId",nativeQuery=true)
	Long getAmountByCategory(@Param("categoryId") Long categoryId);
	
	@Query(value="select sum(amount) from season2_batch2_team3_expense_ems_test e where e.category_id=:categoryId and (e.approved_date  between :fromDate and :toDate) ",nativeQuery=true)
	 Long getTotalAmountByCategoryInBetween(@Param ("fromDate")LocalDate fromDate, @Param ("toDate")LocalDate toDate, @Param("categoryId") Long categoryId );
	
	
	@Query(value="Select sum(amount) from season2_batch2_team3_expense_ems_test e where e.status=:status",nativeQuery=true)
	Long getAmountByStatus(@Param("status") String status);
	
	@Query(value="select sum(amount) from season2_batch2_team3_expense_ems_test e where e.category_id=:categoryId and (e.submission_date  between :fromDate and :toDate) and (e.status=:status) ",nativeQuery=true)
	 Long getTotalAmountByCategoryByStatusInBetween(@Param ("fromDate")LocalDate fromDate, @Param ("toDate")LocalDate toDate, @Param("categoryId") Long categoryId, @Param("status") String status );
	
	@Query(value="Select * from season2_batch2_team3_expense_ems_test e where e.status=:status  and (e.associate_id=:associateId) order by  e.submission_date desc",nativeQuery=true)
	List<Expense> getExpensesByStatusByEmpId(@Param("status") String status ,@Param("associateId") String associateId);
	
	@Query(value="Select sum(amount) from season2_batch2_team3_expense_ems_test e where e.status=:status and e.associate_id=:associateId",nativeQuery=true)
	Long getAmountByStatusByEmpId(@Param("status") String status,@Param("associateId") String associate);
	
	@Query(value="Select * from season2_batch2_team3_expense_ems_test e where e.status=:status order by  e.submission_date desc",
            countQuery="Select count(*) from season2_batch2_team3_expense_ems e where e.status=:status order by  e.submission_date desc",nativeQuery=true)
    Page<Expense> getPagewiseExpensesByStatus(@Param("status") String status,Pageable pageable);
	
	@Query(value="select sum(amount) from season2_batch2_team3_expense_ems_test e where (e.status=:status) and (e.associate_id=:associateId) and (e.approved_date  between :fromDate and :toDate)" ,nativeQuery=true)
    Long getAmountBystatusByPresentYear(@Param ("fromDate")LocalDate fromDate, @Param ("toDate")LocalDate toDate,@Param("associateId") String associateId,@Param("status") String status);
	
	@Query(value="select sum(amount) from season2_batch2_team3_expense_ems_test e where e.status=:status and (e.submission_date  between :fromDate and :toDate) and ( e.associate_id=:associateId)",nativeQuery=true)
    Long getAmountByStatusLastFiveYearWise(@Param ("fromDate")LocalDate fromDate, @Param ("toDate")LocalDate toDate, @Param("status") String status ,@Param("associateId") String associateId);

}
