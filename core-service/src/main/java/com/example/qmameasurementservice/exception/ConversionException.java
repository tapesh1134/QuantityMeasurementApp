
package com.example.qmameasurementservice.exception;

public class ConversionException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ConversionException(String message) {
		super(message);
	}

	public ConversionException(String message, Throwable t) {
		super(message, t);
	}
}
