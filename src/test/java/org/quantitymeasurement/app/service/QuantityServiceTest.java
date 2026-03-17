package org.quantitymeasurement.app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.quantitymeasurement.app.entity.Quantity;
import org.quantitymeasurement.app.entity.units.LengthUnit;
import org.quantitymeasurement.app.entity.units.TemperatureUnit;
import org.quantitymeasurement.app.entity.units.VolumeUnit;
import org.quantitymeasurement.app.entity.units.WeightUnit;
import org.quantitymeasurement.app.repository.QuantityMeasurementCacheRepository;

class QuantityServiceTest {

	QuantityService service = new QuantityServiceImpl(QuantityMeasurementCacheRepository.getInstance());

	@Test
	void testServiceAdd_Length() {
		Quantity<LengthUnit> q1 = new Quantity<>(1.0, LengthUnit.FEET);
		Quantity<LengthUnit> q2 = new Quantity<>(12.0, LengthUnit.INCH);

		Quantity<LengthUnit> result = service.add(q1, q2);

		assertEquals(2.0, result.getValue());
	}

	@Test
	void testServiceAdd_Weight() {
		Quantity<WeightUnit> q1 = new Quantity<>(1.0, WeightUnit.KILOGRAM);
		Quantity<WeightUnit> q2 = new Quantity<>(1000.0, WeightUnit.GRAM);

		Quantity<WeightUnit> result = service.add(q1, q2);

		assertEquals(2.0, result.getValue());
	}

	@Test
	void testServiceAdd_Volume() {
		Quantity<VolumeUnit> q1 = new Quantity<>(1.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> q2 = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);

		Quantity<VolumeUnit> result = service.add(q1, q2);

		assertEquals(2.0, result.getValue());
	}

	@Test
	void testServiceAdd_WithTargetUnit() {
		Quantity<LengthUnit> q1 = new Quantity<>(1.0, LengthUnit.FEET);
		Quantity<LengthUnit> q2 = new Quantity<>(12.0, LengthUnit.INCH);

		Quantity<LengthUnit> result = service.add(q1, q2, LengthUnit.INCH);

		assertEquals(24.0, result.getValue());
	}

	/*
	 * ========================================================= SUBTRACTION TESTS
	 * =========================================================
	 */

	@Test
	void testServiceSubtract_Length() {
		Quantity<LengthUnit> result = service.subtract(new Quantity<>(10.0, LengthUnit.FEET),
				new Quantity<>(6.0, LengthUnit.INCH));

		assertEquals(9.5, result.getValue());
	}

	@Test
	void testServiceSubtract_WithTargetUnit() {
		Quantity<LengthUnit> result = service.subtract(new Quantity<>(10.0, LengthUnit.FEET),
				new Quantity<>(6.0, LengthUnit.INCH), LengthUnit.INCH);

		assertEquals(114.0, result.getValue());
	}

	/*
	 * ========================================================= CONVERSION TESTS
	 * =========================================================
	 */

	@Test
	void testServiceConvert_Length() {
		Quantity<LengthUnit> q = new Quantity<>(1.0, LengthUnit.FEET);

		Quantity<LengthUnit> result = service.convert(q, LengthUnit.INCH);

		assertEquals(12.0, result.getValue());
	}

	@Test
	void testServiceConvert_Weight() {
		Quantity<WeightUnit> q = new Quantity<>(1.0, WeightUnit.KILOGRAM);

		Quantity<WeightUnit> result = service.convert(q, WeightUnit.GRAM);

		assertEquals(1000.0, result.getValue());
	}

	@Test
	void testServiceConvert_Volume() {
		Quantity<VolumeUnit> q = new Quantity<>(1.0, VolumeUnit.LITRE);

		Quantity<VolumeUnit> result = service.convert(q, VolumeUnit.MILLILITRE);

		assertEquals(1000.0, result.getValue());
	}

	@Test
	void testServiceConvert_Temperature() {
		Quantity<TemperatureUnit> q = new Quantity<>(100.0, TemperatureUnit.CELSIUS);

		Quantity<TemperatureUnit> result = service.convert(q, TemperatureUnit.FAHRENHEIT);

		assertEquals(212.0, result.getValue());
	}

	/*
	 * ========================================================= DIVISION TESTS
	 * =========================================================
	 */

	@Test
	void testServiceDivide_Length() {
		double result = service.divide(new Quantity<>(24.0, LengthUnit.INCH), new Quantity<>(2.0, LengthUnit.FEET));

		assertEquals(1.0, result);
	}

	@Test
	void testServiceDivide_Weight() {
		double result = service.divide(new Quantity<>(2000.0, WeightUnit.GRAM),
				new Quantity<>(1.0, WeightUnit.KILOGRAM));

		assertEquals(2.0, result);
	}

	/*
	 * ========================================================= TEMPERATURE RULE
	 * TEST =========================================================
	 */

	@Test
	void testServiceTemperatureUnsupportedOperation() {
		assertThrows(UnsupportedOperationException.class, () -> service
				.add(new Quantity<>(100.0, TemperatureUnit.CELSIUS), new Quantity<>(50.0, TemperatureUnit.CELSIUS)));
	}

	/*
	 * ========================================================= VALIDATION TESTS
	 * =========================================================
	 */

	@Test
	void testServiceDivideByZero() {
		assertThrows(ArithmeticException.class,
				() -> service.divide(new Quantity<>(10.0, LengthUnit.FEET), new Quantity<>(0.0, LengthUnit.FEET)));
	}

	@Test
	void testServiceNullOperand() {
		assertThrows(IllegalArgumentException.class, () -> service.add(new Quantity<>(1.0, LengthUnit.FEET), null));
	}
}