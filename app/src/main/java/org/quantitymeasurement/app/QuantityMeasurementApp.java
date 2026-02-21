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

		Length q3 = new Length(1.0, LengthUnit.YARDS);
		Length q4 = new Length(3.0, LengthUnit.FEET);

		System.out.println(demonstrateLengthEquality(q3, q4));

		Length q5 = new Length(1.0, LengthUnit.CENTIMETERS);
		Length q6 = new Length(0.393701, LengthUnit.INCHES);

		System.out.println(demonstrateLengthEquality(q5, q6));

		System.out.println(demonstrateLengthConversion(1.0, LengthUnit.FEET, LengthUnit.INCHES));
		System.out.println(demonstrateLengthConversion(3.0, LengthUnit.YARDS, LengthUnit.FEET));
		System.out.println(demonstrateLengthConversion(36.0, LengthUnit.INCHES, LengthUnit.YARDS));
		System.out.println(demonstrateLengthConversion(1.0, LengthUnit.CENTIMETERS, LengthUnit.INCHES));
		System.out.println(demonstrateLengthConversion(0.0, LengthUnit.FEET, LengthUnit.INCHES));
		
		System.out.println(demonstrateLengthAddition(q1, q2, LengthUnit.FEET));
		System.out.println(demonstrateLengthAddition(q2, q1, LengthUnit.FEET));
		System.out.println(demonstrateLengthAddition(q3, q4));
		System.out.println(demonstrateLengthAddition(q5, q6));
		System.out.println(demonstrateLengthAddition(q5, q6, LengthUnit.INCHES));
		
	}
}
