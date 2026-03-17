package org.quantitymeasurement.app.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.quantitymeasurement.app.controller.QuantityController;
import org.quantitymeasurement.app.dto.QuantityRequestDto;
import org.quantitymeasurement.app.repository.QuantityMeasurementCacheRepository;
import org.quantitymeasurement.app.service.QuantityServiceImpl;

class QuantityIntegrationTest {

	QuantityController controller = new QuantityController(
			new QuantityServiceImpl(QuantityMeasurementCacheRepository.getInstance()));

    /* =========================================================
       CONVERSION FLOW
       ========================================================= */

    @Test
    void testTemperatureConversionFlow() {
        var result = controller.convert(
                new QuantityRequestDto(100.0, "CELSIUS"),
                "FAHRENHEIT"
        );

        assertEquals(212.0, result.getNumeric());
    }

    @Test
    void testLengthConversionFlow() {
        var result = controller.convert(
                new QuantityRequestDto(1.0, "FEET"),
                "INCH"
        );

        assertEquals(12.0, result.getNumeric());
    }

    @Test
    void testWeightConversionFlow() {
        var result = controller.convert(
                new QuantityRequestDto(1.0, "KILOGRAM"),
                "GRAM"
        );

        assertEquals(1000.0, result.getNumeric());
    }

    @Test
    void testVolumeConversionFlow() {
        var result = controller.convert(
                new QuantityRequestDto(1.0, "LITRE"),
                "MILLILITRE"
        );

        assertEquals(1000.0, result.getNumeric());
    }

    /* =========================================================
       ADDITION FLOW
       ========================================================= */

    @Test
    void testLengthAdditionFlow() {
        var result = controller.add(
                new QuantityRequestDto(1.0, "FEET"),
                new QuantityRequestDto(12.0, "INCH")
        );

        assertEquals(2.0, result.getNumeric());
    }

    @Test
    void testWeightAdditionFlow() {
        var result = controller.add(
                new QuantityRequestDto(1.0, "KILOGRAM"),
                new QuantityRequestDto(1000.0, "GRAM")
        );

        assertEquals(2.0, result.getNumeric());
    }

    @Test
    void testVolumeAdditionFlow() {
        var result = controller.add(
                new QuantityRequestDto(1.0, "LITRE"),
                new QuantityRequestDto(1000.0, "MILLILITRE")
        );

        assertEquals(2.0, result.getNumeric());
    }

    /* =========================================================
       SUBTRACTION FLOW
       ========================================================= */

    @Test
    void testLengthSubtractionFlow() {
        var result = controller.subtract(
                new QuantityRequestDto(10.0, "FEET"),
                new QuantityRequestDto(6.0, "INCH")
        );

        assertEquals(9.5, result.getNumeric());
    }

    /* =========================================================
       DIVISION FLOW
       ========================================================= */

    @Test
    void testLengthDivisionFlow() {
        double result = controller.divide(
                new QuantityRequestDto(24.0, "INCH"),
                new QuantityRequestDto(2.0, "FEET")
        );

        assertEquals(1.0, result);
    }

    /* =========================================================
       TEMPERATURE RULE FLOW
       ========================================================= */

    @Test
    void testTemperatureAdditionBlockedFlow() {
        assertThrows(UnsupportedOperationException.class,
                () -> controller.add(
                        new QuantityRequestDto(100.0, "CELSIUS"),
                        new QuantityRequestDto(50.0, "CELSIUS")
                ));
    }
}