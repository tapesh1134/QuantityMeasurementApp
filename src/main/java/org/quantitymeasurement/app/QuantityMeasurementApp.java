package org.quantitymeasurement.app;

public class QuantityMeasurementApp {

	public static <U extends IMeasurable> void demonstrateEquality(Quantity<U> q1, Quantity<U> q2) {
		System.out.println("Input: " + q1 + " equals " + q2 + " → Output: " + q1.equals(q2));
	}

	public static <U extends IMeasurable> void demonstrateConversion(Quantity<U> q, U targetUnit) {
		System.out.println("Input: " + q + " convertTo " + targetUnit + " → Output: " + q.convertTo(targetUnit));
	}

	public static <U extends IMeasurable> void demonstrateAddition(Quantity<U> q1, Quantity<U> q2, U targetUnit) {
		System.out.println("Input: " + q1 + " add " + q2 + " → Output: " + q1.add(q2, targetUnit));
	}

	public static <U extends IMeasurable> void demonstrateAddition(Quantity<U> q1, Quantity<U> q2) {
		System.out.println("Input: " + q1 + " add " + q2 + " → Output: " + q1.add(q2, q1.getUnit()));
	}

	public static <U extends IMeasurable> void demonstrateSubtraction(Quantity<U> q1, Quantity<U> q2) {
		System.out.println("Input: " + q1 + " subtract " + q2 + " → Output: " + q1.subtract(q2));
	}

	public static <U extends IMeasurable> void demonstrateSubtraction(Quantity<U> q1, Quantity<U> q2, U targetUnit) {

		System.out.println("Input: " + q1 + " subtract " + q2 + " → Output: " + q1.subtract(q2, targetUnit));
	}

	public static <U extends IMeasurable> void demonstrateDivision(Quantity<U> q1, Quantity<U> q2) {
		System.out.println("Input: " + q1 + " divide by " + q2 + " → Output: " + q1.divide(q2));
	}

	public static void main(String[] args) {
		Quantity<LengthUnit> l1 = new Quantity<>(1.0, LengthUnit.FEET);
		Quantity<LengthUnit> l2 = new Quantity<>(12.0, LengthUnit.INCHES);
		demonstrateEquality(l1, l2);
		demonstrateConversion(l1, LengthUnit.INCHES);
		demonstrateAddition(l1, l2, LengthUnit.FEET);
		Quantity<WeightUnit> w1 = new Quantity<>(1.0, WeightUnit.KILOGRAM);
		Quantity<WeightUnit> w2 = new Quantity<>(1000.0, WeightUnit.GRAM);
		demonstrateEquality(w1, w2);
		demonstrateConversion(w1, WeightUnit.GRAM);
		demonstrateAddition(w1, w2, WeightUnit.KILOGRAM);
		System.out.println();
		System.out.println("Cross-category comparison: " + l1.equals((Object) w1));

		Quantity<VolumeUnit> v1 = new Quantity<VolumeUnit>(1.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> v2 = new Quantity<VolumeUnit>(1000.0, VolumeUnit.MILLILITRE);
		demonstrateEquality(v1, v2);
		demonstrateConversion(v1, VolumeUnit.MILLILITRE);
		demonstrateAddition(v1, v2, VolumeUnit.LITRE);
		demonstrateAddition(v1, v2);

		System.out.println("\n----- SUBTRACTION (IMPLICIT TARGET UNIT) -----");
		demonstrateSubtraction(new Quantity<>(10.0, LengthUnit.FEET), new Quantity<>(6.0, LengthUnit.INCHES));
		demonstrateSubtraction(new Quantity<>(10.0, WeightUnit.KILOGRAM), new Quantity<>(5000.0, WeightUnit.GRAM));
		demonstrateSubtraction(new Quantity<>(5.0, VolumeUnit.LITRE), new Quantity<>(500.0, VolumeUnit.MILLILITRE));

		System.out.println("\n----- SUBTRACTION (EXPLICIT TARGET UNIT) -----");
		demonstrateSubtraction(new Quantity<>(10.0, LengthUnit.FEET), new Quantity<>(6.0, LengthUnit.INCHES),
				LengthUnit.INCHES);
		demonstrateSubtraction(new Quantity<>(10.0, WeightUnit.KILOGRAM), new Quantity<>(5000.0, WeightUnit.GRAM),
				WeightUnit.GRAM);
		demonstrateSubtraction(new Quantity<>(5.0, VolumeUnit.LITRE), new Quantity<>(2.0, VolumeUnit.LITRE),
				VolumeUnit.MILLILITRE);

		System.out.println("\n----- SUBTRACTION EDGE CASES -----");
		demonstrateSubtraction(new Quantity<>(5.0, LengthUnit.FEET), new Quantity<>(10.0, LengthUnit.FEET));
		demonstrateSubtraction(new Quantity<>(2.0, WeightUnit.KILOGRAM), new Quantity<>(5.0, WeightUnit.KILOGRAM));
		demonstrateSubtraction(new Quantity<>(10.0, LengthUnit.FEET), new Quantity<>(120.0, LengthUnit.INCHES));
		demonstrateSubtraction(new Quantity<>(1.0, VolumeUnit.LITRE), new Quantity<>(1000.0, VolumeUnit.MILLILITRE));

		System.out.println("\n----- DIVISION OPERATIONS -----");
		demonstrateDivision(new Quantity<>(10.0, LengthUnit.FEET), new Quantity<>(2.0, LengthUnit.FEET));
		demonstrateDivision(new Quantity<>(10.0, LengthUnit.FEET), new Quantity<>(5.0, LengthUnit.FEET));
		demonstrateDivision(new Quantity<>(24.0, LengthUnit.INCHES), new Quantity<>(2.0, LengthUnit.FEET));
		demonstrateDivision(new Quantity<>(10.0, WeightUnit.KILOGRAM), new Quantity<>(5.0, WeightUnit.KILOGRAM));
		demonstrateDivision(new Quantity<>(5.0, VolumeUnit.LITRE), new Quantity<>(10.0, VolumeUnit.LITRE));

		System.out.println("\n----- DIVISION (CROSS UNIT) -----");
		demonstrateDivision(new Quantity<>(12.0, LengthUnit.INCHES), new Quantity<>(1.0, LengthUnit.FEET));
		demonstrateDivision(new Quantity<>(2000.0, WeightUnit.GRAM), new Quantity<>(1.0, WeightUnit.KILOGRAM));
		demonstrateDivision(new Quantity<>(1000.0, VolumeUnit.MILLILITRE), new Quantity<>(1.0, VolumeUnit.LITRE));
	}
}
