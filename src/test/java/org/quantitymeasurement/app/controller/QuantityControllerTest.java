package org.quantitymeasurement.app.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.quantitymeasurement.app.dto.QuantityRequestDto;
import org.quantitymeasurement.app.repository.QuantityMeasurementCacheRepository;
import org.quantitymeasurement.app.service.QuantityServiceImpl;

class QuantityControllerTest {

	QuantityController controller = new QuantityController(
			new QuantityServiceImpl(QuantityMeasurementCacheRepository.getInstance()));

	@Test
	void testControllerEquality_Length() {
		String result = controller.checkEquality(new QuantityRequestDto(1.0, "FEET"),
				new QuantityRequestDto(12.0, "INCH"));
		assertTrue(result.contains("true"));
	}

	@Test
	void testControllerEquality_Weight() {
		String result = controller.checkEquality(new QuantityRequestDto(1.0, "KILOGRAM"),
				new QuantityRequestDto(1000.0, "GRAM"));
		assertTrue(result.contains("true"));
	}

	@Test
	void testControllerEquality_Volume() {
		String result = controller.checkEquality(new QuantityRequestDto(1.0, "LITRE"),
				new QuantityRequestDto(1000.0, "MILLILITRE"));
		assertTrue(result.contains("true"));
	}

	@Test
	void testControllerEquality_Temperature() {
		String result = controller.checkEquality(new QuantityRequestDto(0.0, "CELSIUS"),
				new QuantityRequestDto(32.0, "FAHRENHEIT"));
		assertTrue(result.contains("true"));
	}

	/*
	 * ========================================================= CONVERSION TESTS
	 * =========================================================
	 */

	@Test
	void testControllerConversion_Length() {
		var result = controller.convert(new QuantityRequestDto(1.0, "FEET"), "INCH");
		assertEquals(12.0, result.getNumeric());
	}

	@Test
	void testControllerConversion_Weight() {
		var result = controller.convert(new QuantityRequestDto(1.0, "KILOGRAM"), "GRAM");
		assertEquals(1000.0, result.getNumeric());
	}

	@Test
	void testControllerConversion_Volume() {
		var result = controller.convert(new QuantityRequestDto(1.0, "LITRE"), "MILLILITRE");
		assertEquals(1000.0, result.getNumeric());
	}

	@Test
	void testControllerConversion_Temperature() {
		var result = controller.convert(new QuantityRequestDto(100.0, "CELSIUS"), "FAHRENHEIT");
		assertEquals(212.0, result.getNumeric());
	}

	/*
	 * ========================================================= ADDITION TESTS
	 * =========================================================
	 */

	@Test
	void testControllerAddition_Length() {
		var result = controller.add(new QuantityRequestDto(1.0, "FEET"), new QuantityRequestDto(12.0, "INCH"));
		assertEquals(2.0, result.getNumeric());
	}

	@Test
	void testControllerAddition_Weight() {
		var result = controller.add(new QuantityRequestDto(1.0, "KILOGRAM"), new QuantityRequestDto(1000.0, "GRAM"));
		assertEquals(2.0, result.getNumeric());
	}

	@Test
	void testControllerAddition_Volume() {
		var result = controller.add(new QuantityRequestDto(1.0, "LITRE"), new QuantityRequestDto(1000.0, "MILLILITRE"));
		assertEquals(2.0, result.getNumeric());
	}

	@Test
	void testControllerAddition_WithTargetUnit() {
		var result = controller.add(new QuantityRequestDto(1.0, "FEET"), new QuantityRequestDto(12.0, "INCH"), "INCH");
		assertEquals(24.0, result.getNumeric());
	}

	/*
	 * ========================================================= SUBTRACTION TESTS
	 * =========================================================
	 */

	@Test
	void testControllerSubtraction_Length() {
		var result = controller.subtract(new QuantityRequestDto(10.0, "FEET"), new QuantityRequestDto(6.0, "INCH"));
		assertEquals(9.5, result.getNumeric());
	}

	@Test
	void testControllerSubtraction_WithTarget() {
		var result = controller.subtract(new QuantityRequestDto(10.0, "FEET"), new QuantityRequestDto(6.0, "INCH"),
				"INCH");
		assertEquals(114.0, result.getNumeric());
	}

	/*
	 * ========================================================= DIVISION TESTS
	 * =========================================================
	 */

	@Test
	void testControllerDivision_Length() {
		double result = controller.divide(new QuantityRequestDto(24.0, "INCH"), new QuantityRequestDto(2.0, "FEET"));
		assertEquals(1.0, result);
	}

	@Test
	void testControllerDivision_Weight() {
		double result = controller.divide(new QuantityRequestDto(2000.0, "GRAM"),
				new QuantityRequestDto(1.0, "KILOGRAM"));
		assertEquals(2.0, result);
	}

	/*
	 * ========================================================= TEMPERATURE EDGE
	 * CASES =========================================================
	 */

	@Test
	void testTemperatureAdditionBlocked() {
		assertThrows(UnsupportedOperationException.class, () -> controller.add(new QuantityRequestDto(100.0, "CELSIUS"),
				new QuantityRequestDto(50.0, "CELSIUS")));
	}

	/*
	 * ========================================================= INVALID INPUT TESTS
	 * =========================================================
	 */

	@Test
	void testInvalidUnit() {
		assertThrows(IllegalArgumentException.class,
				() -> controller.convert(new QuantityRequestDto(1.0, "INVALID"), "INCH"));
	}

	@Test
	void testNullUnit() {
		assertThrows(IllegalArgumentException.class,
				() -> controller.convert(new QuantityRequestDto(1.0, null), "INCH"));
	}

	/*
	 * ========================================================= CROSS CATEGORY
	 * SAFETY =========================================================
	 */

	@Test
	void testCrossCategoryComparison() {
		String result = controller.checkEquality(new QuantityRequestDto(1.0, "FEET"),
				new QuantityRequestDto(1.0, "KILOGRAM"));
		assertTrue(result.contains("false"));
	}
}