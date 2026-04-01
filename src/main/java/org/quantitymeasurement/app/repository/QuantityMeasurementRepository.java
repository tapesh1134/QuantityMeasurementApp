package org.quantitymeasurement.app.repository;

import org.quantitymeasurement.app.entity.QuantityMeasurementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuantityMeasurementRepository
        extends JpaRepository<QuantityMeasurementEntity, Long> {
    List<QuantityMeasurementEntity> findByUserId(Long userId);

    Void deleteByUserId(Long userId);
}