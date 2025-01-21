package com.web.service;

import java.util.List;

import com.web.dto.EmployeeDetailsDto;
import com.web.exception.EmployeeException;

public interface EmployeeDetailsService    {
	public abstract List<EmployeeDetailsDto>  getAllEmployees() throws EmployeeException;
	public abstract EmployeeDetailsDto addEmployee(EmployeeDetailsDto employee) throws EmployeeException;
	public abstract EmployeeDetailsDto getEmployeeById(String id)throws EmployeeException;
	public abstract String getWorkerIdByEmployeeId(String associateId) throws EmployeeException;
	//custom query
	public abstract List<EmployeeDetailsDto> getEmployeeDetailsByManagerId(String managerId)throws EmployeeException;
	//public abstract EmployeeDetailsDto getEmployeesById(String empid);
	public abstract EmployeeDetailsDto getManagerByempId(String associateId) throws EmployeeException;
	
	
	
	
	//newlyAdded
    public abstract EmployeeDetailsDto Update(EmployeeDetailsDto e) throws EmployeeException;

}
