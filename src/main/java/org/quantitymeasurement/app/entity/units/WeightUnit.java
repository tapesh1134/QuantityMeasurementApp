
package org.quantitymeasurement.app.entity.units;

import org.quantitymeasurement.app.entity.IMeasurable;

public enum WeightUnit implements IMeasurable {

	GRAM(1), KILOGRAM(1000), MILLIGRAM(0.001), POUND(453.592);

	private final double factor;

	WeightUnit(double factor) {
		this.factor = factor;
	}

	@Override
	public double getConversionFactor() {
		return factor;
	}

	@Override
	public String getUnitName() {
		return name();
	}
}
