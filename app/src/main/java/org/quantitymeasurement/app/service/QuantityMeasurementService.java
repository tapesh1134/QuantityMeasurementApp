package org.quantitymeasurement.app.service;

import org.quantitymeasurement.app.model.Feet;
import org.quantitymeasurement.app.model.Inches;

public class QuantityMeasurementService {
	public static boolean compareFeet(Double value1, Double value2) {
		Feet f1 = new Feet(value1);
		Feet f2 = new Feet(value2);
		return f1.equals(f2);
	}

	public static boolean compareInches(Double value1, Double value2) {
		Inches i1 = new Inches(value1);
		Inches i2 = new Inches(value2);
		return i1.equals(i2);
	}
}
