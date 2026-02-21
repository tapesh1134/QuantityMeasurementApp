package org.quantitymeasurement.app;


public class Length {
    private static final double EPSILON = 0.0001;
    private final double value;
    private final LengthUnit LengthUnit;
    
    public LengthUnit getUnit() {
		return LengthUnit;
	}

    public double getValue() {
		return value;
	}
    
    public Length(double value, LengthUnit LengthUnit) {
        if (LengthUnit == null) {
            throw new IllegalArgumentException("LengthUnit cannot be null");
        }
        if (Double.isNaN(value) || Double.isInfinite(value)) {
            throw new IllegalArgumentException("Value must be a finite number");
        }

        this.value = value;
        this.LengthUnit = LengthUnit;
    }

    private double toInches() {
        return LengthUnit.toInches(value);
    }

    public Length convertTo(LengthUnit to) {
        double inches = this.LengthUnit.toInches(value);
        return new Length(inches / to.getConversionFactor(),to);
    }
    
    public Length add(Length length2) {
    	if (LengthUnit == null || length2 == null) {
            throw new IllegalArgumentException("LengthUnit cannot be null");
        }
    	double totalValue = this.value + length2.convertTo(this.LengthUnit).value;
    	Length sumLength = new Length(totalValue, this.LengthUnit);
    	return sumLength;
    }
    
    public Length add(Length length2, LengthUnit LengthUnit) {
    	if (LengthUnit == null || length2 == null) {
            throw new IllegalArgumentException("LengthUnit cannot be null");
        }
    	double totalValue = this.value + length2.convertTo(this.LengthUnit).value;
    	Length sumLength = new Length(totalValue, this.LengthUnit);
    	return sumLength.convertTo(LengthUnit);
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
        return  value + ", " + LengthUnit;
    }
}