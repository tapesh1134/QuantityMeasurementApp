package org.quantitymeasurement.app;

public class QuantityMeasurementApp {
	public static void main(String[] args) {

		QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
		QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCH);

		System.out.println(q1 + " and " + q2);
		System.out.println("Equal? " + q1.equals(q2));
	}
}
