package org.quantitymeasurement.app;

public enum LengthUnit {
	FEET(1.0), INCH(1.0 / 12.0), YARD(3.0), CENTIMETER(0.0328084167);

	private final double conversionFactorToFeet;

	LengthUnit(double conversionFactorToFeet) {
		this.conversionFactorToFeet = conversionFactorToFeet;
	}

	public double toFeet(double value) {
		return value * conversionFactorToFeet;
	}
}
