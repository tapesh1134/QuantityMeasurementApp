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
}