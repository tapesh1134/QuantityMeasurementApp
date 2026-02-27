package org.quantitymeasurement.app;

import java.util.Objects;

public class Quantity<U extends IMeasurable> {

	private final double value;
	private final U unit;
	private static final double EPSILON = 1e-9;

	public Quantity(double value, U unit) {
		if (unit == null) {
			throw new IllegalArgumentException("Unit cannot be null");
		}
		if (!Double.isFinite(value)) {
			throw new IllegalArgumentException("Value must be finite");
		}

		this.value = value;
		this.unit = unit;
	}

	public double getValue() {
		return value;
	}

	public U getUnit() {
		return unit;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Quantity<?> that))
			return false;

		if (this.unit.getClass() != that.unit.getClass()) {
			return false;
		}

		double thisBase = this.unit.convertToBaseUnit(this.value);
		double thatBase = that.unit.convertToBaseUnit(that.value);

		return Math.abs(thisBase - thatBase) < EPSILON;
	}

	public Quantity<U> convertTo(U targetUnit) {
		double baseValue = unit.convertToBaseUnit(value);
		double converted = targetUnit.convertFromBaseUnit(baseValue);

		return new Quantity<>(round(converted), targetUnit);
	}

	public Quantity<U> add(Quantity<U> other) {
		return add(other, this.unit);
	}

	public Quantity<U> add(Quantity<U> other, U targetUnit) {
		double thisBase = unit.convertToBaseUnit(value);
		double otherBase = other.unit.convertToBaseUnit(other.value);
		double sumBase = thisBase + otherBase;
		double result = targetUnit.convertFromBaseUnit(sumBase);
		return new Quantity<>(round(result), targetUnit);
	}

	public Quantity<U> subtract(Quantity<U> other) {
		return subtract(other, this.unit);
	}

	public Quantity<U> subtract(Quantity<U> other, U targetUnit) {
		if (other == null) {
			throw new IllegalArgumentException("Operand cannot be null");
		}
		if (targetUnit == null) {
			throw new IllegalArgumentException("Target unit cannot be null");
		}
		if (this.unit.getClass() != other.unit.getClass()) {
			throw new IllegalArgumentException("Cross-category operation not allowed");
		}
		double thisBase = unit.convertToBaseUnit(value);
		double otherBase = other.unit.convertToBaseUnit(other.value);
		double diffBase = thisBase - otherBase;
		double result = targetUnit.convertFromBaseUnit(diffBase);
		return new Quantity<>(round(result), targetUnit);
	}

	public double divide(Quantity<U> other) {
		if (other == null) {
			throw new IllegalArgumentException("Operand cannot be null");
		}
		if (this.unit.getClass() != other.unit.getClass()) {
			throw new IllegalArgumentException("Cross-category operation not allowed");
		}
		double thisBase = unit.convertToBaseUnit(value);
		double otherBase = other.unit.convertToBaseUnit(other.value);
		if (Math.abs(otherBase) < EPSILON) {
			throw new ArithmeticException("Division by zero");
		}
		return round(thisBase / otherBase);
	}

	private double round(double value) {
		return Math.round(value * 100.0) / 100.0;
	}

	@Override
	public int hashCode() {
		double baseValue = unit.convertToBaseUnit(value);
		long normalized = Math.round(baseValue / EPSILON);
		return Objects.hash(normalized, unit.getClass());
	}

	@Override
	public String toString() {
		return "Quantity(" + value + ", " + unit.getUnitName() + ")";
	}
}