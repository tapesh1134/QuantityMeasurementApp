package org.quantitymeasurement.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.quantitymeasurement.app.Length.Unit;

public class QuantityMeasurementAppTest {
	private final double EPSILON = 0.001;
	
	@Test
	void testEquality_FeetToFeet_SameValue() {
		Length q1 = new Length(1.0, Unit.FEET);
		Length q2 = new Length(1.0, Unit.FEET);
		assertEquals(q1, q2);
	}

	@Test
	void testEquality_INCHESToINCHES_SameValue() {
		Length q1 = new Length(1.0, Unit.INCHES);
		Length q2 = new Length(1.0, Unit.INCHES);
		assertEquals(q1, q2);
	}

	@Test
	void testEquality_FeetToINCHES_EquivalentValue() {
		Length q1 = new Length(1.0, Unit.FEET);
		Length q2 = new Length(12.0, Unit.INCHES);
		assertEquals(q1, q2);
	}

	@Test
	void testEquality_DifferentValues() {
		Length q1 = new Length(1.0, Unit.FEET);
		Length q2 = new Length(2.0, Unit.FEET);
		assertNotEquals(q1, q2);
	}

	@Test
	void testEquality_SameReference() {
		Length q1 = new Length(1.0, Unit.FEET);
		assertEquals(q1, q1);
	}

	@Test
	void testEquality_NullComparison() {
		Length q1 = new Length(1.0, Unit.FEET);
		assertNotEquals(q1, null);
	}

	@Test
	void testInvalidUnit() {
		assertThrows(IllegalArgumentException.class, () -> new Length(1.0, null));
	}

	@Test
	void testEquality_YARDSToFeet_EquivalentValue() {
		Length q1 = new Length(1.0, Unit.YARDS);
		Length q2 = new Length(3.0, Unit.FEET);
		assertEquals(q1, q2);
	}

	@Test
	void testEquality_YARDSToINCHESes_EquivalentValue() {
		Length q1 = new Length(1.0, Unit.YARDS);
		Length q2 = new Length(36.0, Unit.INCHES);
		assertEquals(q1, q2);
	}

	@Test
	void testEquality_CentimeterToINCHES_EquivalentValue() {
		Length q1 = new Length(1.0, Unit.CENTIMETERS);
		Length q2 = new Length(0.393701, Unit.INCHES);
		assertEquals(q1, q2);
	}

	@Test
	void testEquality_MultiUnit_TransitiveProperty() {
		Length YARDS = new Length(1.0, Unit.YARDS);
		Length feet = new Length(3.0, Unit.FEET);
		Length INCHES = new Length(36.0, Unit.INCHES);

		assertTrue(YARDS.equals(feet));
		assertTrue(feet.equals(INCHES));
		assertTrue(YARDS.equals(INCHES));
	}

	@Test
	void testConversion_FeetToINCHES() {
		assertEquals(QuantityMeasurementApp.demonstrateLengthConversion(1.0, Unit.FEET, Unit.INCHES),
				new Length(12.0, Unit.INCHES));
	}

	@Test
	void testConversion_INCHESToFeet() {
		assertEquals(QuantityMeasurementApp.demonstrateLengthConversion(24.0, Unit.INCHES, Unit.FEET),
				new Length(2.0, Unit.FEET));
	}

	@Test
	void testConversion_YARDSsToINCHESes() {
		assertEquals(QuantityMeasurementApp.demonstrateLengthConversion(1.0, Unit.YARDS, Unit.INCHES),
				new Length(36.0, Unit.INCHES));
	}

	@Test
	void testConversion_INCHESesToYARDSs() {
		assertEquals(QuantityMeasurementApp.demonstrateLengthConversion(72.0, Unit.INCHES, Unit.YARDS),
				new Length(2.0, Unit.YARDS));
	}

	@Test
	void testConversion_FeatToYARDS() {
		assertEquals(QuantityMeasurementApp.demonstrateLengthConversion(6.0, Unit.FEET, Unit.YARDS),
				new Length(2.0, Unit.YARDS));
	}

	@Test
	void testConversion_RoundTrip_PreservesValue() {
		Length original = new Length(5.0, Length.Unit.FEET);
		Length converted = QuantityMeasurementApp.demonstrateLengthConversion(original, Length.Unit.INCHES);
		Length back = QuantityMeasurementApp.demonstrateLengthConversion(converted, Length.Unit.FEET);
		assertEquals(original, back);
	}

	@Test
	void testConversion_ZeroValue() {
		assertEquals(QuantityMeasurementApp.demonstrateLengthConversion(0.0, Unit.FEET, Unit.INCHES),
				new Length(0.0, Unit.INCHES));
	}

