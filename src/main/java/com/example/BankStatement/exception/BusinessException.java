package com.example.BankStatement.exception;

public class BusinessException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4818778019041560327L;

	public BusinessException(String errorMessage) {
		super(errorMessage);
	}
}
