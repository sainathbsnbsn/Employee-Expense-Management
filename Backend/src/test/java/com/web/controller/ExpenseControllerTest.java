package com.web.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.server.ResponseStatusException;

import com.web.ExpenseManagementApplication;
import com.web.dto.ExpenseDto;
import com.web.exception.ExpenseException;
import com.web.service.ExpenseService;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { ExpenseManagementApplication.class })
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = ExpenseManagementApplication.class)
@AutoConfigureMockMvc
class ExpenseControllerTest {
    
    @Autowired
    private MockMvc mvc;


    @MockBean
    private ExpenseService expenseService;
    
    @Autowired
    ExpenseController expenseController;
    
    
    @Test
    @WithMockUser(username = "G3CABGYP28BYXEDF", authorities = {"Manager"})
    public void testAddExpense() throws ExpenseException {
        ExpenseDto expenseDto = new ExpenseDto(1L, "E17NH5C0CBD8F2FG",123L,"Travel",500L,"MNG123","Pending","Approval needed","Business trip expenses",LocalDate.now(),"Business trip",null,new byte[]{0, 1, 2});
        when(expenseService.addExpense(expenseDto)).thenReturn(expenseDto);

        ResponseEntity<ExpenseDto> response = expenseController.addExpense(expenseDto, Mockito.mock(BindingResult.class));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expenseDto, response.getBody());
    }
    
    @Test
    public void testAddExpenseWithBindingErrors() {
        // Mocking the ExpenseService
        ExpenseService expenseService = mock(ExpenseService.class);

        // Mocking a binding result with errors
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getAllErrors()).thenReturn(List.of(new FieldError("expenseDto", "amount", "Amount cannot be negative")));

        // Creating the ExpenseController with the mocked ExpenseService
        ExpenseController expenseController = new ExpenseController();

        // Testing the addExpense method with binding errors
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            expenseController.addExpense(new ExpenseDto(), bindingResult);
        });

        // Verifying that the exception contains the expected message
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertTrue(exception.getMessage().contains("Amount cannot be negative"));
    }

    @Test
    @WithMockUser(username = "G3CABGYP28BYXEDF", authorities = {"Manager"})
    public void testGetExpenseById1() throws ExpenseException {
        Long expenseId = 1L;
        String empId = "G3CABGYP28BYXEDF";
        ExpenseDto expenseDto = createSampleExpenseDto();
        when(expenseService.getExpenseById(expenseId)).thenReturn(expenseDto);

        ResponseEntity<ExpenseDto> response = expenseController.getExpenseById(expenseId, empId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expenseDto, response.getBody());
    }

    @Test
    @WithMockUser(username = "G3CABGYP28BYXEDF", authorities = {"Manager"})
    public void testGetExpensesByEmpId() throws ExpenseException {
        String empId = "G3CABGYP28BYXEDF";
        List<ExpenseDto> expectedExpenseList = Collections.singletonList(createSampleExpenseDto());
        when(expenseService.getExpensesByEmpId(empId)).thenReturn(expectedExpenseList);

        ResponseEntity<List<ExpenseDto>> response = expenseController.getExpensesByEmpId(empId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedExpenseList, response.getBody());
    }

    @Test
    @WithMockUser(username = "G3CABGYP28BYXEDF", authorities = {"Manager"})
    public void testUpdateExpense() throws ExpenseException {
        Long expenseId = 1L;
        ExpenseDto expenseDto = createSampleExpenseDto();
        when(expenseService.getExpenseById(expenseId)).thenReturn(expenseDto);
        when(expenseService.updateExpense(expenseDto)).thenReturn(expenseDto);

        ResponseEntity<ExpenseDto> response = expenseController.updateExpense(expenseId, expenseDto, Mockito.mock(BindingResult.class));

        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expenseDto, response.getBody());
    }

    private ExpenseDto createSampleExpenseDto() {
        ExpenseDto expenseDto = new ExpenseDto();
        expenseDto.setAssociateId("G3CABGYP28BYXEDF");
        expenseDto.setStatus("Approved");
        return expenseDto;
    }
    
    @Test
    @WithMockUser(username = "G3CABGYP28BYXEDF", authorities = {"Manager"})
    public void testDeleteExpenseByIdAuthorized() throws Exception {
        when(expenseService.deleteExpenseById(anyLong())).thenReturn(new ExpenseDto());

        mvc.perform(MockMvcRequestBuilders.delete("/expense/report/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));

        verify(expenseService, times(1)).deleteExpenseById(anyLong());
    }
    
    @Test
    public void testDeleteExpenseByIdUnauthorized() throws Exception {
        when(expenseService.deleteExpenseById(anyLong())).thenThrow(new ExpenseException(""));

        mvc.perform(MockMvcRequestBuilders.delete("/expense/report/delete/1"))
                .andExpect(status().isForbidden());

        verify(expenseService, times(0)).deleteExpenseById(anyLong());
    }

    @Test
    @WithMockUser(username = "G3CABGYP28BYXEDF", authorities = {"Manager"})
    public void testGetAmountByCategory() throws Exception {
        when(expenseService.getAmountByCategory(anyLong())).thenReturn(100L);

        mvc.perform(MockMvcRequestBuilders.get("/expense/report/getAmountByCategory/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").value(100));

        verify(expenseService, times(1)).getAmountByCategory(anyLong());
    }

    @Test
    public void testGetAmountByCategoryUnauthorized() throws Exception {
        when(expenseService.getAmountByCategory(anyLong())).thenThrow(new ExpenseException(""));

        mvc.perform(MockMvcRequestBuilders.get("/expense/report/getAmountByCategory/1"))
                .andExpect(status().isForbidden());

        verify(expenseService, times(0)).getAmountByCategory(anyLong());
    }

    @Test
    @WithMockUser(username = "G3CABGYP28BYXEDF", authorities = {"Manager"})
    public void testGetExpenseById() throws Exception {
        when(expenseService.getExpenseById(anyLong())).thenReturn(new ExpenseDto());

        mvc.perform(MockMvcRequestBuilders.get("/expense/report/get/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));

        verify(expenseService, times(1)).getExpenseById(anyLong());
    }
    @Test
    public void testGetExpenseByIdUnauthorized() throws Exception {
        when(expenseService.getExpenseById(anyLong())).thenThrow(new ExpenseException(""));

        mvc.perform(MockMvcRequestBuilders.get("/expense/get/1"))
                .andExpect(status().isForbidden());

        verify(expenseService, times(0)).getExpenseById(anyLong());
    }

    @Test
    @WithMockUser(username = "G3CABGYP28BYXEDF", authorities = {"Manager"})
    public void testGetAllExpenses() throws Exception {
        when(expenseService.getAllExpenses()).thenReturn(List.of(new ExpenseDto()));

        mvc.perform(MockMvcRequestBuilders.get("/expense/report/allexpenses"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.size()").value(1));

        verify(expenseService, times(1)).getAllExpenses();
    }

    @Test
    //@WithMockUser(username = "G3CABGYP28BYXEDF", authorities = {"Manager"})
    public void testGetAllExpensesUnauthorized() throws Exception {
        when(expenseService.getAllExpenses()).thenThrow(new ExpenseException(""));

        mvc.perform(MockMvcRequestBuilders.get("/expense/report/allexpenses"))
                .andExpect(status().isForbidden());

        verify(expenseService, times(0)).getAllExpenses();
    }
    
    @Test
    @WithMockUser(username = "G3CABGYP28BYXEDF", authorities = {"Manager"})
    public void testRejectExpense() throws Exception {
        when(expenseService.rejectExpense(anyLong())).thenReturn(true);

        mvc.perform(MockMvcRequestBuilders.put("/expense/report/reject/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").value(true));

        verify(expenseService, times(1)).rejectExpense(anyLong());
    }
    
    @Test
    public void testRejectExpenseUnauthorized() throws Exception {
        when(expenseService.rejectExpense(anyLong())).thenThrow(new ExpenseException(""));

        mvc.perform(MockMvcRequestBuilders.put("/expense/report/reject/1"))
                .andExpect(status().isForbidden());

        verify(expenseService, times(0)).rejectExpense(anyLong());
    }

    @Test
    @WithMockUser(username = "G3CABGYP28BYXEDF", authorities = {"Manager"})
    public void testAcceptExpense() throws Exception {
        when(expenseService.acceptExpense(anyLong())).thenReturn(true);

        mvc.perform(MockMvcRequestBuilders.put("/expense/report/accept/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").value(true));

        verify(expenseService, times(1)).acceptExpense(anyLong());
    }

    @Test
    public void testAcceptExpenseUnauthorized() throws Exception {
        when(expenseService.acceptExpense(anyLong())).thenThrow(new ExpenseException(""));

        mvc.perform(MockMvcRequestBuilders.put("/expense/report/accept/1"))
                .andExpect(status().isForbidden());

        verify(expenseService, times(0)).acceptExpense(anyLong());
    }

    @Test
    @WithMockUser(username = "G3CABGYP28BYXEDF", authorities = {"Manager"})
    public void testGetExpensesByCategoryId() throws Exception {
        when(expenseService.getExpensesByCategoryId(anyLong())).thenReturn(List.of(new ExpenseDto()));

        mvc.perform(MockMvcRequestBuilders.get("/expense/report/getExpensesbycategory/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.size()").value(1));

        verify(expenseService, times(1)).getExpensesByCategoryId(anyLong());
    }

    @Test
    public void testGetExpensesByCategoryIdUnauthorized() throws Exception {
        when(expenseService.getExpensesByCategoryId(anyLong())).thenThrow(new ExpenseException(""));

        mvc.perform(MockMvcRequestBuilders.get("/expense/report/getExpensesbycategory/1"))
                .andExpect(status().isForbidden());

        verify(expenseService, times(0)).getExpensesByCategoryId(anyLong());
    }

    @Test
    @WithMockUser(username = "G3CABGYP28BYXEDF", authorities = {"Manager"})
    public void testGetExpensesByStatus() throws Exception {
        when(expenseService.getExpensesByStatus(toString())).thenReturn(List.of(new ExpenseDto()));

        mvc.perform(MockMvcRequestBuilders.get("/expense/report/getExpensesByStatus/approved"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.size()").value(0));

        verify(expenseService, times(1)).getExpensesByStatus("approved");
    }

    @Test

    public void testGetExpensesByStatusUnauthorized() throws Exception {
        when(expenseService.getExpensesByStatus(toString())).thenThrow(new ExpenseException(""));

        mvc.perform(MockMvcRequestBuilders.get("/expense/report/getExpensesByStatus/approved"))
                .andExpect(status().isForbidden());

        verify(expenseService, times(0)).getExpensesByStatus(toString());
    }

    @Test
    @WithMockUser(username = "G3CABGYP28BYXEDF", authorities = {"Manager"})

    public void testGetAmountBystatusByEmpIdPresentYear() throws ExpenseException {
        // Mocking the ExpenseService
        ExpenseService expenseService = mock(ExpenseService.class);
        when(expenseService.getAmountBystatusByPresentYear(any(LocalDate.class), any(LocalDate.class), any(String.class), any(String.class)))
                .thenReturn(100L);

        // Creating the ExpenseController with the mocked ExpenseService

        // Testing the getAmountBystatusByEmpIdPresentYear method
        ResponseEntity<Long> responseEntity = expenseController.getAmountBystatusByEmpIdPresentYear("APPROVED", "123");

        // Verifying the response
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(0L, responseEntity.getBody());
    }

    @Test
    @WithMockUser(username = "G3CABGYP28BYXEDF", authorities = {"Manager"})
    public void testGetExpensesByStatusPagenation() throws ExpenseException {
        // Mocking the ExpenseService
        ExpenseService expenseService = mock(ExpenseService.class);
        try {
			when(expenseService.getPagewiseExpensesByStatus(any(Integer.class), any(Integer.class), any(String.class)))
			        .thenReturn(Arrays.asList(new ExpenseDto(), new ExpenseDto()));
		} catch (ExpenseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        // Creating the ExpenseController with the mocked ExpenseService

        // Testing the getExpensesByStatusPagenation method
        ResponseEntity<List<ExpenseDto>> responseEntity = expenseController.getExpensesByStatusPagenation("APPROVED", 1, 2);

        // Verifying the response
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(0, responseEntity.getBody().size());
    }


    
}

