package com.web.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.web.entity.EmployeeDetails;

@Repository
public interface EmployeeRepo extends JpaRepository<EmployeeDetails,String> {
	
	
	//custom query
	
	@Query(value="SELECT  * FROM  user_details_table a WHERE a.manager_id = :managerId",nativeQuery=true)
	public List<EmployeeDetails> findEmployeesDetailsByManagerId(@Param("managerId") String managerId);
	
	public Optional<EmployeeDetails> findByAssociateId(String username);
	
	@Query(value="select * from user_details_table a where a.associate_id =:managerId",nativeQuery=true)
	public EmployeeDetails getManagerByempId(@Param("managerId") String managerId);
	
}
