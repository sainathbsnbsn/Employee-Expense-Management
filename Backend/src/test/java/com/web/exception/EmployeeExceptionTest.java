package com.web.exception;




//import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.Test;

class EmployeeExceptionTest {

      @Test
        void testDefaultConstructor() {
          EmployeeException exception = new EmployeeException();
            assertEquals(null, exception.getMessage());
        }

        @Test
        void testMessageConstructor() {
            String errorMessage = "Test error message";
            EmployeeException exception = new EmployeeException(errorMessage);
            assertEquals(errorMessage, exception.getMessage());
        }

        @Test
         void testMessageAndThrowableConstructor() {
            String errorMessage = "Test error message";
            Throwable cause = new RuntimeException("Test cause exception");
            EmployeeException exception = new EmployeeException(errorMessage, cause);

            assertEquals(errorMessage, exception.getMessage());
            assertEquals(cause, exception.getCause());
        }

}
