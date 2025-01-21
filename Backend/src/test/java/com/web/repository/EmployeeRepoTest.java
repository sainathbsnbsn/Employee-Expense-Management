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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.web.entity.EmployeeDetails;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
@AutoConfigureTestDatabase(replace = Replace.NONE)
@DataJpaTest
class EmployeeRepoTest {

    @Autowired
    private TestEntityManager entityManager;
    
    @Autowired
    private EmployeeRepo repo;
     
    @Test
    void testFindById() {
        EmployeeDetails employee=new EmployeeDetails("Emp500001","tom@gmail.com","tom",LocalDate.of(2001,11,19),"Man5000001","fsgthgjh","employee",null,null);
        entityManager.persistAndFlush(employee);
        EmployeeDetails foundById=repo.findById("Emp500001").orElse(null);
        assertThat(employee.getAssociateId()).isEqualTo(foundById.getAssociateId());
    }
    
    
    @Test
    void invaildTestFindById() {
        EmployeeDetails foundById=repo.findById("1L").orElse(null);
        assertThat(foundById).isNull();
    }
    
    @Test
    void testFindAll() {
        EmployeeDetails employee1=new EmployeeDetails("Emp500001","ramesh@gmail.com","ramesh",LocalDate.of(2001,11,19),"Man5000001","fsgthgjh","employee",null,null);
        EmployeeDetails employee2=new EmployeeDetails("Emp500002","suresh@gmail.com","suresh",LocalDate.of(2001,06,9),"Man5000001","fsgthgjh","employee",null,null);
        EmployeeDetails employee3=new EmployeeDetails("Emp500003","mahesh@gmail.com","mahesh",LocalDate.of(2000,11,13),"Man5000001","fsgthgjh","employee",null,null);
        List<EmployeeDetails> listOfEmployees=repo.findAll();
        entityManager.persist(employee1);
        entityManager.persist(employee2);
        entityManager.persist(employee3);
        entityManager.flush();
        List<EmployeeDetails> listOfEmployees2=repo.findAll();
        assertThat(listOfEmployees.size()+3).isEqualTo(listOfEmployees2.size());        
    }
    
    @Test 
    void testEmpByManId() {
        EmployeeDetails employee1=new EmployeeDetails("Emp500001","ramesh@gmail.com","ramesh",LocalDate.of(2001,11,19),"Man5000001","fsgthgjh","employee",null,null);
        EmployeeDetails employee2=new EmployeeDetails("Emp500002","suresh@gmail.com","suresh",LocalDate.of(2001,06,9),"Man5000002","fsgthgjh","employee",null,null);
        EmployeeDetails employee3=new EmployeeDetails("Emp500003","mahesh@gmail.com","mahesh",LocalDate.of(2000,11,13),"Man5000001","fsgthgjh","employee",null,null);
        entityManager.persist(employee1);
        entityManager.persist(employee2);
        entityManager.persist(employee3);
        entityManager.flush();
        List<EmployeeDetails> listOfEmployees=repo.findEmployeesDetailsByManagerId("Man5000002");
        assertThat(1).isEqualTo(listOfEmployees.size());
        
    }

}
