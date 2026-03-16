
package org.quantitymeasurement.app.dto;

public class QuantityResponseDto {

	private final String display;
	private final double numeric;

	public QuantityResponseDto(String display, double numeric) {
		this.display = display;
		this.numeric = numeric;
	}

	@Override
	public String toString() {
		return display;
	}

	public String getDisplay() {
		return display;
	}

	public double getNumeric() {
		return numeric;
	}
}
