package com.example.qmameasurementservice.service;

import com.example.qmameasurementservice.entity.IMeasurable;
import com.example.qmameasurementservice.entity.Quantity;
import com.example.qmameasurementservice.entity.QuantityMeasurementEntity;

public interface QuantityService {

	<U extends IMeasurable> QuantityMeasurementEntity add(String token,Quantity<U> q1, Quantity<U> q2);

	<U extends IMeasurable> QuantityMeasurementEntity add(String token, Quantity<U> q1, Quantity<U> q2, U targetUnit);

	<U extends IMeasurable> QuantityMeasurementEntity subtract(String token, Quantity<U> q1, Quantity<U> q2);

	<U extends IMeasurable> QuantityMeasurementEntity subtract(String token, Quantity<U> q1, Quantity<U> q2, U targetUnit);

	<U extends IMeasurable> QuantityMeasurementEntity divide(String token, Quantity<U> q1, Quantity<U> q2);

	<U extends IMeasurable> QuantityMeasurementEntity convert(String token,Quantity<U> q, U targetUnit);

	<U extends IMeasurable> QuantityMeasurementEntity compare(String token, Quantity<U> q1, Quantity<U> q2);

}