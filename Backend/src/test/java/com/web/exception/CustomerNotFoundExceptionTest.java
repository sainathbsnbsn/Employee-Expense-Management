package com.web.exception;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CustomerNotFoundExceptionTest {

	 @Test
     void testMessageConstructor() {
         String errorMessage = "Test error message";
         EmployeeException exception = new EmployeeException(errorMessage);
         assertEquals(errorMessage, exception.getMessage());
     }

}
