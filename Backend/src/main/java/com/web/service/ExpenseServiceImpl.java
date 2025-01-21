package com.web.service;


import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.web.dto.ExpenseDto;
import com.web.exception.ExpenseException;
import com.web.repository.ExpenseRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.web.entity.Expense;
import com.web.util.ImageUtils;
//@Transactional
@Service
public class ExpenseServiceImpl implements ExpenseService {

	@Autowired
	private ExpenseRepository expenseRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public List<ExpenseDto> getAllExpenses() throws ExpenseException {
		
		try {
			List<Expense> expenseList=expenseRepository.findAll();
//			System.out.println(expenseList);
			List<ExpenseDto> expenseDTOList=new ArrayList<>();
			expenseList.forEach(
					(expense) -> 
						{expenseDTOList.add(modelMapper.map(expense, ExpenseDto.class));}
					);
//			expenseDTOList.forEach(System.out::println);
			return expenseDTOList;
		}catch(DataAccessException e) {
			throw new ExpenseException(e.getMessage(),e);
		}
	}

	@Override
	public ExpenseDto getExpenseById(Long id) throws ExpenseException {
		Optional<Expense> expense = expenseRepository.findById(id);
		if(expense.isPresent()) {
			return  modelMapper.map(expense.get(),  ExpenseDto.class);
		}
		else {
			return null;
		}
	}

	@Override
	public ExpenseDto deleteExpenseById(Long id) throws ExpenseException {
		Optional<Expense> expense=expenseRepository.findById(id);
		expenseRepository.deleteById(id);
		return modelMapper.map(expense, ExpenseDto.class);
	}

	@Override
	public ExpenseDto addExpense(ExpenseDto expense) throws ExpenseException {
		Expense newExpense = expenseRepository.save(modelMapper.map(expense, Expense.class));
		return modelMapper.map(newExpense, ExpenseDto.class);
		
	}


	@Override
	public Boolean rejectExpense(Long expId) throws ExpenseException{
		try {
			Optional<Expense> expense=expenseRepository.findById(expId);
			if(!expense.isEmpty()) {
			expense.get().setStatus("Rejected");
			return true;
			}
			return false;
			
		}catch(DataAccessException e) {
			throw new ExpenseException(e.getMessage(),e);
		}
	}

	@Override
	public Boolean acceptExpense(Long expId) throws ExpenseException {
		try {
			Optional<Expense> expense=expenseRepository.findById(expId);
			if(!expense.isEmpty()) {
			expense.get().setStatus("Approved");
			return true;
			}
			return false;
			
		}catch(DataAccessException e) {
			throw new ExpenseException(e.getMessage(),e);
		}
	}



	@Override
	public List<ExpenseDto> getExpensesByEmpId(String empId) throws ExpenseException {
		try {
			List<Expense> expenseList=expenseRepository.getExpensesByEmpId(empId);
			List<ExpenseDto> expenseDtoList=new ArrayList<>();
			
			expenseList.forEach(expense -> {
				expenseDtoList.add(modelMapper.map(expense, ExpenseDto.class));
			});
			
			return expenseDtoList;
			
			
		}catch(DataAccessException e) {
			throw new ExpenseException(e.getMessage(),e);
		}
	}
	






	@Override
	public ExpenseDto updateExpense(ExpenseDto expenseDto) throws ExpenseException {
		try {
			Expense  expense=expenseRepository.save(modelMapper.map(expenseDto, Expense.class)); 
            return modelMapper.map( expense, ExpenseDto.class);
		}catch(DataAccessException e) {
			throw new ExpenseException(e.getMessage(),e);
		}
	}

	

	@Override
	public List<ExpenseDto> getExpensesByCategoryId(Long categoryId) throws ExpenseException {
		try {
		List<Expense> expenseList=expenseRepository.getExpensesByCategoryId(categoryId);
		List<ExpenseDto> expenseDtoList=new ArrayList<>();
		
		expenseList.forEach(expense -> {
			expenseDtoList.add(modelMapper.map(expense, ExpenseDto.class));
		});
		
		return expenseDtoList;
		
		
	}catch(DataAccessException e) {
		throw new ExpenseException(e.getMessage(),e);
	}
	}

	@Override
	public List<ExpenseDto> getExpensesByStatus(String status) throws ExpenseException {
		try {
			List<Expense> expenseList=expenseRepository.getExpenseByStatus(status);
			List<ExpenseDto> expenseDtoList=new ArrayList<>();
			
			expenseList.forEach(expense -> {
				expenseDtoList.add(modelMapper.map(expense, ExpenseDto.class));
			});
			
			return expenseDtoList;
			
			
		}catch(DataAccessException e) {
			throw new ExpenseException(e.getMessage(),e);
		}
	}

	

	@Override
    public List<ExpenseDto> getExpensesByStatusByCategory(String status, Long categoryId) throws ExpenseException {
        try {
            List<Expense> expenseList=expenseRepository.getExpensesByStatusByCategory(status,categoryId);
            //System.out.println(expenseList.size());
            List<ExpenseDto> expenseDtoList=new ArrayList<>();
            
            expenseList.forEach(expense -> {
                expenseDtoList.add(modelMapper.map(expense, ExpenseDto.class));
            });
            
            return expenseDtoList;
            
            
        }catch(DataAccessException e) {
            throw new ExpenseException(e.getMessage(),e);
        }
    }
	
	@Override
	public Long getAmountByCategory(Long categoryId) throws ExpenseException {
		  try {
	          Long amount=expenseRepository.getAmountByCategory(categoryId);
	           return amount; 
	            
	        }catch(DataAccessException e) {
	            throw new ExpenseException(e.getMessage(),e);
	        }
	}

