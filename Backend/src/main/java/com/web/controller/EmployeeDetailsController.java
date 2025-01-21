package com.web.controller;

import static com.web.config.SwaggerConfig.BEARER_KEY_SECURITY_SCHEME;

import java.util.List;

import com.web.exception.ExpenseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.web.dto.EmployeeDetailsDto;
import com.web.exception.EmployeeException;
import com.web.service.EmployeeDetailsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@CrossOrigin(origins="*")
@RequestMapping("/expense/employee")
public class EmployeeDetailsController {
    
    @Autowired
    private EmployeeDetailsService empService;
    
    //----------------------------------Employee-------------------------------------------------
    
    // http://localhost:8888/expense/employee/getworkerId/GGGAV8HGSGVDCB31
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @GetMapping("/getworkerId/{id}")
    @PreAuthorize("hasAuthority('Senior Payroll Specialist') or hasAuthority('Site Engineer I-A')")
    public String getWorkerId(@PathVariable("id") String assId) throws EmployeeException {
    	if(assId.equals(AuthenticateController.getUser())) {
            return empService.getWorkerIdByEmployeeId(assId);
    	}
    	return "No access";
    }
    
  
	//  http://localhost:8888/expense/employee/get/GGGAV8HGSGVDCB31
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
	@GetMapping("/get/{empId}")
	@PreAuthorize("hasAuthority('Senior Payroll Specialist') or hasAuthority('Site Engineer I-A') or hasAuthority('Manager')")
	public ResponseEntity<EmployeeDetailsDto> getEmployeeById(@PathVariable("empId") String empId) throws EmployeeException{ 
		try {
			EmployeeDetailsDto employee=empService.getEmployeeById(empId);
			if(empId.equals(AuthenticateController.getUser())) {
				return ResponseEntity.ok(employee);
	    	}
			throw new EmployeeException();
			}catch(EmployeeException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
		}
	}
	
//-----------------------------------------Manager--------------------------------------------------------	
	
	  // http://localhost:8888/expense/employee/getEmployeebymngId/E1NS5WYJCKN5V0AK
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
		@GetMapping("/getEmployeebymngId/{managerId}")
		@PreAuthorize("hasAuthority('Manager')")
		public ResponseEntity<List<EmployeeDetailsDto>>findEmployeesDetailsByManagerId(@PathVariable("managerId") String managerId) throws EmployeeException{ 
			try {
				List<EmployeeDetailsDto> employeeDetailsDto=empService.getEmployeeDetailsByManagerId(managerId);
				return ResponseEntity.ok(employeeDetailsDto);
			}catch(EmployeeException e) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
			}
		}
		
		// http://localhost:8888/expense/employee/getall
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
		@GetMapping("/getall")
		@PreAuthorize("hasAuthority('Manager')")
		public ResponseEntity<List<EmployeeDetailsDto>> getAllEmployees()throws EmployeeException{
			try {
				List<EmployeeDetailsDto> employeeList=empService.getAllEmployees();
				return ResponseEntity.ok(employeeList);
			}catch(EmployeeException e) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
			}
		}
		// http://localhost:8888/expense/employee/getManagerByempId/E1MNF0AE01F0TRNB
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
		@GetMapping("getManagerByempId/{empId}")
		public ResponseEntity<EmployeeDetailsDto> getManagerByempId(@PathVariable("empId") String empId ) throws EmployeeException{
		try {
		EmployeeDetailsDto manager=empService.getManagerByempId(empId);
		return ResponseEntity.ok(manager);
		}catch(EmployeeException e) {
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
		}
		}
		
		
		
		//newlyAdded
		
		
		//http://localhost:8888/expense/employee/update/associateId
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
        @PutMapping("/update/{associateId}")
        public ResponseEntity<EmployeeDetailsDto> updateEmployee(@PathVariable String associateId ,@RequestBody  EmployeeDetailsDto userDTO,
                BindingResult bindingResult) throws ExpenseException {
        
        try {
            if(bindingResult.hasErrors()) {
                throw new EmployeeException(bindingResult.getAllErrors().toString());
            }
            
            EmployeeDetailsDto employeeDto = empService.getEmployeeById(associateId);
            employeeDto.setEmail(userDTO.getEmail());
            employeeDto.setName(userDTO.getName());
            employeeDto.setBirthDate(userDTO.getBirthDate());
            employeeDto.setManagerId(userDTO.getManagerId());
            employeeDto.setWorkerId(userDTO.getWorkerId());
            employeeDto.setDesignation(userDTO.getDesignation());
            employeeDto.setFundsAllocated(userDTO.getFundsAllocated());
            employeeDto.setFundsReleased(userDTO.getFundsReleased());
            EmployeeDetailsDto updatedExpenseDto = empService.Update(employeeDto);
            return ResponseEntity.ok(updatedExpenseDto);
        }catch(EmployeeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
        
        }
}
