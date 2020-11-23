package com.crash.resources.exceptions;

public class FieldMessage {

	private String field;
	private String MessageError;

	public FieldMessage(String field, String messageError) {
		this.field = field;
		this.MessageError = messageError;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getMessageError() {
		return MessageError;
	}

	public void setMessageError(String messageError) {
		MessageError = messageError;
	}

}
