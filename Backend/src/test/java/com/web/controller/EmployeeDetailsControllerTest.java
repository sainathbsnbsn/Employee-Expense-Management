package com.web.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import com.web.ExpenseManagementApplication;
import com.web.dto.EmployeeDetailsDto;
import com.web.exception.EmployeeException;
import com.web.service.EmployeeDetailsService;
import com.web.service.JwtService;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { ExpenseManagementApplication.class })
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = ExpenseManagementApplication.class)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class EmployeeDetailsControllerTest {

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtService jwtService;
    
    @InjectMocks
    private EmployeeDetailsController employeeDetailsController;
    
    @MockBean
    private EmployeeDetailsService empService;
    
    
    //private final MockMvc mockMvc1 = MockMvcBuilders.standaloneSetup(employeeDetailsController).build();

    @Test
    public void testGetWorkerId() throws Exception {
        given(empService.getWorkerIdByEmployeeId("E17NH5C0CBD8F2FG")).willReturn("$2a$10$kSUUy4fW6XdOpZjcJBXAbu04uC2jvd7EUCU1wbJdd0UVL3VcxoUc6");

        mockMvc.perform(get("/expense/employee/getworkerId/E17NH5C0CBD8F2FG")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtService.generateToken("E17NH5C0CBD8F2FG")))
                .andExpect(status().isOk());
             

        verify(empService, times(1)).getWorkerIdByEmployeeId("E17NH5C0CBD8F2FG");
        reset(empService);

    }

    @Test
    public void testGetEmployeeById() throws Exception {
        EmployeeDetailsDto employeeDto = new EmployeeDetailsDto("GGGAV8HGSGVDCB31",
                "NEHA.KAPOOR@web.COM",
                "EE 3PSP",
                LocalDate.of(1970, 9, 3),
                "E1NS5WYJCKN5V0AK",
                "10000020",
                "Manager",0L,0L
                );
        given(empService.getEmployeeById("GGGAV8HGSGVDCB31")).willReturn(employeeDto);

        mockMvc.perform(get("/expense/employee/get/GGGAV8HGSGVDCB31")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtService.generateToken("GGGAV8HGSGVDCB31")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.designation").value("Manager"));

        verify(empService, times(1)).getEmployeeById("GGGAV8HGSGVDCB31");
        reset(empService);
        
    }

    @Test
    @WithMockUser(username = "E1H0A4TVX77D10BB", authorities = {"Manager"})
    public void testGetManagerByEmpIdUnAuthorized() throws Exception {
        EmployeeDetailsDto employeeDto = new EmployeeDetailsDto("E1H0A4TVX77D10BB",
                "NEHA.KAPOOR@web.COM",
                "EE 3PSP",
                LocalDate.of(1970, 9, 3),
                "E1NS5WYJCKN5V0AK",
                "10000020",
                "Manager",0L,0L
                );
        
        when(empService.getManagerByempId("E1H0A4TVX77D10BB")).thenReturn(employeeDto);
        mockMvc.perform(get("/expense/employee/getManagerByempId/E1H0A4TVX77D10BB")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtService.generateToken("E1H0A4TVX77D10BB")))
                .andExpect(status().isForbidden());
                
        verify(empService, times(0)).getManagerByempId("E1H0A4TVX77D10BB");
        reset(empService);
    }
    
    @Test
    public void testFindEmployeesDetailsByManagerId() throws EmployeeException {
        String managerId = "E1NS5WYJCKN5V0AK";
        List<EmployeeDetailsDto> expectedEmployeeDetails = Collections.singletonList(new EmployeeDetailsDto());

        when(empService.getEmployeeDetailsByManagerId(managerId)).thenReturn(expectedEmployeeDetails);

        ResponseEntity<List<EmployeeDetailsDto>> response = employeeDetailsController.findEmployeesDetailsByManagerId(managerId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedEmployeeDetails, response.getBody());
    }
    
    @Test
    public void testFindEmployeesDetailsByManagerIdError() throws EmployeeException {
        // Arrange
        String managerId = "E1NS5WYJCKN5V0AK";

        Mockito.doThrow(new EmployeeException("Error message")).when(empService).getEmployeeDetailsByManagerId(managerId);

        // Act and Assert
        ResponseStatusException exception = org.junit.jupiter.api.Assertions.assertThrows(ResponseStatusException.class,
                () -> employeeDetailsController.findEmployeesDetailsByManagerId(managerId));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    @Test
    public void testGetAllEmployees() throws EmployeeException {
        List<EmployeeDetailsDto> expectedEmployeeList = Collections.singletonList(new EmployeeDetailsDto());

        when(empService.getAllEmployees()).thenReturn(expectedEmployeeList);

        ResponseEntity<List<EmployeeDetailsDto>> response = employeeDetailsController.getAllEmployees();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedEmployeeList, response.getBody());
    }
    
    @Test
    public void testGetAllEmployeesError() throws EmployeeException {
        Mockito.doThrow(new EmployeeException("Error message")).when(empService).getAllEmployees();

        ResponseStatusException exception = org.junit.jupiter.api.Assertions.assertThrows(ResponseStatusException.class,
                () -> employeeDetailsController.getAllEmployees());

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }
   
}

