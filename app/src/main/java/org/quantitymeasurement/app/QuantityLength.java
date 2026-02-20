package org.quantitymeasurement.app;

public class QuantityLength {

	private final double value;
	private final LengthUnit unit;
	private final double EPSILON = 1e-6;

	public QuantityLength(double value, LengthUnit unit) {
		if (unit == null) {
			throw new IllegalArgumentException("Unit cannot be null");
		}
		this.value = value;
		this.unit = unit;
	}

	private double convertToBase() {
		return unit.toFeet(value);
	}

	public static double convert(double value, LengthUnit fromLengthUnit, LengthUnit toLengthUnit) {
		if (fromLengthUnit == null || toLengthUnit == null) {
			throw new IllegalArgumentException("Unit connet be null");
		}

		if (Double.isNaN(value) || Double.isInfinite(value)) {
			throw new IllegalArgumentException("Value must be finite number");
		}

		return value * (fromLengthUnit.getConversionFactor() / toLengthUnit.getConversionFactor());
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QuantityLength other = (QuantityLength) obj;

		return Math.abs(this.convertToBase() - other.convertToBase()) < EPSILON;
	}

	@Override
	public String toString() {
		return "Quantity(" + value + ", " + unit + ")";
	}
}
