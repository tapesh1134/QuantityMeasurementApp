package org.quantitymeasurement.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class QuantityMeasurementAppTest {
	@Test
	void testEquality_FeetToFeet_SameValue() {
		QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
		QuantityLength q2 = new QuantityLength(1.0, LengthUnit.FEET);
		assertEquals(q1, q2);
	}

	@Test
	void testEquality_InchToInch_SameValue() {
		QuantityLength q1 = new QuantityLength(1.0, LengthUnit.INCH);
		QuantityLength q2 = new QuantityLength(1.0, LengthUnit.INCH);
		assertEquals(q1, q2);
	}

	@Test
	void testEquality_FeetToInch_EquivalentValue() {
		QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
		QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCH);
		assertEquals(q1, q2);
	}

	@Test
	void testEquality_DifferentValues() {
		QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
		QuantityLength q2 = new QuantityLength(2.0, LengthUnit.FEET);
		assertNotEquals(q1, q2);
	}

	@Test
	void testEquality_SameReference() {
		QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
		assertEquals(q1, q1);
	}

	@Test
	void testEquality_NullComparison() {
		QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
		assertNotEquals(q1, null);
	}

	@Test
	void testInvalidUnit() {
		assertThrows(IllegalArgumentException.class, () -> new QuantityLength(1.0, null));
	}

	@Test
	void testEquality_YardToFeet_EquivalentValue() {
		QuantityLength q1 = new QuantityLength(1.0, LengthUnit.YARD);
		QuantityLength q2 = new QuantityLength(3.0, LengthUnit.FEET);
		assertEquals(q1, q2);
	}

	@Test
	void testEquality_YardToInches_EquivalentValue() {
		QuantityLength q1 = new QuantityLength(1.0, LengthUnit.YARD);
		QuantityLength q2 = new QuantityLength(36.0, LengthUnit.INCH);
		assertEquals(q1, q2);
	}

	@Test
	void testEquality_CentimeterToInch_EquivalentValue() {
		QuantityLength q1 = new QuantityLength(1.0, LengthUnit.CENTIMETER);
		QuantityLength q2 = new QuantityLength(0.393701, LengthUnit.INCH);
		assertEquals(q1, q2);
	}

	@Test
	void testEquality_MultiUnit_TransitiveProperty() {
		QuantityLength yard = new QuantityLength(1.0, LengthUnit.YARD);
		QuantityLength feet = new QuantityLength(3.0, LengthUnit.FEET);
		QuantityLength inch = new QuantityLength(36.0, LengthUnit.INCH);

		assertTrue(yard.equals(feet));
		assertTrue(feet.equals(inch));
		assertTrue(yard.equals(inch));
	}

	@Test
	void testConversion_FeetToInches() {
		assertEquals(QuantityLength.convert(1.0, LengthUnit.FEET, LengthUnit.INCH), 12.0);
	}

	@Test
	void testConversion_InchesToFeet() {
		assertEquals(QuantityLength.convert(24.0, LengthUnit.INCH, LengthUnit.FEET), 2.0);
	}

	@Test
	void testConversion_YardsToInches() {
		assertEquals(QuantityLength.convert(1.0, LengthUnit.YARD, LengthUnit.INCH), 36.0);
	}

	@Test
	void testConversion_InchesToYards() {
		assertEquals(QuantityLength.convert(72.0, LengthUnit.INCH, LengthUnit.YARD), 2.0);
	}

	@Test
	void testConversion_FeatToYard() {
		assertEquals(QuantityLength.convert(6.0, LengthUnit.FEET, LengthUnit.YARD), 2.0);
	}

	@Test
	void testConversion_RoundTrip_PreservesValue() {
		assertEquals(QuantityLength.convert(QuantityLength.convert(1.0, LengthUnit.FEET, LengthUnit.INCH),
				LengthUnit.INCH, LengthUnit.FEET), 1.0);
	}

	@Test
	void testConversion_ZeroValue() {
		assertEquals(QuantityLength.convert(0.0, LengthUnit.FEET, LengthUnit.INCH), 0.0);
	}

	@Test
	void testConversion_NegativeValue() {
		assertEquals(QuantityLength.convert(-1.0, LengthUnit.FEET, LengthUnit.INCH), -12.0);
	}

	@Test
	void testConversion_InvalidUnit_Throws() {
		assertThrows(IllegalArgumentException.class, () -> QuantityLength.convert(1.0, null, LengthUnit.INCH));
	}

	@Test
	void testConversion_NaNOrInfinite_Throws() {
		assertThrows(IllegalArgumentException.class,
				() -> QuantityLength.convert(Double.NaN, LengthUnit.FEET, LengthUnit.INCH));

		assertThrows(IllegalArgumentException.class,
				() -> QuantityLength.convert(Double.POSITIVE_INFINITY, LengthUnit.FEET, LengthUnit.INCH));

		assertThrows(IllegalArgumentException.class,
				() -> QuantityLength.convert(Double.NEGATIVE_INFINITY, LengthUnit.FEET, LengthUnit.INCH));
	}

	@Test
	void testConversion_PrecisionTolerance() {
		double result = QuantityLength.convert(1.0, LengthUnit.FEET, LengthUnit.INCH);
		double expected = 12.0;
		double epsilon = 1e-6;
		assertEquals(expected, result, epsilon);
	}
}
