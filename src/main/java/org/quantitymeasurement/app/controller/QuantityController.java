package org.quantitymeasurement.app.controller;

import org.quantitymeasurement.app.dto.QuantityRequestDto;
import org.quantitymeasurement.app.dto.QuantityResponseDto;
import org.quantitymeasurement.app.dto.TwoQuantityRequestDto;
import org.quantitymeasurement.app.entity.IMeasurable;
import org.quantitymeasurement.app.entity.Quantity;
import org.quantitymeasurement.app.entity.units.*;
import org.quantitymeasurement.app.service.QuantityService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/quantity")
public class QuantityController {

	private final QuantityService quantityService;

	public QuantityController(QuantityService quantityService) {
		this.quantityService = quantityService;
	}

    /* =========================================================
       UNIT RESOLVER
       ========================================================= */

	private IMeasurable resolveUnit(String unit) {

		try { return LengthUnit.valueOf(unit); } catch (Exception ignored) {}
		try { return WeightUnit.valueOf(unit); } catch (Exception ignored) {}
		try { return VolumeUnit.valueOf(unit); } catch (Exception ignored) {}
		try { return TemperatureUnit.valueOf(unit); } catch (Exception ignored) {}

		throw new IllegalArgumentException("Unknown unit: " + unit);
	}

    /* =========================================================
       EQUALITY
       ========================================================= */

	@PostMapping("/equality")
	public String checkEquality(@RequestBody TwoQuantityRequestDto request) {

		QuantityRequestDto q1 = request.getQ1();
		QuantityRequestDto q2 = request.getQ2();

		Quantity<IMeasurable> left =
				new Quantity<>(q1.getValue(), resolveUnit(q1.getUnit()));

		Quantity<IMeasurable> right =
				new Quantity<>(q2.getValue(), resolveUnit(q2.getUnit()));

		return left + " equals " + right + " → " + left.equals(right);
	}

    /* =========================================================
       CONVERSION
       ========================================================= */

	@PostMapping("/convert")
	public QuantityResponseDto convert(
			@RequestBody QuantityRequestDto req,
			@RequestParam String targetUnit) {

		Quantity<IMeasurable> quantity =
				new Quantity<>(req.getValue(), resolveUnit(req.getUnit()));

		Quantity<IMeasurable> result =
				quantityService.convert(quantity, resolveUnit(targetUnit));

		return new QuantityResponseDto(result.toString(), result.getValue());
	}

    /* =========================================================
       ADDITION
       ========================================================= */

	@PostMapping("/add")
	public QuantityResponseDto add(
			@RequestBody TwoQuantityRequestDto request,
			@RequestParam(required = false) String targetUnit) {

		QuantityRequestDto q1 = request.getQ1();
		QuantityRequestDto q2 = request.getQ2();

		Quantity<IMeasurable> left =
				new Quantity<>(q1.getValue(), resolveUnit(q1.getUnit()));

		Quantity<IMeasurable> right =
				new Quantity<>(q2.getValue(), resolveUnit(q2.getUnit()));

		Quantity<IMeasurable> result =
				(targetUnit != null)
						? quantityService.add(left, right, resolveUnit(targetUnit))
						: quantityService.add(left, right);

		return new QuantityResponseDto(result.toString(), result.getValue());
	}

    /* =========================================================
       SUBTRACTION
       ========================================================= */

	@PostMapping("/subtract")
	public QuantityResponseDto subtract(
			@RequestBody TwoQuantityRequestDto request,
			@RequestParam(required = false) String targetUnit) {

		QuantityRequestDto q1 = request.getQ1();
		QuantityRequestDto q2 = request.getQ2();

		Quantity<IMeasurable> left =
				new Quantity<>(q1.getValue(), resolveUnit(q1.getUnit()));

		Quantity<IMeasurable> right =
				new Quantity<>(q2.getValue(), resolveUnit(q2.getUnit()));

		Quantity<IMeasurable> result =
				(targetUnit != null)
						? quantityService.subtract(left, right, resolveUnit(targetUnit))
						: quantityService.subtract(left, right);

		return new QuantityResponseDto(result.toString(), result.getValue());
	}

    /* =========================================================
       DIVISION
       ========================================================= */

	@PostMapping("/divide")
	public double divide(@RequestBody TwoQuantityRequestDto request) {

		QuantityRequestDto q1 = request.getQ1();
		QuantityRequestDto q2 = request.getQ2();

		Quantity<IMeasurable> left =
				new Quantity<>(q1.getValue(), resolveUnit(q1.getUnit()));

		Quantity<IMeasurable> right =
				new Quantity<>(q2.getValue(), resolveUnit(q2.getUnit()));

		return quantityService.divide(left, right);
	}
}