	@Override
	public Long getTotalAmountByCategoryInBetween(LocalDate fromDate, LocalDate toDate, Long categoryId)
			throws ExpenseException {
		 try {
	          Long amount=expenseRepository.getTotalAmountByCategoryInBetween(fromDate,toDate,categoryId);
	           return amount; 
	            
	        }catch(DataAccessException e) {
	            throw new ExpenseException(e.getMessage(),e);
	        }
		
	}

	@Override
	public Long getAmountByStatus(String status) throws ExpenseException {
		 try {
	          Long amount=expenseRepository.getAmountByStatus(status);
	           return amount; 
	            
	        }catch(DataAccessException e) {
	            throw new ExpenseException(e.getMessage(),e);
	        }
	}

	@Override
	public Long getTotalAmountByCategoryByStatusInBetween(LocalDate fromDate, LocalDate toDate, Long categoryId,
			String status) throws ExpenseException {
		 try {
	          Long amount=expenseRepository.getTotalAmountByCategoryByStatusInBetween(fromDate,toDate,categoryId,status);
	          if(amount==null)
	        	  return 0L;
	           
	            
	          else
	        	  return amount; 
	        }catch(DataAccessException e) {
	            throw new ExpenseException(e.getMessage(),e);
	        }
		
	}
	
	@Override
    public String addExpenseById(MultipartFile file, Long id) throws ExpenseException {
        if(!file.isEmpty()) {
            try {
                byte[] compressedImageData = ImageUtils.compressImage(file.getBytes());
                Optional<Expense> optional = expenseRepository.findById(id);
                Expense customer = optional.orElseThrow(() -> new
                        ExpenseException("CUSTOMER_NOT_FOUND"));
                customer.setReceipt(compressedImageData);
                Expense expense= expenseRepository.save(customer);
                
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            }
            // TODO Auto-generated method stub
            return "Done";
    }

    @Override
    public byte[] getReceiptByExpId(Long id) throws ExpenseException {
        Optional<Expense>receipt = expenseRepository.findById(id);
        byte[]receipts=ImageUtils.decompressImage(receipt.get().getReceipt());
        return receipts;
    }
//
//	@Override
//	public List<ExpenseDto> getPagewiseExpensesByCategoryId(int pageno, int rows,Long categoryId) throws ExpenseException {
//		try {
//			Pageable pageable = PageRequest.of(pageno, rows);
//
//			Page<Expense> page =expenseRepository.get.getExpensesByCategoryId(pageable);
//			if(!page.hasContent()) {
//				throw new ExpenseException("No records in the given page");
//			}
//			List<Expense> expenseEntityList =  page.getContent();
//
//			List<ExpenseDto> expenseDtoList = new ArrayList<>();
//
//			expenseEntityList.forEach(expense  -> {
//
//				expenseDtoList.add(modelMapper.map(expense, ExpenseDto.class));
//			});
//
//			return expenseDtoList;
//			
//		}catch(DataAccessException e) {
//			throw new ExpenseException(e.getMessage(),e);
//		}
//	}

	@Override
	public List<ExpenseDto> getExpensesByStatusByEmpId(String status, String associateId) throws ExpenseException {
		try {
			
			List<Expense> expenseList=expenseRepository.getExpensesByStatusByEmpId(status,associateId);
			List<ExpenseDto> expenseDtoList=new ArrayList<>();
			
			expenseList.forEach(expense -> {
				expenseDtoList.add(modelMapper.map(expense, ExpenseDto.class));
			});
			
			return expenseDtoList;
			
		}catch(DataAccessException e) {
			throw new ExpenseException(e.getMessage(),e);
		}
	}

	@Override
	public Long getAmountByStatusByEmpId(String status, String associateId) throws ExpenseException {
		 try {
	          Long amount=expenseRepository.getAmountByStatusByEmpId(status,associateId);
	          if(amount==null)
	        	  return 0L;
	           
	            
	          else
	        	  return amount; 
	           
	            
	        }catch(DataAccessException e) {
	            throw new ExpenseException(e.getMessage(),e);
	        }
	}
	
    public List<ExpenseDto> getPagewiseExpensesByStatus(int pageno, int rows, String status)
            throws ExpenseException {
        try {
            Pageable pageable = PageRequest.of(pageno, rows);

            Page<Expense> page =expenseRepository.getPagewiseExpensesByStatus(status,pageable);
            if(!page.hasContent()) {
                throw new ExpenseException("No records in the given page");
            }
            List<Expense> expenseEntityList =  page.getContent();

            List<ExpenseDto> expenseDtoList = new ArrayList<>();

            expenseEntityList.forEach(expense  -> {

                expenseDtoList.add(modelMapper.map(expense, ExpenseDto.class));
            });

            return expenseDtoList;
            
        }catch(DataAccessException e) {
            throw new ExpenseException(e.getMessage(),e);
        }
        
    }

    @Override
    public Long getAmountBystatusByPresentYear(LocalDate fromDate,LocalDate toDate,String status,String associateId) throws ExpenseException {
        
        try {
            
            return expenseRepository.getAmountBystatusByPresentYear(fromDate,toDate,associateId,status);
        }catch(DataAccessException e) {
            throw new ExpenseException(e.getMessage(),e);
        }
    }
    
    @Override
    public Long getAmountByStatusLastFiveYearWise(LocalDate fromDate, LocalDate toDate, String status,String empId)
            throws ExpenseException {
         try {
              Long amount=expenseRepository.getAmountByStatusLastFiveYearWise(fromDate,toDate,status,empId);
              if(amount==null)
                  return 0L;
               
                
              else
                  return amount; 
                
            }catch(DataAccessException e) {
                throw new ExpenseException(e.getMessage(),e);
            }
        
    }

}
