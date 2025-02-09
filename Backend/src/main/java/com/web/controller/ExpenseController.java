package com.web.controller;

import static com.web.config.SwaggerConfig.BEARER_KEY_SECURITY_SCHEME;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.web.dto.ExpenseDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.web.dto.EmployeeDetailsDto;
import com.web.dto.PayloadDto;
import com.web.dto.PayloadDtoWithStatus;
import com.web.exception.EmployeeException;
import com.web.exception.ExpenseException;
import com.web.service.EmployeeDetailsService;
import com.web.service.ExpenseService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.Min;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/expense/report")
public class ExpenseController {
	
	@Autowired
	private ExpenseService expenseService;
	
	 @Autowired
	 private EmployeeDetailsService empService;
	 
	 @Autowired
	 private ModelMapper modelMapper;
	    
	
	// http://localhost:8888/expense/add
	
	/*
{ 
	
	 "expenseId":null,
	
	  "categoryId":3,
	
	 "amount": 1000,
	
	  "managerId": "GGGAV8HGSGVDCB1",
	
	  "status": "accepted",
	
	  "managerComments" : "need some more proofs",
	
	  "employeeComments" : "Please give accpetance",
	
	  "submissionDate" : "2023-10-10",

      "approvedDate" : "2023-12-10",
	
	 "recipt":null,

    "associateId" : "E1GRZY9G034NW2KS"


}
	 */
	 
	 
//------------------------------------------Employee--------------------------------------------------
	
	// http://localhost:8888/expense/report/add
	 @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
	@PostMapping("/add")
	@PreAuthorize("hasAuthority('Employee') or"
			+ " hasAuthority('Senior Payroll Specialist') or"
			+ " hasAuthority('Site Engineer I-A')"
			+ " or hasAuthority('Manager')")
	public ResponseEntity<ExpenseDto> addExpense(@RequestBody ExpenseDto expenseDto,
                                                 BindingResult bindingResult ) throws ExpenseException{
		try {
			if(bindingResult.hasErrors()) {
				throw new ExpenseException(bindingResult.getAllErrors().toString());
			}
			ExpenseDto employeeNew=expenseService.addExpense(expenseDto);
			return ResponseEntity.ok(employeeNew);
			
		}catch(ExpenseException e){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
		}
		
	}
	 
