package com.web.exception;

public class ExpenseException extends Exception {
	public ExpenseException() {

	}
	public ExpenseException(String message) {
		super(message);
	}
	public ExpenseException(String message,Throwable t) {
		super(message,t);
	}
}
