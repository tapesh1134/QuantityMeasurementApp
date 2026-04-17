package com.example.qmameasurementservice.dto;

import com.example.qmameasurementservice.entity.QuantityMeasurementEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RecordRequestDto {
    private Double thisValue;
    private String thisUnit;
    private String thisMeasurementType;

    private Double thatValue;
    private String thatUnit;
    private String thatMeasurementType;

    private String operation;

    private Double resultValue;
    private String resultUnit;
    private String resultMeasurementType;

    private String resultString;

    private Boolean isError;
    private String errorMessage;
    private LocalDateTime timestamp;

    public static RecordRequestDto getRecord(QuantityMeasurementEntity entity){
        return RecordRequestDto.builder()
                .thisValue(entity.getThisValue())
                .thisUnit(entity.getThisUnit())
                .thisMeasurementType(entity.getThisMeasurementType())
                .thatValue(entity.getThatValue())
                .thatUnit(entity.getThatUnit())
                .thatMeasurementType(entity.getThatMeasurementType())
                .operation(entity.getOperation())
                .resultValue(entity.getResultValue())
                .resultUnit(entity.getResultUnit())
                .resultMeasurementType(entity.getResultMeasurementType())
                .resultString(entity.getResultString())
                .isError(entity.getIsError())
                .errorMessage(entity.getErrorMessage())
                .timestamp(entity.getTimestamp())
                .build();
    }
}