	//Manager has seperate getExpenseByid method where he can access every expense.
	//  http://localhost:8888/expense/report/get/{expenseId}/{empid}
	 @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
		@GetMapping("/get/{expenseId}/{empid}")
		@PreAuthorize("hasAuthority('Employee') or"
				+ " hasAuthority('Senior Payroll Specialist') or"
				+ " hasAuthority('Site Engineer I-A')"
				+ " or hasAuthority('Manager')")
		public ResponseEntity<ExpenseDto> getExpenseById(@PathVariable("expenseId") Long expenseId,@PathVariable("empid") String empId) throws ExpenseException{ 
			System.out.println("in method");
			try {
				ExpenseDto expenseDto=expenseService.getExpenseById(expenseId);
				if(expenseDto.getAssociateId().equals(AuthenticateController.getUser())) {
					System.out.println("in return");
					return ResponseEntity.ok(expenseDto);
		    	}
				System.out.println("out of if statement");
				throw new ExpenseException();
				
			}catch(ExpenseException e) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
			}
		}
		
		//another function
		//http://localhost:8888/expense/report/getExpenses/{empid}
	 @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
		@GetMapping("/getExpenses/{empid}")
		@PreAuthorize("hasAuthority('Senior Payroll Specialist') or hasAuthority('Site Engineer I-A') or hasAuthority('Manager')")
		public ResponseEntity<List<ExpenseDto>>getExpensesByEmpId(@PathVariable String empid) throws ExpenseException{ 
			
			try {
				List<ExpenseDto> expenseDto=expenseService.getExpensesByEmpId(empid);
				if(empid.equals(AuthenticateController.getUser())) {
					return ResponseEntity.ok(expenseDto);
		    	}
				throw new ExpenseException();
			}catch(ExpenseException e) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
			}
		}
		
		
		
			// http://localhost:8888/expense/report/update/402
	 @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
			@PutMapping("/update/{expenseId}")
			@PreAuthorize("hasAuthority('Senior Payroll Specialist') or hasAuthority('Site Engineer I-A') or hasAuthority('Manager')")
			public ResponseEntity<ExpenseDto> updateExpense(@PathVariable Long expenseId ,@RequestBody ExpenseDto userDTO,
					BindingResult bindingResult) throws ExpenseException{		
			try {
				if(bindingResult.hasErrors()) {
					throw new EmployeeException(bindingResult.getAllErrors().toString());
				}
				if(userDTO.getAssociateId().equals(AuthenticateController.getUser())) {
					ExpenseDto expenseDto = expenseService.getExpenseById(expenseId);	
					expenseDto.setCategoryId(userDTO.getCategoryId());
					expenseDto.setAmount(userDTO.getAmount());
					expenseDto.setStatus(userDTO.getStatus());
					expenseDto.setManagerComments(userDTO.getManagerComments());
					expenseDto.setEmployeeComments(userDTO.getEmployeeComments());
					expenseDto.setSubmissionDate(userDTO.getSubmissionDate());
					expenseDto.setBusinessPurpose(userDTO.getBusinessPurpose());
					expenseDto.setCategory(userDTO.getCategory());
					expenseDto.setApprovedDate(userDTO.getApprovedDate());
					//newly added
					   if(userDTO.getStatus().equals("approved")) {
			                EmployeeDetailsDto  e = empService.getEmployeeById(expenseDto.getAssociateId());
			        //Employee fund released
			            Long total = e.getFundsReleased()+userDTO.getAmount();
			            e.setFundsReleased(total);
			            EmployeeDetailsDto  ep = empService.Update(e);
			            
//			        Manager Fund Released
			            e=empService.getEmployeeById("222222");
			            Long total1 = e.getFundsReleased()+userDTO.getAmount();
			            e.setFundsReleased(total1);
			            EmployeeDetailsDto  m = empService.Update(e);
			            }
					
					
					
					
				
					ExpenseDto updatedExpenseDto = expenseService.updateExpense(expenseDto);
					return ResponseEntity.ok(updatedExpenseDto);
		    	}
				throw new ExpenseException();
				
			}catch(EmployeeException e) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
			}
			
			}
			
		
			// http://localhost:8888/expense/report/delete/{expenseId}
//	 @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
			@DeleteMapping("/delete/{expenseId}")
//			@PreAuthorize("hasAuthority('Senior Payroll Specialist') or hasAuthority('Site Engineer I-A')")
			public ResponseEntity<ExpenseDto> deleteExpenseById(@PathVariable("expenseId") Long expenseId) throws ExpenseException   {
				try {
					ExpenseDto employeeDTO =  expenseService.deleteExpenseById(expenseId);
					
					return ResponseEntity.ok(employeeDTO);
				}catch(ExpenseException e) {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
				}
			}
			
		      
//		        http://localhost:8888/expense/getTotalAmountByCategoryInBetween
		        /*
		         * {
   					"fromDate" : "2021-01-01",
   					"toDate" : "2023-12-12",
   					"categoryId" : 2
					}
		         */
	

	 
//-----------------------------------------Manager-------------------------------------------------------------
			
			// http://localhost:8888/expense/report/getAmountByCategory/{null values in database}
	 @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
	        @GetMapping("/getAmountByCategory/{categoryId}")
	        @PreAuthorize("hasAuthority('Manager')")
	        public ResponseEntity<Long> getAmountByCategory(@PathVariable("categoryId") Long categoryId)
	          throws ExpenseException{ 
	            try {
	            		Long amount=expenseService.getAmountByCategory(categoryId);
	                return ResponseEntity.ok(amount);
	            }catch(ExpenseException e) {
	                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
	            }
	        }
	        
	  
	        
