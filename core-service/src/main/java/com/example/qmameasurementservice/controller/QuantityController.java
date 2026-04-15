package com.example.qmameasurementservice.controller;


import com.example.qmameasurementservice.dto.ApiResponseDto;
import com.example.qmameasurementservice.dto.QuantityRequestDto;
import com.example.qmameasurementservice.dto.TwoQuantityRequestDto;
import com.example.qmameasurementservice.entity.IMeasurable;
import com.example.qmameasurementservice.entity.Quantity;
import com.example.qmameasurementservice.entity.QuantityMeasurementEntity;
import com.example.qmameasurementservice.entity.units.LengthUnit;
import com.example.qmameasurementservice.entity.units.TemperatureUnit;
import com.example.qmameasurementservice.entity.units.VolumeUnit;
import com.example.qmameasurementservice.entity.units.WeightUnit;
import com.example.qmameasurementservice.service.QuantityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/quantities")
public class QuantityController {

	private final QuantityService quantityService;

	public QuantityController(QuantityService quantityService) {
		this.quantityService = quantityService;
	}

	private IMeasurable resolveUnit(String unit) {

		try { return LengthUnit.valueOf(unit); } catch (Exception ignored) {}
		try { return WeightUnit.valueOf(unit); } catch (Exception ignored) {}
		try { return VolumeUnit.valueOf(unit); } catch (Exception ignored) {}
		try { return TemperatureUnit.valueOf(unit); } catch (Exception ignored) {}

		throw new IllegalArgumentException("Unknown unit: " + unit);
	}

	@GetMapping
	public ResponseEntity<?> welcome(){
		return ResponseEntity.status(200).body("Welcome to the quantity measurement you are authorized");
	}

	@PostMapping("/equality")
	public ResponseEntity<ApiResponseDto<QuantityMeasurementEntity>> checkEquality(@CookieValue(value = "jwt", required = false) String token, @RequestBody TwoQuantityRequestDto request) {

		QuantityRequestDto q1 = request.getQ1();
		QuantityRequestDto q2 = request.getQ2();

		Quantity<IMeasurable> left =
				new Quantity<>(q1.getValue(), resolveUnit(q1.getUnit()));

		Quantity<IMeasurable> right =
				new Quantity<>(q2.getValue(), resolveUnit(q2.getUnit()));

		return ResponseEntity.status(200).body(new ApiResponseDto<>(true, "Operation Successful", quantityService.compare(token ,left, right)));
	}

	@PostMapping("/convert")
	public ResponseEntity<ApiResponseDto<QuantityMeasurementEntity>> convert(@CookieValue(value = "jwt", required = false) String token,@RequestBody QuantityRequestDto req,
	                                                                      @RequestParam String targetUnit) {

		Quantity<IMeasurable> quantity =
				new Quantity<>(req.getValue(), resolveUnit(req.getUnit()));

		return ResponseEntity.status(200).body(new ApiResponseDto<>(true, "Operation Successful", quantityService.convert(token, quantity, resolveUnit(targetUnit))));
	}

	@PostMapping("/add")
	public ResponseEntity<ApiResponseDto<QuantityMeasurementEntity>> add(@CookieValue(value = "jwt", required = false) String token, @RequestBody TwoQuantityRequestDto request,
	                                                                  @RequestParam(required = false) String targetUnit) {

		QuantityRequestDto q1 = request.getQ1();
		QuantityRequestDto q2 = request.getQ2();

		Quantity<IMeasurable> left =
				new Quantity<>(q1.getValue(), resolveUnit(q1.getUnit()));

		Quantity<IMeasurable> right =
				new Quantity<>(q2.getValue(), resolveUnit(q2.getUnit()));

		return ResponseEntity.status(200).body(new ApiResponseDto<>(true, "Operation Successful", quantityService.add(token, left, right, resolveUnit(targetUnit))));
	}

	@PostMapping("/subtract")
	public ResponseEntity<ApiResponseDto<QuantityMeasurementEntity>> subtract(@CookieValue(value = "jwt", required = false) String token, @RequestBody TwoQuantityRequestDto request,
	                                                                       @RequestParam(required = false) String targetUnit) {

		QuantityRequestDto q1 = request.getQ1();
		QuantityRequestDto q2 = request.getQ2();

		Quantity<IMeasurable> left =
				new Quantity<>(q1.getValue(), resolveUnit(q1.getUnit()));

		Quantity<IMeasurable> right =
				new Quantity<>(q2.getValue(), resolveUnit(q2.getUnit()));

		return ResponseEntity.status(200).body(new ApiResponseDto<>(true, "Operation Successful", quantityService.subtract(token, left, right, resolveUnit(targetUnit))));
	}

	@PostMapping("/divide")
	public ResponseEntity<ApiResponseDto<QuantityMeasurementEntity>> divide(@CookieValue(value = "jwt", required = false) String token, @RequestBody TwoQuantityRequestDto request) {

		QuantityRequestDto q1 = request.getQ1();
		QuantityRequestDto q2 = request.getQ2();

		Quantity<IMeasurable> left =
				new Quantity<>(q1.getValue(), resolveUnit(q1.getUnit()));

		Quantity<IMeasurable> right =
				new Quantity<>(q2.getValue(), resolveUnit(q2.getUnit()));

		return ResponseEntity.status(200).body(new ApiResponseDto<>(true, "Operation Successful", quantityService.divide(token,left, right)));
	}
}