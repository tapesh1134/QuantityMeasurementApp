package org.quantitymeasurement.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.quantitymeasurement.app.model.Feet;
import org.quantitymeasurement.app.model.Inches;

public class QuantityMeasurementAppTest {
	@Test
	void testEquality_SameValue() {
		Feet f1 = new Feet(1.0);
		Feet f2 = new Feet(1.0);
		assertEquals(f1, f2);

		Inches i1 = new Inches(1.0);
		Inches i2 = new Inches(1.0);
		assertEquals(i1, i2);
	}

	@Test
	void testEquality_DifferentValue() {
		Feet f1 = new Feet(1.0);
		Feet f2 = new Feet(2.0);
		assertNotEquals(f1, f2);

		Inches i1 = new Inches(1.0);
		Inches i2 = new Inches(2.0);
		assertNotEquals(i1, i2);
	}

	@Test
	void testEquality_NullComparison() {
		Feet f1 = new Feet(1.0);
		assertNotEquals(null, f1);
	}

	@Test
	void testEquality_NonNumericInput() {
		assertThrows(IllegalArgumentException.class, () -> new Feet(null));
		assertThrows(IllegalArgumentException.class, () -> new Inches(null));
	}

	@Test
	void testEquality_SameReference() {
		Feet f1 = new Feet(1.0);
		assertEquals(f1, f1);

		Inches i1 = new Inches(1.0);
		assertEquals(i1, i1);
	}
}