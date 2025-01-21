package com.web.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.web.ExpenseManagementApplication;
import com.web.dto.EmployeeDetailsDto;
import com.web.entity.EmployeeDetails;
import com.web.exception.EmployeeException;
import com.web.repository.EmployeeRepo;



@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = ExpenseManagementApplication.class)
@AutoConfigureMockMvc 
class EmployeeDetailsServiceImplTest {
    
//     @Autowired
//     private EmployeeDetailsService empService;
    
    @Mock
    private EmployeeRepo repo;
    
    @InjectMocks
    private EmployeeDetailsServiceImpl service;
    
    

    private ModelMapper modelMapper=new ModelMapper();

    
 
    @Test
    void testGetWorkerIdByEmpId() throws EmployeeException {
        EmployeeDetails employee1=new EmployeeDetails("Man500001","ramesh@gmail.com","ramesh",LocalDate.of(2001,11,19),"Man5000002","fsghgjh","Manager",60000L,1000000L);
        Mockito.when(repo.findById("Man500001")).thenReturn(Optional.of(employee1));
        assertEquals(employee1.getWorkerId(),service.getWorkerIdByEmployeeId("Man500001"));
    }
    
    @Test
    void testGetEmployeeDetailsByManagerId() throws EmployeeException {
        EmployeeDetails employee1=new EmployeeDetails("Emp500001","ramesh@gmail.com","ramesh",LocalDate.of(2001,11,19),"Man5000001","fsghgjh","Manager",60000L,1000000L);
        EmployeeDetails employee2=new EmployeeDetails("Emp500002","suresh@gmail.com","suresh",LocalDate.of(2002,06,9),"Man5000001","fsghgjh","Employee",null,null);
        EmployeeDetails employee3=new EmployeeDetails("Emp500003","mahesh@gmail.com","mahesh",LocalDate.of(2000,11,14),"Man5000001","fsghgjh","Employee",null,null);
        List<EmployeeDetails> listOfEmployees=new ArrayList<EmployeeDetails>();
        listOfEmployees.add(employee1);
        listOfEmployees.add(employee2);
        listOfEmployees.add(employee3);
        List<EmployeeDetails> listofExpcetedEmployees=new ArrayList<EmployeeDetails>();
        listofExpcetedEmployees.add(employee1);
        listofExpcetedEmployees.add(employee2);
        listofExpcetedEmployees.add(employee3);
        Mockito.when(repo.findEmployeesDetailsByManagerId("Man5000001")).thenReturn(listofExpcetedEmployees);
        List<EmployeeDetailsDto> res=service.getEmployeeDetailsByManagerId("Man500001");
        assertThat(res).hasSize(0);
        
    }

}
