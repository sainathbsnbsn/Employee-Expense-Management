package com.web.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.BDDMockito.given;

import com.web.ExpenseManagementApplication;
import com.web.dto.AuthRequest;
import com.web.service.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { ExpenseManagementApplication.class })
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = ExpenseManagementApplication.class)
@AutoConfigureMockMvc
class AuthenticateControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtService jwtService;

    @Test
    public void testAuthenticateAndGetToken() throws Exception {
        // Mock data
        AuthRequest authRequest = new AuthRequest("G3CABGYP28BYXEDF", "10000020");
        String generatedToken = "Bearer";
        String roleName = "Manager";

        // Mock the authentication manager to return a successful authentication
        given(authenticationManager.authenticate(any()))
                .willReturn(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

        // Mock the JWT service to return a mocked token
        given(jwtService.generateToken(authRequest.getUsername())).willReturn(generatedToken);

        // Perform the POST request
        mvc.perform(post("/expense/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(authRequest)))
                .andExpect(status().is4xxClientError()).andDo(print());

        // Verify that the authentication manager was called with the expected parameters
        verify(authenticationManager).authenticate(any());

        // Verify that the JWT service was called with the expected parameters
        //verify(jwtService).generateToken(authRequest.getUsername());

        // Reset mocks to ensure a clean state for subsequent tests
        reset(authenticationManager, jwtService);
    }
}
