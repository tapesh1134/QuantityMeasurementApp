package org.quantitymeasurement.app;

import java.util.Objects;
import java.util.function.DoubleBinaryOperator;

public class Quantity<U extends IMeasurable> {
	private enum ArithmeticOperations{
		ADD((a,b) -> a + b),
		SUBTRACT((a,b) -> a-b),
		DIVIDE((a,b) -> { 
			if(Math.abs(b) < EPSILON) throw new ArithmeticException("Divide by zero");
			return a/b;
		});
		
		private final DoubleBinaryOperator operation;
		
		ArithmeticOperations(DoubleBinaryOperator operation) {
			this.operation = operation;
		}
		
		public double compute(double a, double b) {
			return operation.applyAsDouble(a, b);
		}
	}
	
	private final double value;
	private final U unit;
	private static final double EPSILON = 1e-9;
	
	private Quantity<U> applyOperation(Quantity<U> other, U targetUnit, ArithmeticOperations operation) {
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

	    double resultBase = operation.compute(thisBase, otherBase);
	    double result = targetUnit.convertFromBaseUnit(resultBase);

	    return new Quantity<>(round(result), targetUnit);
	}

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
	    return applyOperation(other, targetUnit, ArithmeticOperations.ADD);
	}

	public Quantity<U> subtract(Quantity<U> other) {
	    return subtract(other, this.unit);
	}

	public Quantity<U> subtract(Quantity<U> other, U targetUnit) {
	    return applyOperation(other, targetUnit, ArithmeticOperations.SUBTRACT);
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

	    return round(ArithmeticOperations.DIVIDE.compute(thisBase, otherBase));
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