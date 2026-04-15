package com.example.qmameasurementservice.service;
import com.example.qmameasurementservice.client.HistoryClient;
import com.example.qmameasurementservice.dto.RecordRequestDto;
import com.example.qmameasurementservice.entity.IMeasurable;
import com.example.qmameasurementservice.entity.Quantity;
import com.example.qmameasurementservice.entity.QuantityMeasurementEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuantityServiceImpl implements QuantityService {
    private final HistoryClient historyClient;

    @Autowired
    public QuantityServiceImpl(HistoryClient historyClient) {
        this.historyClient = historyClient;
    }

    @Override
    public <U extends IMeasurable> QuantityMeasurementEntity add(String token, Quantity<U> q1, Quantity<U> q2) {
        try {
            Quantity<U> result = q1.add(q2);
            QuantityMeasurementEntity res = new QuantityMeasurementEntity((Quantity<IMeasurable>) q1,
                    (Quantity<IMeasurable>) q2,
                    "ADD",
                    (Quantity<IMeasurable>) result
            );
            save(res,token);
            return res;
        } catch (Exception e) {
            logError(q1, q2, "ADD", e);
            throw e;
        }
    }

    @Override
    public <U extends IMeasurable> QuantityMeasurementEntity add(String token,Quantity<U> q1, Quantity<U> q2, U targetUnit) {
        try {
            Quantity<U> result = q1.add(q2, targetUnit);
            QuantityMeasurementEntity res = new QuantityMeasurementEntity(
                    (Quantity<IMeasurable>) q1,
                    (Quantity<IMeasurable>) q2,
                    "ADD",
                    (Quantity<IMeasurable>) result
            );
            save(res,token);
            return res;
        } catch (Exception e) {
            logError(q1, q2, "ADD", e);
            throw e;
        }
    }

    @Override
    public <U extends IMeasurable> QuantityMeasurementEntity subtract(String token,Quantity<U> q1, Quantity<U> q2) {
        try {
            Quantity<U> result = q1.subtract(q2);
            QuantityMeasurementEntity res = new QuantityMeasurementEntity(
                    (Quantity<IMeasurable>) q1,
                    (Quantity<IMeasurable>) q2,
                    "SUBTRACT",
                    (Quantity<IMeasurable>) result
            );
            save(res,token);
            return res;

        } catch (Exception e) {
            logError(q1, q2, "SUBTRACT", e);
            throw e;
        }
    }

    @Override
    public <U extends IMeasurable> QuantityMeasurementEntity subtract(String token,Quantity<U> q1, Quantity<U> q2, U targetUnit) {
        try {
            Quantity<U> result = q1.subtract(q2, targetUnit);
            QuantityMeasurementEntity res = new QuantityMeasurementEntity(
                    (Quantity<IMeasurable>) q1,
                    (Quantity<IMeasurable>) q2,
                    "SUBTRACT",
                    (Quantity<IMeasurable>) result
            );
            save(res,token);
            return res;

        } catch (Exception e) {
            logError(q1, q2, "SUBTRACT", e);
            throw e;
        }
    }

    @Override
    public <U extends IMeasurable> QuantityMeasurementEntity divide(String token,Quantity<U> q1, Quantity<U> q2) {
        try {
            double result = q1.divide(q2);
            QuantityMeasurementEntity res = new QuantityMeasurementEntity(
                    (Quantity<IMeasurable>) q1,
                    (Quantity<IMeasurable>) q2,
                    "DIVIDE",
                    String.valueOf(result)
            );
            save(res,token);
            return res;

        } catch (Exception e) {
            logError(q1, q2, "DIVIDE", e);
            throw e;
        }
    }

    @Override
    public <U extends IMeasurable> QuantityMeasurementEntity convert(String token, Quantity<U> quantity, U targetUnit) {
        try {
            Quantity<U> result = quantity.convertTo(targetUnit);
            QuantityMeasurementEntity res = new QuantityMeasurementEntity((Quantity<IMeasurable>) quantity,
                    "CONVERT",
                    (Quantity<IMeasurable>) result
            );
            save(res,token);
            return res;

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public <U extends IMeasurable> QuantityMeasurementEntity compare(String token, Quantity<U> q1, Quantity<U> q2) {
        boolean result = q1.equals(q2);
        QuantityMeasurementEntity res = new QuantityMeasurementEntity(
                (Quantity<IMeasurable>) q1,
                (Quantity<IMeasurable>) q2,
                "COMPARE",
                result ? "Equal" : "Not Equal"
        );
        save(res,token);
        return res;
    }
    private <U extends IMeasurable> void logError(Quantity<U> q1, Quantity<U> q2, String operation, Exception e) {
        QuantityMeasurementEntity res = new QuantityMeasurementEntity(
                (Quantity<IMeasurable>) q1,
                (Quantity<IMeasurable>) q2,
                operation,
                e.getMessage(),
                true
        );
    }

    private void save(QuantityMeasurementEntity res, String token){
        if(token != null || !token.isEmpty()){
            historyClient.saveHistory(RecordRequestDto.getRecord(res), "jwt=" + token);
        }
    }
}