package org.quantitymeasurement.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class QuantityMeasurementAppTest {
	private final double EPSILON = 0.001;

	@Test
	void testEquality_FeetToFeet_SameValue() {
		Length q1 = new Length(1.0, LengthUnit.FEET);
		Length q2 = new Length(1.0, LengthUnit.FEET);
		assertEquals(q1, q2);
	}

	@Test
	void testEquality_INCHESToINCHES_SameValue() {
		Length q1 = new Length(1.0, LengthUnit.INCHES);
		Length q2 = new Length(1.0, LengthUnit.INCHES);
		assertEquals(q1, q2);
	}

	@Test
	void testEquality_FeetToINCHES_EquivalentValue() {
		Length q1 = new Length(1.0, LengthUnit.FEET);
		Length q2 = new Length(12.0, LengthUnit.INCHES);
		assertEquals(q1, q2);
	}

	@Test
	void testEquality_DifferentValues() {
		Length q1 = new Length(1.0, LengthUnit.FEET);
		Length q2 = new Length(2.0, LengthUnit.FEET);
		assertNotEquals(q1, q2);
	}

	@Test
	void testEquality_SameReference() {
		Length q1 = new Length(1.0, LengthUnit.FEET);
		assertEquals(q1, q1);
	}

	@Test
	void testEquality_NullComparison() {
		Length q1 = new Length(1.0, LengthUnit.FEET);
		assertNotEquals(q1, null);
	}

	@Test
	void testInvalidUnit() {
		assertThrows(IllegalArgumentException.class, () -> new Length(1.0, null));
	}

	@Test
	void testEquality_YARDSToFeet_EquivalentValue() {
		Length q1 = new Length(1.0, LengthUnit.YARDS);
		Length q2 = new Length(3.0, LengthUnit.FEET);
		assertEquals(q1, q2);
	}

	@Test
	void testEquality_YARDSToINCHESes_EquivalentValue() {
		Length q1 = new Length(1.0, LengthUnit.YARDS);
		Length q2 = new Length(36.0, LengthUnit.INCHES);
		assertEquals(q1, q2);
	}

	@Test
	void testEquality_CentimeterToINCHES_EquivalentValue() {
		Length q1 = new Length(1.0, LengthUnit.CENTIMETERS);
		Length q2 = new Length(0.393701, LengthUnit.INCHES);
		assertEquals(q1, q2);
	}

	@Test
	void testEquality_MultiUnit_TransitiveProperty() {
		Length YARDS = new Length(1.0, LengthUnit.YARDS);
		Length feet = new Length(3.0, LengthUnit.FEET);
		Length INCHES = new Length(36.0, LengthUnit.INCHES);

		assertTrue(YARDS.equals(feet));
		assertTrue(feet.equals(INCHES));
		assertTrue(YARDS.equals(INCHES));
	}

	@Test
	void testConversion_FeetToINCHES() {
		assertEquals(QuantityMeasurementApp.demonstrateLengthConversion(1.0, LengthUnit.FEET, LengthUnit.INCHES),
				new Length(12.0, LengthUnit.INCHES));
	}

	@Test
	void testConversion_INCHESToFeet() {
		assertEquals(QuantityMeasurementApp.demonstrateLengthConversion(24.0, LengthUnit.INCHES, LengthUnit.FEET),
				new Length(2.0, LengthUnit.FEET));
	}

	@Test
	void testConversion_YARDSsToINCHESes() {
		assertEquals(QuantityMeasurementApp.demonstrateLengthConversion(1.0, LengthUnit.YARDS, LengthUnit.INCHES),
				new Length(36.0, LengthUnit.INCHES));
	}

	@Test
	void testConversion_INCHESesToYARDSs() {
		assertEquals(QuantityMeasurementApp.demonstrateLengthConversion(72.0, LengthUnit.INCHES, LengthUnit.YARDS),
				new Length(2.0, LengthUnit.YARDS));
	}

	@Test
	void testConversion_FeatToYARDS() {
		assertEquals(QuantityMeasurementApp.demonstrateLengthConversion(6.0, LengthUnit.FEET, LengthUnit.YARDS),
				new Length(2.0, LengthUnit.YARDS));
	}

	@Test
	void testConversion_RoundTrip_PreservesValue() {
		Length original = new Length(5.0, LengthUnit.FEET);
		Length converted = QuantityMeasurementApp.demonstrateLengthConversion(original, LengthUnit.INCHES);
		Length back = QuantityMeasurementApp.demonstrateLengthConversion(converted, LengthUnit.FEET);
		assertEquals(original, back);
	}

	@Test
	void testConversion_ZeroValue() {
		assertEquals(QuantityMeasurementApp.demonstrateLengthConversion(0.0, LengthUnit.FEET, LengthUnit.INCHES),
				new Length(0.0, LengthUnit.INCHES));
	}

	@Test
	void testConversion_NegativeValue() {
		Length result = QuantityMeasurementApp.demonstrateLengthConversion(-1.0, LengthUnit.FEET, LengthUnit.INCHES);

		assertEquals(new Length(-12.0, LengthUnit.INCHES), result);
	}

	@Test
	void testConversion_InvalidUnit_Throws() {
		assertThrows(IllegalArgumentException.class,
				() -> QuantityMeasurementApp.demonstrateLengthConversion(1.0, null, LengthUnit.INCHES));
	}

	@Test
	void testConversion_NaNOrInfinite_Throws() {
		assertThrows(IllegalArgumentException.class, () -> QuantityMeasurementApp
				.demonstrateLengthConversion(Double.NaN, LengthUnit.FEET, LengthUnit.INCHES));

		assertThrows(IllegalArgumentException.class, () -> QuantityMeasurementApp
				.demonstrateLengthConversion(Double.POSITIVE_INFINITY, LengthUnit.FEET, LengthUnit.INCHES));

		assertThrows(IllegalArgumentException.class, () -> QuantityMeasurementApp
				.demonstrateLengthConversion(Double.NEGATIVE_INFINITY, LengthUnit.FEET, LengthUnit.INCHES));
	}

	@Test
	void testConversion_PrecisionTolerance() {
		Length result = QuantityMeasurementApp.demonstrateLengthConversion(1.0, LengthUnit.FEET, LengthUnit.INCHES);
		double expected = 12.0;
		assertEquals(result, new Length(expected, LengthUnit.INCHES));
	}

	@Test
	void testAddition_SameUnit_FeetPlusFeet() {
		Length l1 = new Length(1.0, LengthUnit.FEET);
		Length l2 = new Length(2.0, LengthUnit.FEET);
		assertEquals(new Length(3.0, LengthUnit.FEET), l1.add(l2));
	}

	@Test
	void testAddition_SameUnit_InchPlusInch() {
		Length l1 = new Length(6.0, LengthUnit.INCHES);
		Length l2 = new Length(6.0, LengthUnit.INCHES);
		assertEquals(new Length(12.0, LengthUnit.INCHES), l1.add(l2));
	}

	@Test
	void testAddition_CrossUnit_FeetPlusInches() {
		Length l1 = new Length(1.0, LengthUnit.FEET);
		Length l2 = new Length(12.0, LengthUnit.INCHES);
		assertEquals(new Length(2.0, LengthUnit.FEET), l1.add(l2));
	}

	@Test
	void testAddition_CrossUnit_InchPlusFeet() {
		Length l1 = new Length(12.0, LengthUnit.INCHES);
		Length l2 = new Length(1.0, LengthUnit.FEET);
		assertEquals(new Length(24.0, LengthUnit.INCHES), l1.add(l2));
	}

	@Test
	void testAddition_CrossUnit_YardPlusFeet() {
		Length l1 = new Length(1.0, LengthUnit.YARDS);
		Length l2 = new Length(3.0, LengthUnit.FEET);
		assertEquals(new Length(2.0, LengthUnit.YARDS), l1.add(l2));
	}

	@Test
	void testAddition_CrossUnit_CentimeterPlusInch() {
		Length l1 = new Length(2.54, LengthUnit.CENTIMETERS);
		Length l2 = new Length(1.0, LengthUnit.INCHES);
		assertEquals(new Length(5.08, LengthUnit.CENTIMETERS), l1.add(l2));
	}

	@Test
	void testAddition_Commutativity() {
		Length l1 = new Length(1.0, LengthUnit.FEET);
		Length l2 = new Length(12.0, LengthUnit.INCHES);
		assertEquals(l1.add(l2), l2.add(l1));
	}

	@Test
	void testAddition_WithZero() {
		Length l1 = new Length(5.0, LengthUnit.FEET);
		Length l2 = new Length(0.0, LengthUnit.INCHES);
		assertEquals(new Length(5.0, LengthUnit.FEET), l1.add(l2));
	}

	@Test
	void testAddition_NegativeValues() {
		Length l1 = new Length(5.0, LengthUnit.FEET);
		Length l2 = new Length(-2.0, LengthUnit.FEET);
		assertEquals(new Length(3.0, LengthUnit.FEET), l1.add(l2));
	}

	@Test
	void testAddition_NullSecondOperand() {
		Length l1 = new Length(1.0, LengthUnit.FEET);
		assertThrows(IllegalArgumentException.class, () -> l1.add(null));
	}

	@Test
	void testAddition_LargeValues() {
		Length l1 = new Length(1e6, LengthUnit.FEET);
		Length l2 = new Length(1e6, LengthUnit.FEET);
		assertEquals(new Length(2e6, LengthUnit.FEET), l1.add(l2));
	}

	@Test
	void testAddition_SmallValues() {
		Length l1 = new Length(0.001, LengthUnit.FEET);
		Length l2 = new Length(0.002, LengthUnit.FEET);
		assertEquals(new Length(0.003, LengthUnit.FEET), l1.add(l2));
	}

	@Test
	void testAddition_ExplicitTargetUnit_Feet() {
		Length l1 = new Length(1.0, LengthUnit.FEET);
		Length l2 = new Length(12.0, LengthUnit.INCHES);
		assertEquals(new Length(2.0, LengthUnit.FEET), l1.add(l2, LengthUnit.FEET));
	}

	@Test
	void testAddition_ExplicitTargetUnit_Inches() {
		Length l1 = new Length(1.0, LengthUnit.FEET);
		Length l2 = new Length(12.0, LengthUnit.INCHES);
		assertEquals(new Length(24.0, LengthUnit.INCHES), l1.add(l2, LengthUnit.INCHES));
	}

	@Test
	void testAddition_ExplicitTargetUnit_Yards() {
		Length l1 = new Length(1.0, LengthUnit.FEET);
		Length l2 = new Length(12.0, LengthUnit.INCHES);
		Length result = l1.add(l2, LengthUnit.YARDS);
		assertEquals(0.667, result.getValue(), EPSILON);
		assertEquals(LengthUnit.YARDS, result.getUnit());
	}

	@Test
	void testAddition_ExplicitTargetUnit_Centimeters() {
		Length l1 = new Length(1.0, LengthUnit.INCHES);
		Length l2 = new Length(1.0, LengthUnit.INCHES);
		Length result = l1.add(l2, LengthUnit.CENTIMETERS);
		assertEquals(5.08, result.getValue(), EPSILON);
		assertEquals(LengthUnit.CENTIMETERS, result.getUnit());
	}

	@Test
	void testAddition_ExplicitTargetUnit_SameAsFirstOperand() {
		Length l1 = new Length(2.0, LengthUnit.YARDS);
		Length l2 = new Length(3.0, LengthUnit.FEET);
		assertEquals(new Length(3.0, LengthUnit.YARDS), l1.add(l2, LengthUnit.YARDS));
	}

	@Test
	void testAddition_ExplicitTargetUnit_SameAsSecondOperand() {
		Length l1 = new Length(2.0, LengthUnit.YARDS);
		Length l2 = new Length(3.0, LengthUnit.FEET);
		assertEquals(new Length(9.0, LengthUnit.FEET), l1.add(l2, LengthUnit.FEET));
	}

	@Test
	void testAddition_ExplicitTargetUnit_Commutativity() {
		Length l1 = new Length(1.0, LengthUnit.FEET);
		Length l2 = new Length(12.0, LengthUnit.INCHES);
		Length result1 = l1.add(l2, LengthUnit.YARDS);
		Length result2 = l2.add(l1, LengthUnit.YARDS);
		assertEquals(result1, result2);
	}

	@Test
	void testAddition_ExplicitTargetUnit_WithZero() {
		Length l1 = new Length(5.0, LengthUnit.FEET);
		Length l2 = new Length(0.0, LengthUnit.INCHES);
		assertEquals(1.667, l1.add(l2, LengthUnit.YARDS).getValue(), EPSILON);
	}

	@Test
	void testAddition_ExplicitTargetUnit_NegativeValues() {
		Length l1 = new Length(5.0, LengthUnit.FEET);
		Length l2 = new Length(-2.0, LengthUnit.FEET);
		Length result = l1.add(l2, LengthUnit.INCHES);
		assertEquals(36.0, result.getValue());
		assertEquals(LengthUnit.INCHES, result.getUnit());
	}

	@Test
	void testAddition_ExplicitTargetUnit_NullTargetUnit() {
		Length l1 = new Length(1.0, LengthUnit.FEET);
		Length l2 = new Length(12.0, LengthUnit.INCHES);
		assertThrows(IllegalArgumentException.class, () -> l1.add(l2, null));
	}

	@Test
	void testAddition_ExplicitTargetUnit_LargeToSmallScale() {
		Length l1 = new Length(1000.0, LengthUnit.FEET);
		Length l2 = new Length(500.0, LengthUnit.FEET);
		assertEquals(18000.0, l1.add(l2, LengthUnit.INCHES).getValue());
	}

	@Test
	void testAddition_ExplicitTargetUnit_SmallToLargeScale() {
		Length l1 = new Length(12.0, LengthUnit.INCHES);
		Length l2 = new Length(12.0, LengthUnit.INCHES);
		assertEquals(0.667, l1.add(l2, LengthUnit.YARDS).getValue(), EPSILON);
	}

	@Test
	void testAddition_ExplicitTargetUnit_AllUnitCombinations() {
		LengthUnit[] units = LengthUnit.values();
		for (LengthUnit u1 : units) {
			for (LengthUnit u2 : units) {
				for (LengthUnit target : units) {
					Length l1 = new Length(1.0, u1);
					Length l2 = new Length(1.0, u2);
					Length result = l1.add(l2, target);
					double expected = l1.convertTo(target).getValue() + l2.convertTo(target).getValue();
					assertEquals(expected, result.getValue(), EPSILON);
				}
			}
		}
	}

	@Test
	void testAddition_ExplicitTargetUnit_PrecisionTolerance() {
		Length l1 = new Length(2.54, LengthUnit.CENTIMETERS);
		Length l2 = new Length(1.0, LengthUnit.INCHES);
		assertEquals(5.08, l1.add(l2, LengthUnit.CENTIMETERS).getValue(), EPSILON);
	}

	@Test
	void testEquality_KilogramToGram() {
		QuantityWeight kg = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
		QuantityWeight g = new QuantityWeight(1000.0, WeightUnit.GRAM);
		assertEquals(kg, g);
	}

	@Test
	void testConversion_PoundToKilogram() {
		QuantityWeight pound = new QuantityWeight(2.20462, WeightUnit.POUND);
		QuantityWeight kg = pound.convertTo(WeightUnit.KILOGRAM);
		assertEquals(1.0, kg.getValue(), 1e-4);
	}

	@Test
	void testAddition_CrossUnit() {
		QuantityWeight w1 = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
		QuantityWeight w2 = new QuantityWeight(1000.0, WeightUnit.GRAM);
		QuantityWeight result = w1.add(w2);
		assertEquals(new QuantityWeight(2.0, WeightUnit.KILOGRAM), result);
	}
}
