package org.quantitymeasurement.app;

import org.quantitymeasurement.app.controller.QuantityMeasurementController;

public class QuantityMeasurementApp {
	public static void main(String[] args) {

		Double feet1 = 1.0;
		Double feet2 = 1.0;

		Double inch1 = 1.0;
		Double inch2 = 1.0;

		boolean feetResult = QuantityMeasurementController.validateFeet(feet1, feet2);
		boolean inchResult = QuantityMeasurementController.validateInches(inch1, inch2);

		System.out.println("Input: 1.0 ft and 1.0 ft");
		System.out.println("Output: Equal (" + feetResult + ")");

		System.out.println("Input: 1.0 inch and 1.0 inch");
		System.out.println("Output: Equal (" + inchResult + ")");
	}
}
