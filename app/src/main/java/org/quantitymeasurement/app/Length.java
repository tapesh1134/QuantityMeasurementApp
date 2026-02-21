package org.quantitymeasurement.app;

public class Length {
    private static final double EPSILON = 0.0001;
    private final double value;
    private final Unit unit;

    public enum Unit {
        FEET(12),
        INCHES(1),
        YARDS(36),
        CENTIMETERS(0.393701);

        private final double conversionFactorToInches;

        Unit(double conversionFactorToInches) {
            this.conversionFactorToInches = conversionFactorToInches;
        }

        public double toInches(double value) {
            return value * conversionFactorToInches;
        }

        public double getConversionFactor() {
            return conversionFactorToInches;
        }
    }

    public double getValue() {
		return value;
	}
    
    public Length(double value, Unit unit) {
        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }
        if (Double.isNaN(value) || Double.isInfinite(value)) {
            throw new IllegalArgumentException("Value must be a finite number");
        }

        this.value = value;
        this.unit = unit;
    }

    private double toInches() {
        return unit.toInches(value);
    }

    public Length convertTo(Unit to) {
        double inches = this.unit.toInches(value);
        return new Length(inches / to.getConversionFactor(),to);
    }
    
    public Length add(Length length2) {
    	if (unit == null || length2 == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }
    	double totalValue = this.value + length2.convertTo(this.unit).value;
    	Length sumLength = new Length(totalValue, this.unit);
    	return sumLength;
    }
    
    public Length add(Length length2, Unit unit) {
    	if (unit == null || length2 == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }
    	double totalValue = this.value + length2.convertTo(this.unit).value;
    	Length sumLength = new Length(totalValue, this.unit);
    	return sumLength.convertTo(unit);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Length)) return false;

        Length other = (Length) obj;
        return Math.abs(this.toInches() - other.toInches()) < EPSILON;
    }

    @Override
    public String toString() {
        return  value + ", " + unit;
    }
}