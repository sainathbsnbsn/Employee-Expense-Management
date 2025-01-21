package com.web.exception;



import org.junit.jupiter.api.Test;
//import com.web.exception.ExpenseException;
//import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class ExpenseExceptionTest {
    

    

        @Test
        public void testDefaultConstructor() {
            ExpenseException exception = new ExpenseException();
            assertEquals(null, exception.getMessage());
        }

        @Test
        public void testMessageConstructor() {
            String errorMessage = "Test error message";
            ExpenseException exception = new ExpenseException(errorMessage);
            assertEquals(errorMessage, exception.getMessage());
        }

        @Test
        public void testMessageAndThrowableConstructor() {
            String errorMessage = "Test error message";
            Throwable cause = new RuntimeException("Test cause exception");
            ExpenseException exception = new ExpenseException(errorMessage, cause);

            assertEquals(errorMessage, exception.getMessage());
            assertEquals(cause, exception.getCause());
        }
    }
