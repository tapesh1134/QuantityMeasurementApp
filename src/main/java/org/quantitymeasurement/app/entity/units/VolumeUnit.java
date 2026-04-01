
package org.quantitymeasurement.app.entity.units;

import org.quantitymeasurement.app.entity.IMeasurable;

public enum VolumeUnit implements IMeasurable {

	LITRE(1), MILLILITRE(0.001), GALLON(3.78541);

	private final double factor;

	VolumeUnit(double factor) {
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
