package org.quantitymeasurement.app;

public class QuantityMeasurementApp {

	public static void main(String[] args) {

		QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
		QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCH);

		System.out.println(q1.equals(q2));

		QuantityLength q3 = new QuantityLength(1.0, LengthUnit.YARD);
		QuantityLength q4 = new QuantityLength(3.0, LengthUnit.FEET);

		System.out.println(q3.equals(q4));

		QuantityLength q5 = new QuantityLength(1.0, LengthUnit.CENTIMETER);
		QuantityLength q6 = new QuantityLength(0.393701, LengthUnit.INCH);

		System.out.println(q5.equals(q6));

		System.out.println(QuantityLength.convert(1.0, LengthUnit.FEET, LengthUnit.INCH));
		System.out.println(QuantityLength.convert(3.0, LengthUnit.YARD, LengthUnit.FEET));
		System.out.println(QuantityLength.convert(36.0, LengthUnit.INCH, LengthUnit.YARD));
		System.out.println(QuantityLength.convert(1.0, LengthUnit.CENTIMETER, LengthUnit.INCH));
		System.out.println(QuantityLength.convert(0.0, LengthUnit.FEET, LengthUnit.INCH));
	}
}
