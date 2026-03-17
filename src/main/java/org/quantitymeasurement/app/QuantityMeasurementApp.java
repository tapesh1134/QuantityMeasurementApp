package org.quantitymeasurement.app;

import org.quantitymeasurement.app.controller.QuantityController;
import org.quantitymeasurement.app.dto.QuantityRequestDto;
import org.quantitymeasurement.app.repository.IQuantityMeasurementRepository;
import org.quantitymeasurement.app.repository.QuantityMeasurementCacheRepository;
import org.quantitymeasurement.app.service.QuantityServiceImpl;

public class QuantityMeasurementApp {

	public static void main(String[] args) {
		IQuantityMeasurementRepository repository = QuantityMeasurementCacheRepository.getInstance();
		QuantityServiceImpl service = new QuantityServiceImpl(repository);
		QuantityController controller = new QuantityController(service);

		System.out.println("----- LENGTH OPERATIONS -----");

		System.out.println(
				controller.checkEquality(new QuantityRequestDto(1.0, "FEET"), new QuantityRequestDto(12.0, "INCH")));

		System.out.println(controller.convert(new QuantityRequestDto(1.0, "FEET"), "INCH"));

		System.out.println(
				controller.add(new QuantityRequestDto(1.0, "FEET"), new QuantityRequestDto(12.0, "INCH"), "FEET"));

		System.out.println("\n----- WEIGHT OPERATIONS -----");

		System.out.println(controller.checkEquality(new QuantityRequestDto(1.0, "KILOGRAM"),
				new QuantityRequestDto(1000.0, "GRAM")));

		System.out.println(controller.convert(new QuantityRequestDto(1.0, "KILOGRAM"), "GRAM"));

		System.out.println(controller.add(new QuantityRequestDto(1.0, "KILOGRAM"),
				new QuantityRequestDto(1000.0, "GRAM"), "KILOGRAM"));

		System.out.println("\n----- VOLUME OPERATIONS -----");

		System.out.println(controller.checkEquality(new QuantityRequestDto(1.0, "LITRE"),
				new QuantityRequestDto(1000.0, "MILLILITRE")));

		System.out.println(controller.convert(new QuantityRequestDto(1.0, "LITRE"), "MILLILITRE"));

		System.out.println(controller.add(new QuantityRequestDto(1.0, "LITRE"),
				new QuantityRequestDto(1000.0, "MILLILITRE"), "LITRE"));

		System.out.println("\n----- SUBTRACTION -----");

		System.out.println(
				controller.subtract(new QuantityRequestDto(10.0, "FEET"), new QuantityRequestDto(6.0, "INCH"), "FEET"));

		System.out.println("\n----- DIVISION -----");

		System.out
				.println(controller.divide(new QuantityRequestDto(24.0, "INCH"), new QuantityRequestDto(2.0, "FEET")));

		System.out.println("\n----- TEMPERATURE OPERATIONS -----");

		System.out.println(controller.checkEquality(new QuantityRequestDto(0.0, "CELSIUS"),
				new QuantityRequestDto(32.0, "FAHRENHEIT")));

		System.out.println(controller.convert(new QuantityRequestDto(100.0, "CELSIUS"), "FAHRENHEIT"));

		try {

			System.out.println(
					controller.add(new QuantityRequestDto(100.0, "CELSIUS"), new QuantityRequestDto(50.0, "CELSIUS")));

		} catch (UnsupportedOperationException e) {

			System.out.println("Temperature addition blocked → " + e.getMessage());
		}
	}
}