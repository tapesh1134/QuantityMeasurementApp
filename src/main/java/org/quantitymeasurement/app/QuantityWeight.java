package org.quantitymeasurement.app;

public final class QuantityWeight {

	private static final double EPSILON = 1e-6;

	private final double value;
	private final WeightUnit unit;

	public QuantityWeight(double value, WeightUnit unit) {
		if (unit == null)
			throw new IllegalArgumentException("Unit cannot be null");
		if (Double.isNaN(value) || Double.isInfinite(value))
			throw new IllegalArgumentException("Invalid numeric value");
		this.value = value;
		this.unit = unit;
	}

	public double getValue() {
		return value;
	}

	public WeightUnit getUnit() {
		return unit;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		QuantityWeight other = (QuantityWeight) obj;
		double thisInKg = unit.convertToBaseUnit(this.value);
		double otherInKg = other.unit.convertToBaseUnit(other.value);
		return Math.abs(thisInKg - otherInKg) < EPSILON;
	}

	public QuantityWeight convertTo(WeightUnit targetUnit) {
		if (targetUnit == null)
			throw new IllegalArgumentException("Target unit cannot be null");
		double baseValue = unit.convertToBaseUnit(value);
		double converted = targetUnit.convertFromBaseUnit(baseValue);
		return new QuantityWeight(converted, targetUnit);
	}

	public QuantityWeight add(QuantityWeight other) {
		return add(other, this.unit);
	}

	public QuantityWeight add(QuantityWeight other, WeightUnit targetUnit) {
		if (other == null)
			throw new IllegalArgumentException("Other weight cannot be null");
		if (targetUnit == null)
			throw new IllegalArgumentException("Target unit cannot be null");
		double thisInKg = unit.convertToBaseUnit(this.value);
		double otherInKg = other.unit.convertToBaseUnit(other.value);
		double sumInKg = thisInKg + otherInKg;
		double finalValue = targetUnit.convertFromBaseUnit(sumInKg);
		return new QuantityWeight(finalValue, targetUnit);
	}

	@Override
	public String toString() {
		return String.format("%.6f, %s", value, unit);
	}
}