//	    //  http://localhost:8888/expense/report/get/402
	 @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
	    	@GetMapping("/get/{expenseId}")
	        @PreAuthorize("hasAuthority('Manager')")
	    	public ResponseEntity<ExpenseDto> getExpenseById(@PathVariable("expenseId") Long expenseId) throws ExpenseException{ 
	    		try {
	    			ExpenseDto expenseDto=expenseService.getExpenseById(expenseId);
	    			return ResponseEntity.ok(expenseDto);
	    		}catch(ExpenseException e) {
	    			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
    		}
	    	}
		        
		        
		        
	//http://localhost:8888/expense/report/allexpenses   
	 @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
	@GetMapping("/allexpenses")
	@PreAuthorize("hasAuthority('Manager')")
	public ResponseEntity<List<ExpenseDto>> getAllExpenses() throws ExpenseException{
		try {
			List<ExpenseDto> expenseList=expenseService.getAllExpenses();
//			System.out.println("controller------------------------------------------------"+expenseList.size());
			return ResponseEntity.ok(expenseList);
		}catch(ExpenseException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
		}
	}
	
	
	
	// http://localhost:8888/expense/report/reject/402
	 @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
	@PutMapping("/reject/{expenseId}")
	@PreAuthorize("hasAuthority('Manager')")
	public ResponseEntity<Boolean> rejectExpense(@PathVariable("expenseId") Long expenseId) throws ExpenseException{ 
		try {
			boolean success=expenseService.rejectExpense(expenseId);
			return ResponseEntity.ok(success);
		}catch(ExpenseException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
		}
	}
	
	// http://localhost:8888/expense/report/accept/402
	 @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
		@PutMapping("/accept/{expenseId}")
		@PreAuthorize("hasAuthority('Manager')")
		public ResponseEntity<Boolean> acceptExpense(@PathVariable("expenseId") Long expenseId) throws ExpenseException{ 
			try {
				boolean success=expenseService.acceptExpense(expenseId);
				return ResponseEntity.ok(success);
			}catch(ExpenseException e) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
			}
		}
		

		
		
	
		//http://localhost:8888/expense/report/getExpensesbycategory/1
	 @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
		@GetMapping("/getExpensesbycategory/{categoryId}")
		@PreAuthorize("hasAuthority('Manager')")
		public ResponseEntity<List<ExpenseDto>>getExpensesByCategoryId(@PathVariable Long categoryId) throws ExpenseException{ 
			
			try {
				List<ExpenseDto> expenseDto=expenseService.getExpensesByCategoryId(categoryId);
				return ResponseEntity.ok(expenseDto);
			}catch(ExpenseException e) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
			}
		}
		
		//http://localhost:8888/expense/report/getExpensesByStatus/approved
	 @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
				@GetMapping("/getExpensesByStatus/{status}")
				@PreAuthorize("hasAuthority('Manager')")
				public ResponseEntity<List<ExpenseDto>>getExpensesByStatus(@PathVariable String status) throws ExpenseException{ 
					
					try {
						List<ExpenseDto> expenseDto=expenseService.getExpensesByStatus(status);
						return ResponseEntity.ok(expenseDto);
					}catch(ExpenseException e) {
						throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
					}
				}
		
				// http://localhost:8888/expense/report/bystatusbycategoryId/approved/1
	 @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
		        @GetMapping("/bystatusbycategoryId/{status}/{categoryId}")
		        @PreAuthorize("hasAuthority('Manager')")
		        public ResponseEntity<List<ExpenseDto>> getExpensesByStatusByCategory(@PathVariable("status") String status,
		                @PathVariable("categoryId")  Long categoryId) throws ExpenseException{ 
		            try {
		                List<ExpenseDto> expenseList=expenseService.getExpensesByStatusByCategory(status,categoryId);
		                return ResponseEntity.ok(expenseList);
		            }catch(ExpenseException e) {
		                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
		            }
		        }


	@Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
	@GetMapping("/getExpenseStatusCategoryEmpId/{status}/{categoryId}/{associateId}")
	@PreAuthorize("hasAuthority('Manager') or hasAuthority('Employee')")
	public ResponseEntity<List<ExpenseDto>> getExpensesByStatusByCategoryByEmpId(@PathVariable("status") String status,
																		  @PathVariable("categoryId")  Long categoryId, @PathVariable("associateId") String associateId) throws ExpenseException{
		try {
			List<ExpenseDto> expenseList=expenseService.getExpensesByStatusByCategoryByEmployeeId(status,categoryId, associateId);
			return ResponseEntity.ok(expenseList);
		}catch(ExpenseException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
		}
	}
		        
		    
		     // http://localhost:8888/expense/report/getTotalAmountByCategoryInBetween
	 @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
		    	@PostMapping("/getTotalAmountByCategoryInBetween")
		    	@PreAuthorize("hasAuthority('Manager')")
		    	public ResponseEntity<Long> getTotalAmountByCategoryInBetween( @RequestBody PayloadDto payloadDto,
		    				BindingResult bindingResult ) throws ExpenseException{
		    		try {
		    			if(bindingResult.hasErrors()) {
		    				throw new ExpenseException(bindingResult.getAllErrors().toString());
		    			}
		    			System.out.println(payloadDto.getFromDate()+" "+ payloadDto.getToDate()+" "+ payloadDto.getCategoryId());
		    			Long amount=expenseService.getTotalAmountByCategoryInBetween(payloadDto.getFromDate(), payloadDto.getToDate(),payloadDto.getCategoryId() );
		    			return ResponseEntity.ok(amount);
		    			
		    		}catch(ExpenseException e){
		    			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
		    		}
		    		
		    	}
		    	
		    	// http://localhost:8888/expense/report/getAmountByStatus/declined
	 @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
		        @GetMapping("/getAmountByStatus/{status}")
		        @PreAuthorize("hasAuthority('Manager')")
		        public ResponseEntity<Long> getAmountByStatus(@PathVariable("status") String status)
		          throws ExpenseException{ 
		            try {
		            		Long amount=expenseService.getAmountByStatus(status);
		                return ResponseEntity.ok(amount);
		            }catch(ExpenseException e) {
		                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
		            }
		        }
		        
