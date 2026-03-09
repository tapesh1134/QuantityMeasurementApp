package org.quantitymeasurement.app;

@FunctionalInterface
interface SupportArithmetic {
	boolean isSupported();
}

public interface IMeasurable {
	SupportArithmetic supportArithmetic = () -> true;

	double getConversionFactor();

	default double convertToBaseUnit(double value) {
		return value * getConversionFactor();
	}

	default double convertFromBaseUnit(double baseValue) {
		return baseValue / getConversionFactor();
	}

	String getUnitName();

	default boolean SupportsArithmetic() {
		return supportArithmetic.isSupported();
	}

	default void validateOperationSupport(String operation) {
	}
}
