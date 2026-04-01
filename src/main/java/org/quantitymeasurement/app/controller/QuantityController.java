package org.quantitymeasurement.app.controller;


import org.quantitymeasurement.app.dto.ApiResponseDto;
import org.quantitymeasurement.app.dto.QuantityRequestDto;
import org.quantitymeasurement.app.dto.TwoQuantityRequestDto;
import org.quantitymeasurement.app.entity.IMeasurable;
import org.quantitymeasurement.app.entity.Quantity;
import org.quantitymeasurement.app.entity.QuantityMeasurementEntity;
import org.quantitymeasurement.app.entity.units.LengthUnit;
import org.quantitymeasurement.app.entity.units.TemperatureUnit;
import org.quantitymeasurement.app.entity.units.VolumeUnit;
import org.quantitymeasurement.app.entity.units.WeightUnit;
import org.quantitymeasurement.app.service.QuantityService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quantities")
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
	public ResponseEntity<ApiResponseDto<QuantityMeasurementEntity>> checkEquality(@RequestBody TwoQuantityRequestDto request, Authentication authentication) {

		QuantityRequestDto q1 = request.getQ1();
		QuantityRequestDto q2 = request.getQ2();

		Quantity<IMeasurable> left =
				new Quantity<>(q1.getValue(), resolveUnit(q1.getUnit()));

		Quantity<IMeasurable> right =
				new Quantity<>(q2.getValue(), resolveUnit(q2.getUnit()));

		return ResponseEntity.status(200).body(new ApiResponseDto<>(true, "Operation Successful", quantityService.compare(authentication != null ? (Long) authentication.getPrincipal() : -1,left, right)));
	}

	@PostMapping("/convert")
	public ResponseEntity<ApiResponseDto<QuantityMeasurementEntity>> convert(Authentication authentication,
	                                                                      @RequestBody QuantityRequestDto req,
	                                                                      @RequestParam String targetUnit) {

		Quantity<IMeasurable> quantity =
				new Quantity<>(req.getValue(), resolveUnit(req.getUnit()));

		return ResponseEntity.status(200).body(new ApiResponseDto<>(true, "Operation Successful", quantityService.convert(authentication != null ? (Long) authentication.getPrincipal() : -1,quantity, resolveUnit(targetUnit))));
	}

	@PostMapping("/add")
	public ResponseEntity<ApiResponseDto<QuantityMeasurementEntity>> add(Authentication authentication,
	                                                                  @RequestBody TwoQuantityRequestDto request,
	                                                                  @RequestParam(required = false) String targetUnit) {

		QuantityRequestDto q1 = request.getQ1();
		QuantityRequestDto q2 = request.getQ2();

		Quantity<IMeasurable> left =
				new Quantity<>(q1.getValue(), resolveUnit(q1.getUnit()));

		Quantity<IMeasurable> right =
				new Quantity<>(q2.getValue(), resolveUnit(q2.getUnit()));

		return ResponseEntity.status(200).body(new ApiResponseDto<>(true, "Operation Successful", quantityService.add(authentication != null ? (Long) authentication.getPrincipal() : -1,left, right, resolveUnit(targetUnit))));
	}

	@PostMapping("/subtract")
	public ResponseEntity<ApiResponseDto<QuantityMeasurementEntity>> subtract(Authentication authentication,
	                                                                       @RequestBody TwoQuantityRequestDto request,
	                                                                       @RequestParam(required = false) String targetUnit) {

		QuantityRequestDto q1 = request.getQ1();
		QuantityRequestDto q2 = request.getQ2();

		Quantity<IMeasurable> left =
				new Quantity<>(q1.getValue(), resolveUnit(q1.getUnit()));

		Quantity<IMeasurable> right =
				new Quantity<>(q2.getValue(), resolveUnit(q2.getUnit()));

		return ResponseEntity.status(200).body(new ApiResponseDto<>(true, "Operation Successful", quantityService.subtract(authentication != null ? (Long) authentication.getPrincipal() : -1,left, right, resolveUnit(targetUnit))));
	}

	@PostMapping("/divide")
	public ResponseEntity<ApiResponseDto<QuantityMeasurementEntity>> divide(Authentication authentication,@RequestBody TwoQuantityRequestDto request) {

		QuantityRequestDto q1 = request.getQ1();
		QuantityRequestDto q2 = request.getQ2();

		Quantity<IMeasurable> left =
				new Quantity<>(q1.getValue(), resolveUnit(q1.getUnit()));

		Quantity<IMeasurable> right =
				new Quantity<>(q2.getValue(), resolveUnit(q2.getUnit()));

		return ResponseEntity.status(200).body(new ApiResponseDto<>(true, "Operation Successful", quantityService.divide(authentication != null ? (Long) authentication.getPrincipal() : -1, left, right)));
	}

	@GetMapping("/history")
	public ResponseEntity<ApiResponseDto<List<QuantityMeasurementEntity>>> getHistory(Authentication authentication){
		return ResponseEntity.status(200).body(new ApiResponseDto<>(true, "History fetched successfully.", quantityService.getAllMeasurements(authentication != null ? (Long) authentication.getPrincipal() : -1)));
	}

	@DeleteMapping("/history")
	public ResponseEntity<ApiResponseDto<List<?>>> deleteHistory(Authentication authentication){
		return ResponseEntity.status(200).body(new ApiResponseDto<>(true, "History deleted successfully."));
	}
}