package com.web.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.web.exception.EmployeeException;
import com.web.repository.EmployeeRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.web.dto.EmployeeDetailsDto;
import com.web.entity.EmployeeDetails;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class EmployeeDetailsServiceImpl implements EmployeeDetailsService {

	@Autowired
	private EmployeeRepo employeeRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	@Override
	public List<EmployeeDetailsDto> getAllEmployees() throws EmployeeException {
		List<EmployeeDetails>emps=employeeRepo.findAll();
		List<EmployeeDetailsDto>empdtos=emps.stream()
				                            .map(a->modelMapper.map(a, EmployeeDetailsDto.class)).toList();
		
		return empdtos;
	}

	@Override
	public EmployeeDetailsDto addEmployee(EmployeeDetailsDto employee) throws EmployeeException {
		EmployeeDetails emp=modelMapper.map(employee, EmployeeDetails.class);
		System.out.println(emp);
		emp.setWorkerId(passwordEncoder.encode(emp.getWorkerId()));
		System.out.println(emp.getWorkerId());
		employeeRepo.save(emp);
		return modelMapper.map(emp, EmployeeDetailsDto.class);
		
	}

	@Override
	public EmployeeDetailsDto getEmployeeById(String empid) throws EmployeeException {
		Optional<EmployeeDetails> emp=employeeRepo.findById(empid);
		return modelMapper.map(emp.get(), EmployeeDetailsDto.class);
	}

	

	
	
	//custom query
//	@Override
//	public List<EmployeeDetails> getEmployeeDetailsByManagerId(String managerId) throws EmployeeException {
//		return employeeRepo.findEmployeesDetailsByManagerId(managerId);
//	}
	
	@Override
    public String getWorkerIdByEmployeeId(String associateId) throws EmployeeException {
        Optional<EmployeeDetails> emp=employeeRepo.findById(associateId);
        return emp.get().getWorkerId();
    }

	@Override
	public List<EmployeeDetailsDto> getEmployeeDetailsByManagerId(String managerId) throws EmployeeException {
		
		try {
			List<EmployeeDetails> employeeList=employeeRepo.findEmployeesDetailsByManagerId(managerId);
			List<EmployeeDetailsDto> employeeDtoList=new ArrayList<>();
			
			employeeList.forEach(employee -> {
				employeeDtoList.add(modelMapper.map(employee, EmployeeDetailsDto.class));
			});
			
			return employeeDtoList;
		}catch(DataAccessException e) {
			throw new EmployeeException(e.getMessage(),e);
		}
	}
	
	@Override
	public EmployeeDetailsDto getManagerByempId(String associateId) throws EmployeeException {
	try {
	EmployeeDetailsDto employee=getEmployeeById(associateId);
	String managerId=employee.getManagerId();
	EmployeeDetailsDto manager=modelMapper.map(employeeRepo.getManagerByempId(managerId),EmployeeDetailsDto.class );
	return manager;
	}catch(DataAccessException e) {
	throw new EmployeeException(e.getMessage(),e);
	}
	}
	
	
	
	
	
	//newlyAddedd
	
	@Override
    public EmployeeDetailsDto Update(EmployeeDetailsDto employeedto) throws EmployeeException {
        try {
            
            
            EmployeeDetails emp=modelMapper.map(employeedto, EmployeeDetails.class);
            employeeRepo.save(emp);
            return modelMapper.map(emp, EmployeeDetailsDto.class);
            }catch(DataAccessException e) {
                throw new EmployeeException(e.getMessage(),e);
            }
    }

	
}
