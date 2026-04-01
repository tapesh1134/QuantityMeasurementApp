
package org.quantitymeasurement.app.entity.units;

import org.quantitymeasurement.app.entity.IMeasurable;

public enum LengthUnit implements IMeasurable {

	FEET(12), INCH(1), YARD(36), CENTIMETER(0.393701);

	private final double factor;

	LengthUnit(double factor) {
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
