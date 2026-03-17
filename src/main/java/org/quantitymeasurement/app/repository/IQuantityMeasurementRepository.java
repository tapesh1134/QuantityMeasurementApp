package org.quantitymeasurement.app.repository;

import java.util.List;

import org.quantitymeasurement.app.entity.QuantityMeasurementEntity;

public interface IQuantityMeasurementRepository {
	void save(QuantityMeasurementEntity entity);

	List<QuantityMeasurementEntity> findAll();
}