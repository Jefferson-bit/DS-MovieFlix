package com.crash.resources.exceptions;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ValidatorError extends StandardError{
	private static final long serialVersionUID = 1L;
	private List<FieldMessage> err = new ArrayList<>();
	
	public ValidatorError() {
		super();
	}
	
	public ValidatorError(Instant timeStamp, Integer status, String error, String message, String path) {
		super(timeStamp, status, error, message, path);
	}

	public List<FieldMessage> getErr() {
		return err;
	}
	
	public void getError(String message, String defaulMessage) {
		err.add(new FieldMessage(message, defaulMessage));
	}
}