	@Test
	void testConversion_NegativeValue() {
		Length result = QuantityMeasurementApp.demonstrateLengthConversion(-1.0, Length.Unit.FEET, Length.Unit.INCHES);

		assertEquals(new Length(-12.0, Length.Unit.INCHES), result);
	}

	@Test
	void testConversion_InvalidUnit_Throws() {
		assertThrows(IllegalArgumentException.class,
				() -> QuantityMeasurementApp.demonstrateLengthConversion(1.0, null, Unit.INCHES));
	}

	@Test
	void testConversion_NaNOrInfinite_Throws() {
		assertThrows(IllegalArgumentException.class,
				() -> QuantityMeasurementApp.demonstrateLengthConversion(Double.NaN, Unit.FEET, Unit.INCHES));

		assertThrows(IllegalArgumentException.class, () -> QuantityMeasurementApp
				.demonstrateLengthConversion(Double.POSITIVE_INFINITY, Unit.FEET, Unit.INCHES));

		assertThrows(IllegalArgumentException.class, () -> QuantityMeasurementApp
				.demonstrateLengthConversion(Double.NEGATIVE_INFINITY, Unit.FEET, Unit.INCHES));
	}

	@Test
	void testConversion_PrecisionTolerance() {
		Length result = QuantityMeasurementApp.demonstrateLengthConversion(1.0, Unit.FEET, Unit.INCHES);
		double expected = 12.0;
		assertEquals(result, new Length(expected, Unit.INCHES));
	}

	@Test
	void testAddition_SameUnit_FeetPlusFeet() {
		Length l1 = new Length(1.0, Unit.FEET);
		Length l2 = new Length(2.0, Unit.FEET);
		assertEquals(new Length(3.0, Unit.FEET), l1.add(l2));
	}

	@Test
	void testAddition_SameUnit_InchPlusInch() {
		Length l1 = new Length(6.0, Unit.INCHES);
		Length l2 = new Length(6.0, Unit.INCHES);
		assertEquals(new Length(12.0, Unit.INCHES), l1.add(l2));
	}

	@Test
	void testAddition_CrossUnit_FeetPlusInches() {
		Length l1 = new Length(1.0, Unit.FEET);
		Length l2 = new Length(12.0, Unit.INCHES);
		assertEquals(new Length(2.0, Unit.FEET), l1.add(l2));
	}

	@Test
	void testAddition_CrossUnit_InchPlusFeet() {
		Length l1 = new Length(12.0, Unit.INCHES);
		Length l2 = new Length(1.0, Unit.FEET);
		assertEquals(new Length(24.0, Unit.INCHES), l1.add(l2));
	}

	@Test
	void testAddition_CrossUnit_YardPlusFeet() {
		Length l1 = new Length(1.0, Unit.YARDS);
		Length l2 = new Length(3.0, Unit.FEET);
		assertEquals(new Length(2.0, Unit.YARDS), l1.add(l2));
	}

	@Test
	void testAddition_CrossUnit_CentimeterPlusInch() {
		Length l1 = new Length(2.54, Unit.CENTIMETERS);
		Length l2 = new Length(1.0, Unit.INCHES);
		assertEquals(new Length(5.08, Unit.CENTIMETERS), l1.add(l2));
	}

	@Test
	void testAddition_Commutativity() {
		Length l1 = new Length(1.0, Unit.FEET);
		Length l2 = new Length(12.0, Unit.INCHES);
		assertEquals(l1.add(l2), l2.add(l1));
	}

	@Test
	void testAddition_WithZero() {
		Length l1 = new Length(5.0, Unit.FEET);
		Length l2 = new Length(0.0, Unit.INCHES);
		assertEquals(new Length(5.0, Unit.FEET), l1.add(l2));
	}

	@Test
	void testAddition_NegativeValues() {
		Length l1 = new Length(5.0, Unit.FEET);
		Length l2 = new Length(-2.0, Unit.FEET);
		assertEquals(new Length(3.0, Unit.FEET), l1.add(l2));
	}

	@Test
	void testAddition_NullSecondOperand() {
		Length l1 = new Length(1.0, Unit.FEET);
		assertThrows(IllegalArgumentException.class, () -> l1.add(null));
	}

	@Test
	void testAddition_LargeValues() {
		Length l1 = new Length(1e6, Unit.FEET);
		Length l2 = new Length(1e6, Unit.FEET);
		assertEquals(new Length(2e6, Unit.FEET), l1.add(l2));
	}

	@Test
	void testAddition_SmallValues() {
		Length l1 = new Length(0.001, Unit.FEET);
		Length l2 = new Length(0.002, Unit.FEET);
		assertEquals(new Length(0.003, Unit.FEET), l1.add(l2));
	}