//		        http://localhost:8888/expense/getTotalAmountByCategoryByStatusInBetween
//		        {
//		            "fromDate" : "2021-01-01",
//		            "toDate" : "2023-12-12",
//		            "categoryId" : 2,
//		            "status" : "approved"
//		        }
		        
		        // http://localhost:8888/expense/report/getTotalAmountByCategoryByStatusInBetween
	 @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
		    	@PostMapping("/getTotalAmountByCategoryByStatusInBetween")
		    	@PreAuthorize("hasAuthority('Manager')")
		    	public ResponseEntity<Long> getTotalAmountByCategoryByStatusInBetween( @RequestBody PayloadDtoWithStatus payloadDtoWithStatus,
		    				BindingResult bindingResult ) throws ExpenseException{
		    		try {
		    			if(bindingResult.hasErrors()) {
		    				throw new ExpenseException(bindingResult.getAllErrors().toString());
		    			}
		    			//System.out.println(payloadDto.getFromDate()+" "+ payloadDto.getToDate()+" "+ payloadDto.getCategoryId());
		    			Long amount=expenseService.getTotalAmountByCategoryByStatusInBetween(payloadDtoWithStatus.getFromDate(), payloadDtoWithStatus.getToDate(),payloadDtoWithStatus.getCategoryId(),payloadDtoWithStatus.getStatus());
		    			return ResponseEntity.ok(amount);
		    			
		    		}catch(ExpenseException e){
		    			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
		    		}
		    		
		    	}
		        
		    	// http://localhost:8888/expense/report/getAmountByCategoryByStatusMonthWise/2023
		    	 @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
		    	@PostMapping("/getAmountByCategoryByStatusMonthWise/{year}")
		    	@PreAuthorize("hasAuthority('Manager')")
		    	public ResponseEntity<List<Long>> getAmountByCategoryByStatusMonthWise( @PathVariable("year") String year,@RequestBody PayloadDtoWithStatus payloadDtoWithStatus,
		    				BindingResult bindingResult ) throws ExpenseException{
		    		try {
		    			if(bindingResult.hasErrors()) {
		    				throw new ExpenseException(bindingResult.getAllErrors().toString());
		    			}
		    			List<Long> amount=new ArrayList<>();
		    			
		    		   for(int i=1;i<=12;i++)
		    		   {
		    			   String M="";
		    			    
//		    			   String fromDate= year+"-10-01";
//				   	       String toDate= year+"-10-28";
		    			   if(i<10) {
					    		M=M+("0"+i);
					    	}
					    	else {
					    		M=M+i;
					    	}
		    			   String fromDate= year+"-"+M+"-01";
				   	       String toDate= year+"-"+M+"-28";
				    	 
				   	       payloadDtoWithStatus.setFromDate(LocalDate.parse(fromDate));
				   	       payloadDtoWithStatus.setToDate(LocalDate.parse(toDate));

				    	    amount.add(expenseService.getTotalAmountByCategoryByStatusInBetween(payloadDtoWithStatus.getFromDate(), payloadDtoWithStatus.getToDate(),payloadDtoWithStatus.getCategoryId(),payloadDtoWithStatus.getStatus()));
		    		   }
		    			return ResponseEntity.ok(amount);
		    			
		    		}catch(ExpenseException e){
		    			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
		    		}
		    		
		    	}
		    	
		    	//both
		    	//http://localhost:8888/expense/report/addexpensereceipt/552
		    	 @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
		         @PutMapping("/addexpensereceipt/{id}")
		         @PreAuthorize("hasAuthority('Employee') or"
		     			+ " hasAuthority('Senior Payroll Specialist') or"
		     			+ " hasAuthority('Site Engineer I-A')"
		     			+ " or hasAuthority('Manager')")
		            public String addExpenseRecipt(@PathVariable("id") Long id,@RequestParam("file") MultipartFile file) throws ExpenseException {
		                String s=expenseService.addExpenseById(file, id);
		                return s;
		            }
		            
		    
		         //http://localhost:8888/expense/report/getreceipt/552
		         @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
		         @GetMapping("/getreceipt/{id}")
