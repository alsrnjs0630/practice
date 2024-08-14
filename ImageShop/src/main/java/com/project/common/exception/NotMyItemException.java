package com.project.common.exception;

public class NotMyItemException extends Exception{
	
	private static final long serialVersionID = 1L;
	
	public NotMyItemException(String msg) {
		super(msg);
	}
}