	@Test
	void testAddition_ExplicitTargetUnit_Feet() {
		Length l1 = new Length(1.0, Unit.FEET);
		Length l2 = new Length(12.0, Unit.INCHES);
		assertEquals(new Length(2.0, Unit.FEET), l1.add(l2, Unit.FEET));
	}

	@Test
	void testAddition_ExplicitTargetUnit_Inches() {
		Length l1 = new Length(1.0, Unit.FEET);
		Length l2 = new Length(12.0, Unit.INCHES);
		assertEquals(new Length(24.0, Unit.INCHES), l1.add(l2, Unit.INCHES));
	}

	@Test
	void testAddition_ExplicitTargetUnit_Yards() {
		Length l1 = new Length(1.0, Unit.FEET);
		Length l2 = new Length(12.0, Unit.INCHES);
		Length result = l1.add(l2, Unit.YARDS);
		assertEquals(0.667, result.getValue(),EPSILON);
		assertEquals(Unit.YARDS, result.getUnit());
	}

	@Test
	void testAddition_ExplicitTargetUnit_Centimeters() {
		Length l1 = new Length(1.0, Unit.INCHES);
		Length l2 = new Length(1.0, Unit.INCHES);
		Length result = l1.add(l2, Unit.CENTIMETERS);
		assertEquals(5.08, result.getValue(), EPSILON);
		assertEquals(Unit.CENTIMETERS, result.getUnit());
	}

	@Test
	void testAddition_ExplicitTargetUnit_SameAsFirstOperand() {
		Length l1 = new Length(2.0, Unit.YARDS);
		Length l2 = new Length(3.0, Unit.FEET);
		assertEquals(new Length(3.0, Unit.YARDS), l1.add(l2, Unit.YARDS));
	}

	@Test
	void testAddition_ExplicitTargetUnit_SameAsSecondOperand() {
		Length l1 = new Length(2.0, Unit.YARDS);
		Length l2 = new Length(3.0, Unit.FEET);
		assertEquals(new Length(9.0, Unit.FEET), l1.add(l2, Unit.FEET));
	}

	@Test
	void testAddition_ExplicitTargetUnit_Commutativity() {
		Length l1 = new Length(1.0, Unit.FEET);
		Length l2 = new Length(12.0, Unit.INCHES);
		Length result1 = l1.add(l2, Unit.YARDS);
		Length result2 = l2.add(l1, Unit.YARDS);
		assertEquals(result1, result2);
	}

	@Test
	void testAddition_ExplicitTargetUnit_WithZero() {
		Length l1 = new Length(5.0, Unit.FEET);
		Length l2 = new Length(0.0, Unit.INCHES);
		assertEquals(1.667, l1.add(l2, Unit.YARDS).getValue(),EPSILON);
	}

	@Test
	void testAddition_ExplicitTargetUnit_NegativeValues() {
		Length l1 = new Length(5.0, Unit.FEET);
		Length l2 = new Length(-2.0, Unit.FEET);
		Length result = l1.add(l2, Unit.INCHES);
		assertEquals(36.0, result.getValue());
		assertEquals(Unit.INCHES, result.getUnit());
	}

	@Test
	void testAddition_ExplicitTargetUnit_NullTargetUnit() {
		Length l1 = new Length(1.0, Unit.FEET);
		Length l2 = new Length(12.0, Unit.INCHES);
		assertThrows(IllegalArgumentException.class, () -> l1.add(l2, null));
	}

	@Test
	void testAddition_ExplicitTargetUnit_LargeToSmallScale() {
		Length l1 = new Length(1000.0, Unit.FEET);
		Length l2 = new Length(500.0, Unit.FEET);
		assertEquals(18000.0, l1.add(l2, Unit.INCHES).getValue());
	}

	@Test
	void testAddition_ExplicitTargetUnit_SmallToLargeScale() {
		Length l1 = new Length(12.0, Unit.INCHES);
		Length l2 = new Length(12.0, Unit.INCHES);
		assertEquals(0.667, l1.add(l2, Unit.YARDS).getValue(), EPSILON);
	}

	@Test
	void testAddition_ExplicitTargetUnit_AllUnitCombinations() {
		Unit[] units = Unit.values();
		for (Unit u1 : units) {
			for (Unit u2 : units) {
				for (Unit target : units) {
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
		Length l1 = new Length(2.54, Unit.CENTIMETERS);
		Length l2 = new Length(1.0, Unit.INCHES);
		assertEquals(5.08, l1.add(l2, Unit.CENTIMETERS).getValue(),EPSILON);
	}

}
