package org.quantitymeasurement.app.service;

import java.util.List;

import org.quantitymeasurement.app.entity.IMeasurable;
import org.quantitymeasurement.app.entity.Quantity;
import org.quantitymeasurement.app.entity.QuantityMeasurementEntity;
import org.quantitymeasurement.app.repository.QuantityMeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuantityServiceImpl implements QuantityService {
    private QuantityMeasurementRepository repository;

    @Autowired
    public QuantityServiceImpl(QuantityMeasurementRepository quantityMeasurementRepository) {
        this.repository = quantityMeasurementRepository;
    }

    @Override
    public <U extends IMeasurable> QuantityMeasurementEntity add(Long userId,Quantity<U> q1, Quantity<U> q2) {
        try {
            Quantity<U> result = q1.add(q2);
            QuantityMeasurementEntity res = new QuantityMeasurementEntity(userId,
                    (Quantity<IMeasurable>) q1,
                    (Quantity<IMeasurable>) q2,
                    "ADD",
                    (Quantity<IMeasurable>) result
            );
            if(userId != null){
                return repository.save(res);
            } else {
                return res;
            }
        } catch (Exception e) {
            logError(userId,q1, q2, "ADD", e);
            throw e;
        }
    }

    @Override
    public <U extends IMeasurable> QuantityMeasurementEntity add(Long userId, Quantity<U> q1, Quantity<U> q2, U targetUnit) {
        try {
            Quantity<U> result = q1.add(q2, targetUnit);
            QuantityMeasurementEntity res = new QuantityMeasurementEntity(
                    userId,
                    (Quantity<IMeasurable>) q1,
                    (Quantity<IMeasurable>) q2,
                    "ADD",
                    (Quantity<IMeasurable>) result
            );
            if(userId != -1){
                return repository.save(res);
            } else {
                return res;
            }
        } catch (Exception e) {
            logError(userId,q1, q2, "ADD", e);
            throw e;
        }
    }

    @Override
    public <U extends IMeasurable> QuantityMeasurementEntity subtract(Long userId, Quantity<U> q1, Quantity<U> q2) {
        try {
            Quantity<U> result = q1.subtract(q2);
            QuantityMeasurementEntity res = new QuantityMeasurementEntity(
                    userId,
                    (Quantity<IMeasurable>) q1,
                    (Quantity<IMeasurable>) q2,
                    "SUBTRACT",
                    (Quantity<IMeasurable>) result
            );
            if(userId != -1){
                return repository.save(res);
            } else  {
                return res;
            }
        } catch (Exception e) {
            logError(userId,q1, q2, "SUBTRACT", e);
            throw e;
        }
    }

    @Override
    public <U extends IMeasurable> QuantityMeasurementEntity subtract(Long userId, Quantity<U> q1, Quantity<U> q2, U targetUnit) {
        try {
            Quantity<U> result = q1.subtract(q2, targetUnit);
            QuantityMeasurementEntity res = new QuantityMeasurementEntity(
                    userId,
                    (Quantity<IMeasurable>) q1,
                    (Quantity<IMeasurable>) q2,
                    "SUBTRACT",
                    (Quantity<IMeasurable>) result
            );
            if(userId != -1){
                return repository.save(res);
            } else {
                return res;
            }
        } catch (Exception e) {
            logError(userId,q1, q2, "SUBTRACT", e);
            throw e;
        }
    }

    @Override
    public <U extends IMeasurable> QuantityMeasurementEntity divide(Long userId,Quantity<U> q1, Quantity<U> q2) {
        try {
            double result = q1.divide(q2);
            QuantityMeasurementEntity res = new QuantityMeasurementEntity(
                    userId,
                    (Quantity<IMeasurable>) q1,
                    (Quantity<IMeasurable>) q2,
                    "DIVIDE",
                    String.valueOf(result)
            );
            if(userId != -1){
                return repository.save(res);
            } else {
                return res;
            }
        } catch (Exception e) {
            logError(userId,q1, q2, "DIVIDE", e);
            throw e;
        }
    }

    @Override
    public <U extends IMeasurable> QuantityMeasurementEntity convert(Long userId, Quantity<U> quantity, U targetUnit) {
        try {
            Quantity<U> result = quantity.convertTo(targetUnit);
            QuantityMeasurementEntity res = new QuantityMeasurementEntity(userId,
                    (Quantity<IMeasurable>) quantity,
                    "CONVERT",
                    (Quantity<IMeasurable>) result
            );
            if(userId != -1){
                return repository.save(res);
            } else {
                return res;
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public <U extends IMeasurable> QuantityMeasurementEntity compare(Long userId,Quantity<U> q1, Quantity<U> q2) {
        boolean result = q1.equals(q2);
        QuantityMeasurementEntity res = new QuantityMeasurementEntity(
                userId,
                (Quantity<IMeasurable>) q1,
                (Quantity<IMeasurable>) q2,
                "COMPARE",
                result ? "Equal" : "Not Equal"
        );
        if(userId != -1){
            return repository.save(res);
        } else {
            return res;
        }
    }

    @Override
    public List<QuantityMeasurementEntity> getAllMeasurements(Long userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public Void deleteAllMeasurements(Long userId) {
        return repository.deleteByUserId(userId);
    }

    private <U extends IMeasurable> void logError(Long userId, Quantity<U> q1, Quantity<U> q2, String operation, Exception e) {
        QuantityMeasurementEntity res = new QuantityMeasurementEntity(
                userId,
                (Quantity<IMeasurable>) q1,
                (Quantity<IMeasurable>) q2,
                operation,
                e.getMessage(),
                true
        );
        if(userId != -1){
            repository.save(res);
        }
    }
}