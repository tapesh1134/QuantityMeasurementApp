
package org.quantitymeasurement.app.entity.units;

import org.quantitymeasurement.app.entity.IMeasurable;

public enum TemperatureUnit implements IMeasurable {

	CELSIUS, FAHRENHEIT;

	@Override
	public double getConversionFactor() {
		return 1.0;
	}

	@Override
	public String getUnitName() {
		return name();
	}

	public double convertToBase(double value) {
		if (this == FAHRENHEIT) {
			return (value - 32) * 5.0 / 9.0;
		}
		return value;
	}

	public double convertFromBase(double baseValue) {
		if (this == FAHRENHEIT) {
			return (baseValue * 9.0 / 5.0) + 32.0;
		}
		return baseValue;
	}

	public double convertTo(double value, TemperatureUnit target) {
		double base = this.convertToBase(value);
		return target.convertFromBase(base);
	}

	@Override
	public void validateOperationSupport(String operation) {
		throw new UnsupportedOperationException("Temperature units do not support arithmetic operations: " + operation);
	}

	@Override
	public boolean supportsArithmetic() {
		return false;
	}
}
