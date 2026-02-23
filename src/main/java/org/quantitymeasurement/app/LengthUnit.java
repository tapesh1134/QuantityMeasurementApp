package org.quantitymeasurement.app;

public enum LengthUnit {
    FEET(12),
    INCHES(1),
    YARDS(36),
    CENTIMETERS(0.393701);

    private final double conversionFactorToInches;

    LengthUnit(double conversionFactorToInches) {
        this.conversionFactorToInches = conversionFactorToInches;
    }

    public double toInches(double value) {
        return value * conversionFactorToInches;
    }

    public double getConversionFactor() {
        return conversionFactorToInches;
    }
}