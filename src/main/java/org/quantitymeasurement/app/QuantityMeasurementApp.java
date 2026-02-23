package org.quantitymeasurement.app;

public class QuantityMeasurementApp {
	public static boolean demonstrateLengthEquality(Length length1,Length length2) {
		return length1.equals(length2);
	}

	public static boolean demonstrateLengthComparision(double value1, LengthUnit unit1, double value2, LengthUnit unit2) {
		Length length1 = new Length(value1,unit1);
		Length length2 = new Length(value2,unit2);
		return length1.equals(length2);
	}
	
	public static Length demonstrateLengthConversion(double value, LengthUnit fromUnit, LengthUnit toUnit) {
		if (fromUnit == null || toUnit == null) {
            throw new IllegalArgumentException("LengthUnit cannot be null");
        }
        if (Double.isNaN(value) || Double.isInfinite(value)) {
            throw new IllegalArgumentException("Value must be a finite number");
        }
        Length length = new Length(value,fromUnit);
        return length.convertTo(toUnit);
	}
	
	public static Length demonstrateLengthConversion(Length length, LengthUnit toUnit) {
    	if(length == null) {
    		throw new IllegalArgumentException("Length cannot be null");
    	}
        return length.convertTo(toUnit);
    }
	
	public static Length demonstrateLengthAddition(Length length1, Length length2) {
		return length1.add(length2);
	}
	
	public static Length demonstrateLengthAddition(Length length1, Length length2,LengthUnit LengthUnit) {
		return length1.add(length2,LengthUnit);
	}
	
	public static void main(String[] args) {

		Length q1 = new Length(1.0, LengthUnit.FEET);
		Length q2 = new Length(12.0, LengthUnit.INCHES);

		System.out.println(demonstrateLengthEquality(q1, q2));

		System.out.println(demonstrateLengthConversion(1.0, LengthUnit.FEET, LengthUnit.INCHES));
		System.out.println(demonstrateLengthConversion(3.0, LengthUnit.YARDS, LengthUnit.FEET));
		
		System.out.println(demonstrateLengthAddition(q1, q2, LengthUnit.FEET));
		System.out.println(demonstrateLengthAddition(q2, q1, LengthUnit.FEET));
		
		QuantityWeight w1 = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
		QuantityWeight w2 = new QuantityWeight(1000.0, WeightUnit.GRAM);

		System.out.println(w1.equals(w2));

		QuantityWeight converted = w1.convertTo(WeightUnit.POUND);
		System.out.println(converted);

		QuantityWeight sum = w1.add(w2);
		System.out.println(sum);
		
	}
}
