package org.quantitymeasurement.app;

public enum WeightUnit implements IMeasurable {
	KILOGRAM(1.0), GRAM(0.001);

	private final double conversionFactor;

	WeightUnit(double conversionFactor) {
		this.conversionFactor = conversionFactor;
	}

	@Override
	public double getConversionFactor() {
		return conversionFactor;
	}

	@Override
	public String getUnitName() {
		return name();
	}
}