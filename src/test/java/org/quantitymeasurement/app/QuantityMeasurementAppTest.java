package org.quantitymeasurement.app;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class QuantityMeasurementAppTest {
	@Test
	void testIMeasurableInterface_LengthUnitImplementation() {
		IMeasurable unit = LengthUnit.FEET;
		assertEquals(12.0, unit.getConversionFactor());
		assertEquals("FEET", unit.getUnitName());
		assertEquals(12.0, unit.convertToBaseUnit(1.0));
	}

	@Test
	void testIMeasurableInterface_WeightUnitImplementation() {
		IMeasurable unit = WeightUnit.KILOGRAM;
		assertEquals(1000.0, unit.getConversionFactor());
		assertEquals("KILOGRAM", unit.getUnitName());
		assertEquals(1000.0, unit.convertToBaseUnit(1.0));
	}

	@Test
	void testIMeasurableInterface_ConsistentBehavior() {
		IMeasurable length = LengthUnit.INCHES;
		IMeasurable weight = WeightUnit.GRAM;
		assertEquals(1.0, length.getConversionFactor());
		assertEquals(1.0, weight.getConversionFactor());
	}

	@Test
	void testGenericQuantity_LengthOperations_Equality() {
		Quantity<LengthUnit> q1 = new Quantity<>(1.0, LengthUnit.FEET);
		Quantity<LengthUnit> q2 = new Quantity<>(12.0, LengthUnit.INCHES);
		assertTrue(q1.equals(q2));
	}

	@Test
	void testGenericQuantity_WeightOperations_Equality() {
		Quantity<WeightUnit> q1 = new Quantity<>(1.0, WeightUnit.KILOGRAM);
		Quantity<WeightUnit> q2 = new Quantity<>(1000.0, WeightUnit.GRAM);
		assertTrue(q1.equals(q2));
	}

	@Test
	void testCrossCategoryPrevention_LengthVsWeight() {
		Quantity<LengthUnit> length = new Quantity<>(1.0, LengthUnit.FEET);
		Quantity<WeightUnit> weight = new Quantity<>(1.0, WeightUnit.KILOGRAM);
		assertFalse(length.equals(weight));
	}

	@Test
	void testGenericQuantity_LengthOperations_Conversion() {
		Quantity<LengthUnit> q = new Quantity<>(1.0, LengthUnit.FEET);
		Quantity<LengthUnit> result = q.convertTo(LengthUnit.INCHES);
		assertEquals(12.0, result.getValue());
		assertEquals(LengthUnit.INCHES, result.getUnit());
	}

	@Test
	void testGenericQuantity_WeightOperations_Conversion() {
		Quantity<WeightUnit> q = new Quantity<>(1.0, WeightUnit.KILOGRAM);
		Quantity<WeightUnit> result = q.convertTo(WeightUnit.GRAM);
		assertEquals(1000.0, result.getValue());
		assertEquals(WeightUnit.GRAM, result.getUnit());
	}

	@Test
	void testGenericQuantity_LengthOperations_Addition() {
		Quantity<LengthUnit> q1 = new Quantity<>(1.0, LengthUnit.FEET);
		Quantity<LengthUnit> q2 = new Quantity<>(12.0, LengthUnit.INCHES);
		Quantity<LengthUnit> result = q1.add(q2, LengthUnit.FEET);
		assertEquals(2.0, result.getValue());
		assertEquals(LengthUnit.FEET, result.getUnit());
	}

	@Test
	void testGenericQuantity_WeightOperations_Addition() {
		Quantity<WeightUnit> q1 = new Quantity<>(1.0, WeightUnit.KILOGRAM);
		Quantity<WeightUnit> q2 = new Quantity<>(1000.0, WeightUnit.GRAM);
		Quantity<WeightUnit> result = q1.add(q2, WeightUnit.KILOGRAM);
		assertEquals(2.0, result.getValue());
		assertEquals(WeightUnit.KILOGRAM, result.getUnit());
	}

	@Test
	void testGenericQuantity_ConstructorValidation_NullUnit() {
		assertThrows(IllegalArgumentException.class, () -> new Quantity<>(1.0, null));
	}

	@Test
	void testGenericQuantity_ConstructorValidation_InvalidValue() {
		assertThrows(IllegalArgumentException.class, () -> new Quantity<>(Double.NaN, LengthUnit.FEET));
	}

	@Test
	void testHashCode_GenericQuantity_Consistency() {
		Quantity<LengthUnit> q1 = new Quantity<>(1.0, LengthUnit.FEET);
		Quantity<LengthUnit> q2 = new Quantity<>(12.0, LengthUnit.INCHES);
		assertEquals(q1, q2);
		assertEquals(q1.hashCode(), q2.hashCode());
	}

	@Test
	void testEquals_GenericQuantity_ContractPreservation() {
		Quantity<LengthUnit> a = new Quantity<>(1.0, LengthUnit.FEET);
		Quantity<LengthUnit> b = new Quantity<>(12.0, LengthUnit.INCHES);
		assertTrue(a.equals(b));
		assertTrue(b.equals(a));
	}

	@Test
	void testImmutability_GenericQuantity() {
		Quantity<LengthUnit> q1 = new Quantity<>(1.0, LengthUnit.FEET);
		Quantity<LengthUnit> q2 = q1.convertTo(LengthUnit.INCHES);
		assertNotSame(q1, q2);
	}

	@Test
	void testTypeWildcard_FlexibleSignatures() {
		Quantity<?> q1 = new Quantity<>(1.0, LengthUnit.FEET);
		Quantity<?> q2 = new Quantity<>(1.0, WeightUnit.KILOGRAM);
		assertNotNull(q1);
		assertNotNull(q2);
	}

	enum VolumeUnit implements IMeasurable {
		LITER(1.0), MILLILITER(0.001);

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

	@Test
	void testScalability_NewUnitEnumIntegration() {
		Quantity<VolumeUnit> v1 = new Quantity<>(1.0, VolumeUnit.LITER);
		Quantity<VolumeUnit> v2 = new Quantity<>(1000.0, VolumeUnit.MILLILITER);
		assertTrue(v1.equals(v2));
	}

	@Test
	void testQuantityMeasurementApp_SimplifiedDemonstration_Equality() {
		Quantity<LengthUnit> q1 = new Quantity<>(1.0, LengthUnit.FEET);
		Quantity<LengthUnit> q2 = new Quantity<>(12.0, LengthUnit.INCHES);
		assertDoesNotThrow(() -> QuantityMeasurementApp.demonstrateEquality(q1, q2));
	}

	@Test
	void testQuantityMeasurementApp_SimplifiedDemonstration_Conversion() {
		Quantity<WeightUnit> q = new Quantity<>(1.0, WeightUnit.KILOGRAM);
		assertDoesNotThrow(() -> QuantityMeasurementApp.demonstrateConversion(q, WeightUnit.GRAM));
	}

	@Test
	void testQuantityMeasurementApp_SimplifiedDemonstration_Addition() {
		Quantity<WeightUnit> q1 = new Quantity<>(1.0, WeightUnit.KILOGRAM);
		Quantity<WeightUnit> q2 = new Quantity<>(1000.0, WeightUnit.GRAM);
		assertDoesNotThrow(() -> QuantityMeasurementApp.demonstrateAddition(q1, q2, WeightUnit.KILOGRAM));
	}
}
