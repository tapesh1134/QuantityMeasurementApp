package org.quantitymeasurement.app;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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

	@Test
	void testScalability_NewUnitEnumIntegration() {
		Quantity<VolumeUnit> v1 = new Quantity<>(1.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> v2 = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
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

	@Test
	void testVolumeConversion_LitreToMillilitre() {
		Quantity<VolumeUnit> result = new Quantity<>(1.0, VolumeUnit.LITRE).convertTo(VolumeUnit.MILLILITRE);

		assertEquals(1000.0, result.getValue());
	}

	@Test
	void testVolumeConversion_MillilitreToLitre() {
		Quantity<VolumeUnit> result = new Quantity<>(1000.0, VolumeUnit.MILLILITRE).convertTo(VolumeUnit.LITRE);

		assertEquals(1.0, result.getValue());
	}

	@Test
	void testVolumeConversion_GallonToLitre() {
		Quantity<VolumeUnit> result = new Quantity<>(1.0, VolumeUnit.GALLON).convertTo(VolumeUnit.LITRE);

		assertEquals(3.78541, result.getValue(), 0.01);
	}

	@Test
	void testVolumeConversion_LitreToGallon() {
		Quantity<VolumeUnit> result = new Quantity<>(3.78541, VolumeUnit.LITRE).convertTo(VolumeUnit.GALLON);

		assertEquals(1.0, result.getValue(), 0.01);
	}

	@Test
	void testVolumeConversion_SameUnit() {
		Quantity<VolumeUnit> result = new Quantity<>(5.0, VolumeUnit.LITRE).convertTo(VolumeUnit.LITRE);

		assertEquals(5.0, result.getValue());
	}

	// === === addition test === ===

	@Test
	void testVolumeAddition_SameUnit_Litre() {
		Quantity<VolumeUnit> result = new Quantity<>(1.0, VolumeUnit.LITRE).add(new Quantity<>(2.0, VolumeUnit.LITRE));

		assertEquals(3.0, result.getValue());
	}

	@Test
	void testVolumeAddition_CrossUnit_LitreMillilitre() {
		Quantity<VolumeUnit> result = new Quantity<>(1.0, VolumeUnit.LITRE)
				.add(new Quantity<>(1000.0, VolumeUnit.MILLILITRE));

		assertEquals(2.0, result.getValue());
	}

	@Test
	void testVolumeAddition_CrossUnit_GallonLitre() {
		Quantity<VolumeUnit> result = new Quantity<>(1.0, VolumeUnit.GALLON)
				.add(new Quantity<>(3.78541, VolumeUnit.LITRE));

		assertEquals(2.0, result.getValue(), 0.01);
	}

	@Test
	void testVolumeAddition_Target_Litre() {
		Quantity<VolumeUnit> result = new Quantity<>(1.0, VolumeUnit.LITRE)
				.add(new Quantity<>(1000.0, VolumeUnit.MILLILITRE), VolumeUnit.LITRE);

		assertEquals(2.0, result.getValue());
	}

	@Test
	void testVolumeAddition_Target_Millilitre() {
		Quantity<VolumeUnit> result = new Quantity<>(1.0, VolumeUnit.LITRE)
				.add(new Quantity<>(1000.0, VolumeUnit.MILLILITRE), VolumeUnit.MILLILITRE);

		assertEquals(2000.0, result.getValue());
	}

	@Test
	void testVolumeAddition_Target_Gallon() {
		Quantity<VolumeUnit> result = new Quantity<>(3.78541, VolumeUnit.LITRE)
				.add(new Quantity<>(3.78541, VolumeUnit.LITRE), VolumeUnit.GALLON);

		assertEquals(2.0, result.getValue(), 0.01);
	}

	@Test
	void testVolumeAddition_WithZero() {
		Quantity<VolumeUnit> result = new Quantity<>(5.0, VolumeUnit.LITRE)
				.add(new Quantity<>(0.0, VolumeUnit.MILLILITRE));

		assertEquals(5.0, result.getValue());
	}

	@Test
	void testVolumeAddition_Negative() {
		Quantity<VolumeUnit> result = new Quantity<>(5.0, VolumeUnit.LITRE)
				.add(new Quantity<>(-2000.0, VolumeUnit.MILLILITRE));

		assertEquals(3.0, result.getValue());
	}

	@Test
	void testVolumeAddition_LargeValues() {
		Quantity<VolumeUnit> result = new Quantity<>(1e6, VolumeUnit.LITRE).add(new Quantity<>(1e6, VolumeUnit.LITRE));

		assertEquals(2e6, result.getValue());
	}

	@Test
	void testVolumeUnit_LitreFactor() {
		assertEquals(1.0, VolumeUnit.LITRE.getConversionFactor());
	}

	@Test
	void testVolumeUnit_MillilitreFactor() {
		assertEquals(0.001, VolumeUnit.MILLILITRE.getConversionFactor());
	}

	@Test
	void testVolumeUnit_GallonFactor() {
		assertEquals(3.78541, VolumeUnit.GALLON.getConversionFactor());
	}

	@Test
	void testSubtraction_SameUnit() {
		Quantity<LengthUnit> q1 = new Quantity<>(10.0, LengthUnit.FEET);
		Quantity<LengthUnit> q2 = new Quantity<>(5.0, LengthUnit.FEET);
		Quantity<LengthUnit> result = q1.subtract(q2);
		assertEquals(5.0, result.getValue());
		assertEquals(LengthUnit.FEET, result.getUnit());
	}

	@Test
	void testSubtraction_CrossUnit() {
		Quantity<LengthUnit> q1 = new Quantity<>(10.0, LengthUnit.FEET);
		Quantity<LengthUnit> q2 = new Quantity<>(6.0, LengthUnit.INCHES);
		Quantity<LengthUnit> result = q1.subtract(q2);
		assertEquals(9.5, result.getValue());
		assertEquals(LengthUnit.FEET, result.getUnit());
	}

	@Test
	void testSubtraction_ResultNegative() {
		Quantity<LengthUnit> q1 = new Quantity<>(5.0, LengthUnit.FEET);
		Quantity<LengthUnit> q2 = new Quantity<>(10.0, LengthUnit.FEET);
		Quantity<LengthUnit> result = q1.subtract(q2);
		assertEquals(-5.0, result.getValue());
	}

	@Test
	void testSubtraction_Identity() {
		Quantity<LengthUnit> q1 = new Quantity<>(10.0, LengthUnit.FEET);
		Quantity<LengthUnit> zero = new Quantity<>(0.0, LengthUnit.FEET);
		Quantity<LengthUnit> result = q1.subtract(zero);
		assertEquals(q1, result);
	}

	@Test
	void testSubtraction_NullOperand_Throws() {
		Quantity<LengthUnit> q1 = new Quantity<>(10.0, LengthUnit.FEET);
		assertThrows(IllegalArgumentException.class, () -> q1.subtract(null));
	}

	@Test
	void testSubtraction_CrossCategory_Throws() {
		Quantity<LengthUnit> length = new Quantity<>(10.0, LengthUnit.FEET);
		Quantity<WeightUnit> weight = new Quantity<>(10.0, WeightUnit.KILOGRAM);
		assertThrows(IllegalArgumentException.class, () -> length.subtract((Quantity) weight));
	}

	@Test
	void testDivision_SameUnit() {
		Quantity<LengthUnit> q1 = new Quantity<>(10.0, LengthUnit.FEET);
		Quantity<LengthUnit> q2 = new Quantity<>(2.0, LengthUnit.FEET);
		double result = q1.divide(q2);
		assertEquals(5.0, result);
	}

	@Test
	void testDivision_CrossUnit() {
		Quantity<LengthUnit> inches = new Quantity<>(24.0, LengthUnit.INCHES);
		Quantity<LengthUnit> feet = new Quantity<>(2.0, LengthUnit.FEET);
		double result = inches.divide(feet);
		assertEquals(1.0, result);
	}

	@Test
	void testDivision_ByItself_IsOne() {
		Quantity<LengthUnit> q = new Quantity<>(10.0, LengthUnit.FEET);
		double result = q.divide(q);
		assertEquals(1.0, result);
	}

	@Test
	void testDivision_ByZero_Throws() {
		Quantity<LengthUnit> q1 = new Quantity<>(10.0, LengthUnit.FEET);
		Quantity<LengthUnit> zero = new Quantity<>(0.0, LengthUnit.FEET);
		assertThrows(ArithmeticException.class, () -> q1.divide(zero));
	}

	@Test
	void testDivision_NullOperand_Throws() {
		Quantity<LengthUnit> q1 = new Quantity<>(10.0, LengthUnit.FEET);
		assertThrows(IllegalArgumentException.class, () -> q1.divide(null));
	}

	@Test
	void testDivision_CrossCategory_Throws() {
		Quantity<LengthUnit> length = new Quantity<>(10.0, LengthUnit.FEET);
		Quantity<WeightUnit> weight = new Quantity<>(2.0, WeightUnit.KILOGRAM);
		assertThrows(IllegalArgumentException.class, () -> length.divide((Quantity) weight));
	}

	@Test
	void testNonCommutative_Subtraction() {
		Quantity<LengthUnit> a = new Quantity<>(10.0, LengthUnit.FEET);
		Quantity<LengthUnit> b = new Quantity<>(5.0, LengthUnit.FEET);
		assertNotEquals(a.subtract(b), b.subtract(a));
	}

	@Test
	void testInverse_Property() {
		Quantity<LengthUnit> a = new Quantity<>(10.0, LengthUnit.FEET);
		Quantity<LengthUnit> b = new Quantity<>(3.0, LengthUnit.FEET);
		Quantity<LengthUnit> result = a.add(b).subtract(b);
		assertEquals(a, result);
	}

	@Test
	void testImmutability() {
		Quantity<LengthUnit> original = new Quantity<>(10.0, LengthUnit.FEET);
		Quantity<LengthUnit> other = new Quantity<>(5.0, LengthUnit.FEET);
		original.subtract(other);
		assertEquals(10.0, original.getValue());
		assertEquals(LengthUnit.FEET, original.getUnit());
	}

	@Test
	void testArithmetic_Add_Delegation() {
		Quantity<LengthUnit> q1 = new Quantity<>(10.0, LengthUnit.FEET);
		Quantity<LengthUnit> q2 = new Quantity<>(5.0, LengthUnit.FEET);

		assertEquals(15.0, q1.add(q2).getValue());
	}

	@Test
	void testArithmetic_Subtract_Delegation() {
		Quantity<LengthUnit> q1 = new Quantity<>(10.0, LengthUnit.FEET);
		Quantity<LengthUnit> q2 = new Quantity<>(5.0, LengthUnit.FEET);

		assertEquals(5.0, q1.subtract(q2).getValue());
	}

	@Test
	void testArithmetic_Divide_Delegation() {
		Quantity<LengthUnit> q1 = new Quantity<>(10.0, LengthUnit.FEET);
		Quantity<LengthUnit> q2 = new Quantity<>(5.0, LengthUnit.FEET);
		assertEquals(2.0, q1.divide(q2));
	}

	@Test
	void testValidation_NullOperand_Add() {
		Quantity<LengthUnit> q = new Quantity<>(10.0, LengthUnit.FEET);
		assertThrows(IllegalArgumentException.class, () -> q.add(null));
	}

	@Test
	void testValidation_NullOperand_Subtract() {
		Quantity<LengthUnit> q = new Quantity<>(10.0, LengthUnit.FEET);
		assertThrows(IllegalArgumentException.class, () -> q.subtract(null));
	}

	@Test
	void testValidation_NullOperand_Divide() {
		Quantity<LengthUnit> q = new Quantity<>(10.0, LengthUnit.FEET);
		assertThrows(IllegalArgumentException.class, () -> q.divide(null));
	}

	@Test
	void testValidation_DivideByZero() {
		Quantity<LengthUnit> q1 = new Quantity<>(10.0, LengthUnit.FEET);
		Quantity<LengthUnit> q2 = new Quantity<>(0.0, LengthUnit.FEET);
		assertThrows(ArithmeticException.class, () -> q1.divide(q2));
	}

	@Test
	void testValidation_NullTargetUnit() {
		Quantity<LengthUnit> q1 = new Quantity<>(10.0, LengthUnit.FEET);
		Quantity<LengthUnit> q2 = new Quantity<>(5.0, LengthUnit.FEET);
		assertThrows(IllegalArgumentException.class, () -> q1.add(q2, null));
	}

	@Test
	void testAddition_SameUnit() {
		Quantity<LengthUnit> result = new Quantity<>(10.0, LengthUnit.FEET).add(new Quantity<>(5.0, LengthUnit.FEET));
		assertEquals(15.0, result.getValue());
	}

	@Test
	void testAddition_CrossUnit() {
		Quantity<LengthUnit> result = new Quantity<>(1.0, LengthUnit.FEET).add(new Quantity<>(12.0, LengthUnit.INCHES));
		assertEquals(2.0, result.getValue());
	}

	@Test
	void testAddition_WithTargetUnit() {
		Quantity<LengthUnit> result = new Quantity<>(1.0, LengthUnit.FEET).add(new Quantity<>(12.0, LengthUnit.INCHES),
				LengthUnit.INCHES);
		assertEquals(24.0, result.getValue());
	}

	@Test
	void testAddition_WithZero() {
		Quantity<LengthUnit> result = new Quantity<>(5.0, LengthUnit.FEET).add(new Quantity<>(0.0, LengthUnit.INCHES));
		assertEquals(5.0, result.getValue());
	}

	@Test
	void testAddition_NegativeValues() {
		Quantity<LengthUnit> result = new Quantity<>(5.0, LengthUnit.FEET).add(new Quantity<>(-2.0, LengthUnit.FEET));
		assertEquals(3.0, result.getValue());
	}

	@Test
	void testAddition_LargeValues() {
		Quantity<WeightUnit> result = new Quantity<>(1e6, WeightUnit.KILOGRAM)
				.add(new Quantity<>(1e6, WeightUnit.KILOGRAM));
		assertEquals(2e6, result.getValue());
	}

	@Test
	void testSubtraction_WithTargetUnit() {
		Quantity<LengthUnit> result = new Quantity<>(10.0, LengthUnit.FEET)
				.subtract(new Quantity<>(6.0, LengthUnit.INCHES), LengthUnit.INCHES);

		assertEquals(114.0, result.getValue());
	}

	@Test
	void testSubtraction_ResultZero() {
		Quantity<LengthUnit> result = new Quantity<>(10.0, LengthUnit.FEET)
				.subtract(new Quantity<>(120.0, LengthUnit.INCHES));

		assertEquals(0.0, result.getValue());
	}

	@Test
	void testDivision_LargeRatio() {
		assertEquals(1e6, new Quantity<>(1e6, WeightUnit.KILOGRAM).divide(new Quantity<>(1.0, WeightUnit.KILOGRAM)));
	}

	@Test
	void testRounding_Addition() {
		Quantity<LengthUnit> result = new Quantity<>(1.234, LengthUnit.FEET)
				.add(new Quantity<>(1.234, LengthUnit.FEET));
		assertEquals(2.47, result.getValue());
	}

	@Test
	void testRounding_Subtraction() {
		Quantity<LengthUnit> result = new Quantity<>(1.235, LengthUnit.FEET)
				.subtract(new Quantity<>(0.234, LengthUnit.FEET));
		assertEquals(1.0, result.getValue());
	}

	@Test
	void testChainedOperations() {
		Quantity<LengthUnit> result = new Quantity<>(10.0, LengthUnit.FEET)
				.subtract(new Quantity<>(2.0, LengthUnit.FEET)).subtract(new Quantity<>(1.0, LengthUnit.FEET));
		assertEquals(7.0, result.getValue());
	}

	@Test
	void testAddSubtractInverse() {
		Quantity<LengthUnit> a = new Quantity<>(10.0, LengthUnit.FEET);
		Quantity<LengthUnit> b = new Quantity<>(2.0, LengthUnit.FEET);
		Quantity<LengthUnit> result = a.add(b).subtract(b);
		assertTrue(a.equals(result));
	}

	@Test
	void testOperations_AllCategories() {
		assertEquals(2.0, new Quantity<>(1.0, LengthUnit.FEET).add(new Quantity<>(12.0, LengthUnit.INCHES)).getValue());
		assertEquals(2.0,
				new Quantity<>(1.0, WeightUnit.KILOGRAM).add(new Quantity<>(1000.0, WeightUnit.GRAM)).getValue());
		assertEquals(2.0,
				new Quantity<>(1.0, VolumeUnit.LITRE).add(new Quantity<>(1000.0, VolumeUnit.MILLILITRE)).getValue());
	}
}
