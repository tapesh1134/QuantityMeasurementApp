package org.quantitymeasurement.app.controller;

import org.quantitymeasurement.app.service.QuantityMeasurementService;

public class QuantityMeasurementController {

	public static boolean validateFeet(Double value1, Double value2) {
		return QuantityMeasurementService.compareFeet(value1, value2);
	}

	public static boolean validateInches(Double value1, Double value2) {
		return QuantityMeasurementService.compareInches(value1, value2);
	}
}
