package com.web.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;

import io.jsonwebtoken.Claims;

class JwtUtilTest {
     
     @Autowired
     private JwtUtil jwtUtil;
    
    
     private String testToken;
    
       @BeforeEach
        public void setUp() {
            jwtUtil = new JwtUtil();
            testToken = jwtUtil.generateToken("testUser");
        }

        @Test
        public void testExtractUsername() {
            String username = jwtUtil.extractUsername(testToken);
            assertEquals("testUser", username);
        }

        @Test
        public void testExtractExpiration() {
            Date expiration = (Date) jwtUtil.extractExpiration(testToken);
            assertNotNull(expiration);
        }

        @Test
        public void testExtractClaim() {
            String claim = jwtUtil.extractClaim(testToken, Claims::getSubject);
            assertEquals("testUser", claim);
        }



        @Test
        public void testGenerateToken() {
            String token = jwtUtil.generateToken("newUser");
            assertNotNull(token);
        }

        @Test
        public void testValidateToken() {
            User userDetails = new User("testUser", "password",new ArrayList<>());
            assertTrue(jwtUtil.validateToken(testToken, userDetails));
        }



}
