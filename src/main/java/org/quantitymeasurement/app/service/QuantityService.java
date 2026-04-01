package org.quantitymeasurement.app.service;

import org.quantitymeasurement.app.entity.IMeasurable;
import org.quantitymeasurement.app.entity.Quantity;
import org.quantitymeasurement.app.entity.QuantityMeasurementEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface QuantityService {

	<U extends IMeasurable> QuantityMeasurementEntity add(Long userId,Quantity<U> q1, Quantity<U> q2);

	<U extends IMeasurable> QuantityMeasurementEntity add(Long userId,Quantity<U> q1, Quantity<U> q2, U targetUnit);

	<U extends IMeasurable> QuantityMeasurementEntity subtract(Long userId,Quantity<U> q1, Quantity<U> q2);

	<U extends IMeasurable> QuantityMeasurementEntity subtract(Long userId,Quantity<U> q1, Quantity<U> q2, U targetUnit);

	<U extends IMeasurable> QuantityMeasurementEntity divide(Long userId,Quantity<U> q1, Quantity<U> q2);

	<U extends IMeasurable> QuantityMeasurementEntity convert(Long userId,Quantity<U> q, U targetUnit);

	<U extends IMeasurable> QuantityMeasurementEntity compare(Long userId,Quantity<U> q1, Quantity<U> q2);

	List<QuantityMeasurementEntity> getAllMeasurements(Long userId);

	Void deleteAllMeasurements(Long userId);
}