//		         @PreAuthorize("hasAuthority('Manager')")
		            public ResponseEntity<?> getExpenseById1(@PathVariable("id") Long id) throws ExpenseException {
		                byte[] receipt=expenseService.getReceiptByExpId(id);
		                return ResponseEntity.status(HttpStatus.ACCEPTED)
		                                     .contentType(MediaType.valueOf("application/pdf"))
		                                     .body(receipt);
		            }
		         
		       
		       //http://localhost:8888/expense/report/getExpensesByStatusByEmpId/approved/E1GRZY9G034NW2KS
		         @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
					@GetMapping("/getExpensesByStatusByEmpId/{status}/{associateId}")
//					@PreAuthorize("hasAuthority('Employee') or"
//							+ " hasAuthority('Senior Payroll Specialist') or"
//							+ " hasAuthority('Site Engineer I-A')"
//							+ " or hasAuthority('Manager')")
					public ResponseEntity<List<ExpenseDto>>getExpensesByStatusByEmpId(@PathVariable("status") String status,@PathVariable("associateId") String associateId) throws ExpenseException{ 
						
						try {
							List<ExpenseDto> expenseDto=expenseService.getExpensesByStatusByEmpId(status,associateId);
							return ResponseEntity.ok(expenseDto);
						}catch(ExpenseException e) {
							throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
						}
					}
					
			
				 	// http://localhost:8888/expense/report/getAmountByStatusByEmpId/approved/E1GRZY9G034NW2KS
					 @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
			        @GetMapping("/getAmountByStatusByEmpId/{status}/{associateId}")
