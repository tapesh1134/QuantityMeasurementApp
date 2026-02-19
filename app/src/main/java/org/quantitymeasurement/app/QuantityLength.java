package org.quantitymeasurement.app;

public class QuantityLength {

	private final double value;
	private final LengthUnit unit;

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

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QuantityLength other = (QuantityLength) obj;

		return Double.compare(this.convertToBase(), other.convertToBase()) == 0;
	}

	@Override
	public String toString() {
		return "Quantity(" + value + ", " + unit + ")";
	}
}
