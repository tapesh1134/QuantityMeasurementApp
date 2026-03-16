
package org.quantitymeasurement.app.controller;

import org.quantitymeasurement.app.dto.QuantityRequestDto;
import org.quantitymeasurement.app.dto.QuantityResponseDto;
import org.quantitymeasurement.app.entity.IMeasurable;
import org.quantitymeasurement.app.entity.Quantity;
import org.quantitymeasurement.app.entity.units.LengthUnit;
import org.quantitymeasurement.app.entity.units.TemperatureUnit;
import org.quantitymeasurement.app.entity.units.VolumeUnit;
import org.quantitymeasurement.app.entity.units.WeightUnit;
import org.quantitymeasurement.app.service.QuantityService;

public class QuantityController {
	private QuantityService quantityService;

	public QuantityController(QuantityService quantityService) {
		this.quantityService = quantityService;
	}

	private IMeasurable resolveUnit(String unit) {

		try {
			return LengthUnit.valueOf(unit);
		} catch (Exception ignored) {
		}
		try {
			return WeightUnit.valueOf(unit);
		} catch (Exception ignored) {
		}
		try {
			return VolumeUnit.valueOf(unit);
		} catch (Exception ignored) {
		}
		try {
			return TemperatureUnit.valueOf(unit);
		} catch (Exception ignored) {
		}

		throw new IllegalArgumentException("Unknown unit: " + unit);
	}

	public String checkEquality(QuantityRequestDto q1, QuantityRequestDto q2) {

		IMeasurable unit1 = resolveUnit(q1.getUnit());
		IMeasurable unit2 = resolveUnit(q2.getUnit());

		Quantity<IMeasurable> left = new Quantity<>(q1.getValue(), unit1);
		Quantity<IMeasurable> right = new Quantity<>(q2.getValue(), unit2);

		boolean result = left.equals(right);

		return left + " equals " + right + " → " + result;
	}

	/*
	 * ========================================================= CONVERSION
	 * =========================================================
	 */

	public QuantityResponseDto convert(QuantityRequestDto req, String targetUnit) {

		IMeasurable source = resolveUnit(req.getUnit());
		IMeasurable target = resolveUnit(targetUnit);

		Quantity<IMeasurable> quantity = new Quantity<>(req.getValue(), source);

		Quantity<IMeasurable> result = quantityService.convert(quantity, target);

		return new QuantityResponseDto(result.toString(), result.getValue());
	}

	public QuantityResponseDto add(QuantityRequestDto q1, QuantityRequestDto q2, String targetUnit) {

		IMeasurable unit1 = resolveUnit(q1.getUnit());
		IMeasurable unit2 = resolveUnit(q2.getUnit());
		IMeasurable target = resolveUnit(targetUnit);

		Quantity<IMeasurable> left = new Quantity<>(q1.getValue(), unit1);
		Quantity<IMeasurable> right = new Quantity<>(q2.getValue(), unit2);

		Quantity<IMeasurable> result = quantityService.add(left, right, target);

		return new QuantityResponseDto(result.toString(), result.getValue());
	}

	public QuantityResponseDto add(QuantityRequestDto q1, QuantityRequestDto q2) {

		IMeasurable unit1 = resolveUnit(q1.getUnit());
		IMeasurable unit2 = resolveUnit(q2.getUnit());

		Quantity<IMeasurable> left = new Quantity<>(q1.getValue(), unit1);

		Quantity<IMeasurable> right = new Quantity<>(q2.getValue(), unit2);

		Quantity<IMeasurable> result = quantityService.add(left, right);

		return new QuantityResponseDto(result.toString(), result.getValue());
	}

	public QuantityResponseDto subtract(QuantityRequestDto q1, QuantityRequestDto q2, String targetUnit) {

		IMeasurable unit1 = resolveUnit(q1.getUnit());
		IMeasurable unit2 = resolveUnit(q2.getUnit());
		IMeasurable target = resolveUnit(targetUnit);

		Quantity<IMeasurable> left = new Quantity<>(q1.getValue(), unit1);
		Quantity<IMeasurable> right = new Quantity<>(q2.getValue(), unit2);

		Quantity<IMeasurable> result = quantityService.subtract(left, right, target);

		return new QuantityResponseDto(result.toString(), result.getValue());
	}

	public QuantityResponseDto subtract(QuantityRequestDto q1, QuantityRequestDto q2) {

		IMeasurable unit1 = resolveUnit(q1.getUnit());
		IMeasurable unit2 = resolveUnit(q2.getUnit());

		Quantity<IMeasurable> left = new Quantity<>(q1.getValue(), unit1);

		Quantity<IMeasurable> right = new Quantity<>(q2.getValue(), unit2);

		Quantity<IMeasurable> result = quantityService.subtract(left, right);

		return new QuantityResponseDto(result.toString(), result.getValue());
	}

	public double divide(QuantityRequestDto q1, QuantityRequestDto q2) {

		IMeasurable unit1 = resolveUnit(q1.getUnit());
		IMeasurable unit2 = resolveUnit(q2.getUnit());

		Quantity<IMeasurable> left = new Quantity<>(q1.getValue(), unit1);
		Quantity<IMeasurable> right = new Quantity<>(q2.getValue(), unit2);

		return quantityService.divide(left, right);
	}
}