//			    	@PreAuthorize("hasAuthority('Manager')")
			        public ResponseEntity<Long> getAmountByStatusByEmpId(@PathVariable("status") String status,@PathVariable("associateId") String associateId)
			          throws ExpenseException{ 
			            try {
			            		Long amount=expenseService.getAmountByStatusByEmpId(status,associateId);
			            		System.out.println(amount);
			                return ResponseEntity.ok(amount);
			            }catch(ExpenseException e) {
			                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
			            }
			        }
			        
			        //http://localhost:8888/expense/report/getAmountBystatusByAssIdPresentYear/accepted/{associateId}
			        @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
			        @GetMapping("/getAmountBystatusByAssIdPresentYear/{status}/{associateId}")
                    @PreAuthorize("hasAuthority('Senior Payroll Specialist') or hasAuthority('Site Engineer I-A') or hasAuthority('Manager')")
                    public ResponseEntity<Long> getAmountBystatusByEmpIdPresentYear(@PathVariable("status") String status,@PathVariable("associateId") String associateId)
                      throws ExpenseException{ 
			        	 try {
	                            LocalDate fromDate=LocalDate.parse(LocalDate.now().getYear()+"-01-01");
	                            LocalDate toDate=LocalDate.now();
	                                Long amount=expenseService.getAmountBystatusByPresentYear(fromDate,toDate,status,associateId);
	                            return ResponseEntity.ok(amount);
	                        }catch(ExpenseException e) {
	                            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
	                        }
                    }
			        
			        // http://localhost:8888/expense/report/getExpensesByCategoryIdPagenation/approved/2/2
			        @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
                    @GetMapping("/getExpensesByCategoryIdPagenation/{status}/{pageno}/{rows}")
                    @PreAuthorize("hasAuthority('Manager')")
                    public ResponseEntity<List<ExpenseDto>>getExpensesByStatusPagenation(@PathVariable String status,@PathVariable("pageno") @Min(value= 1) Integer pageno,@PathVariable("rows") Integer rows) throws ExpenseException{ 
                        
                        try {
                            List<ExpenseDto> expenseDto=expenseService.getPagewiseExpensesByStatus(pageno-1,rows,status);
                            return ResponseEntity.ok(expenseDto);
                        }catch(ExpenseException e) {
                            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
                        }
                    } 
                    
                 // http://localhost:8888/expense/report/getAmountByStatusLastFiveYearWise/{empId}
                    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
                    @GetMapping("/getAmountByStatusLastFiveYearWise/{empId}")
                    @PreAuthorize("hasAuthority('Senior Payroll Specialist') or hasAuthority('Site Engineer I-A') or hasAuthority('Manager')")
                    public ResponseEntity<List<Long>> getAmountByStatusLastFiveYearWise(@PathVariable("empId") String empId ) throws ExpenseException{
                        try {
                            
                            List<Long> amount=new ArrayList<>();
                            int year =LocalDate.now().getYear();
                           for(int i=1;i<=5;i++)
                           {
                              
                                
//                               
                               String fromDate= year+"-"+"01"+"-01";
                                  String toDate= year+"-"+"12"+"-31";
                             


                                amount.add(expenseService.getAmountByStatusLastFiveYearWise(LocalDate.parse(fromDate),LocalDate.parse(toDate),"approved",empId));
                                year--;
                           }
                            return ResponseEntity.ok(amount);
                            
                        }catch(ExpenseException e){
                            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
                        }
    
                    }
		    	
                 
}
