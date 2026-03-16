package org.quantitymeasurement.app.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.quantitymeasurement.app.entity.units.LengthUnit;
import org.quantitymeasurement.app.entity.units.TemperatureUnit;
import org.quantitymeasurement.app.entity.units.VolumeUnit;
import org.quantitymeasurement.app.entity.units.WeightUnit;

class QuantityEntityTest {

    /* =========================================================
       EQUALITY TESTS
       ========================================================= */

    @Test
    void testLengthEquality() {
        Quantity<LengthUnit> q1 = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> q2 = new Quantity<>(12.0, LengthUnit.INCH);
        assertTrue(q1.equals(q2));
    }

    @Test
    void testWeightEquality() {
        Quantity<WeightUnit> q1 = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> q2 = new Quantity<>(1000.0, WeightUnit.GRAM);
        assertTrue(q1.equals(q2));
    }

    @Test
    void testVolumeEquality() {
        Quantity<VolumeUnit> q1 = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> q2 = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
        assertTrue(q1.equals(q2));
    }

    @Test
    void testTemperatureEquality() {
        Quantity<TemperatureUnit> q1 = new Quantity<>(0.0, TemperatureUnit.CELSIUS);
        Quantity<TemperatureUnit> q2 = new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT);
        assertTrue(q1.equals(q2));
    }

    @Test
    void testCrossCategoryEquality() {
        Quantity<LengthUnit> length = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<WeightUnit> weight = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        assertFalse(length.equals(weight));
    }

    /* =========================================================
       CONVERSION TESTS
       ========================================================= */

    @Test
    void testLengthConversion() {
        Quantity<LengthUnit> q = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> result = q.convertTo(LengthUnit.INCH);

        assertEquals(12.0, result.getValue());
        assertEquals(LengthUnit.INCH, result.getUnit());
    }

    @Test
    void testWeightConversion() {
        Quantity<WeightUnit> q = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> result = q.convertTo(WeightUnit.GRAM);

        assertEquals(1000.0, result.getValue());
    }

    @Test
    void testVolumeConversion() {
        Quantity<VolumeUnit> q = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> result = q.convertTo(VolumeUnit.MILLILITRE);

        assertEquals(1000.0, result.getValue());
    }

    @Test
    void testTemperatureConversion() {
        Quantity<TemperatureUnit> result =
                new Quantity<>(100.0, TemperatureUnit.CELSIUS)
                        .convertTo(TemperatureUnit.FAHRENHEIT);

        assertEquals(212.0, result.getValue());
    }

    /* =========================================================
       ADDITION TESTS
       ========================================================= */

    @Test
    void testLengthAddition() {
        Quantity<LengthUnit> result =
                new Quantity<>(1.0, LengthUnit.FEET)
                        .add(new Quantity<>(12.0, LengthUnit.INCH));

        assertEquals(2.0, result.getValue());
    }

    @Test
    void testWeightAddition() {
        Quantity<WeightUnit> result =
                new Quantity<>(1.0, WeightUnit.KILOGRAM)
                        .add(new Quantity<>(1000.0, WeightUnit.GRAM));

        assertEquals(2.0, result.getValue());
    }

    @Test
    void testVolumeAddition() {
        Quantity<VolumeUnit> result =
                new Quantity<>(1.0, VolumeUnit.LITRE)
                        .add(new Quantity<>(1000.0, VolumeUnit.MILLILITRE));

        assertEquals(2.0, result.getValue());
    }

    @Test
    void testAdditionWithTargetUnit() {
        Quantity<LengthUnit> result =
                new Quantity<>(1.0, LengthUnit.FEET)
                        .add(new Quantity<>(12.0, LengthUnit.INCH), LengthUnit.INCH);

        assertEquals(24.0, result.getValue());
    }

    /* =========================================================
       SUBTRACTION TESTS
       ========================================================= */

    @Test
    void testLengthSubtraction() {
        Quantity<LengthUnit> result =
                new Quantity<>(10.0, LengthUnit.FEET)
                        .subtract(new Quantity<>(6.0, LengthUnit.INCH));

        assertEquals(9.5, result.getValue());
    }

    @Test
    void testSubtractionWithTargetUnit() {
        Quantity<LengthUnit> result =
                new Quantity<>(10.0, LengthUnit.FEET)
                        .subtract(new Quantity<>(6.0, LengthUnit.INCH), LengthUnit.INCH);

        assertEquals(114.0, result.getValue());
    }

    /* =========================================================
       DIVISION TESTS
       ========================================================= */

    @Test
    void testLengthDivision() {
        double result =
                new Quantity<>(24.0, LengthUnit.INCH)
                        .divide(new Quantity<>(2.0, LengthUnit.FEET));

        assertEquals(1.0, result);
    }

    @Test
    void testWeightDivision() {
        double result =
                new Quantity<>(2000.0, WeightUnit.GRAM)
                        .divide(new Quantity<>(1.0, WeightUnit.KILOGRAM));

        assertEquals(2.0, result);
    }

    /* =========================================================
       VALIDATION TESTS
       ========================================================= */

    @Test
    void testConstructorNullUnit() {
        assertThrows(IllegalArgumentException.class,
                () -> new Quantity<>(1.0, null));
    }

    @Test
    void testConstructorInvalidValue() {
        assertThrows(IllegalArgumentException.class,
                () -> new Quantity<>(Double.NaN, LengthUnit.FEET));
    }

    @Test
    void testDivideByZero() {
        assertThrows(ArithmeticException.class,
                () -> new Quantity<>(10.0, LengthUnit.FEET)
                        .divide(new Quantity<>(0.0, LengthUnit.FEET)));
    }

    /* =========================================================
       TEMPERATURE RULES
       ========================================================= */

    @Test
    void testTemperatureUnsupportedOperation() {
        assertThrows(UnsupportedOperationException.class,
                () -> new Quantity<>(100.0, TemperatureUnit.CELSIUS)
                        .add(new Quantity<>(50.0, TemperatureUnit.CELSIUS)));
    }

    /* =========================================================
       IMMUTABILITY
       ========================================================= */

    @Test
    void testImmutabilityAfterAddition() {
        Quantity<LengthUnit> original =
                new Quantity<>(10.0, LengthUnit.FEET);

        Quantity<LengthUnit> result =
                original.add(new Quantity<>(5.0, LengthUnit.FEET));

        assertNotSame(original, result);
    }

    /* =========================================================
       ROUNDING
       ========================================================= */

    @Test
    void testRoundingBehavior() {
        Quantity<LengthUnit> result =
                new Quantity<>(1.234, LengthUnit.FEET)
                        .add(new Quantity<>(1.234, LengthUnit.FEET));

        assertEquals(2.47, result.getValue());
    }
}