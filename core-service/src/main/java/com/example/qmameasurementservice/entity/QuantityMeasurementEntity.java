package com.example.qmameasurementservice.entity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class QuantityMeasurementEntity {
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
	protected void onCreate() {
		this.timestamp = LocalDateTime.now();
	}

	private QuantityMeasurementEntity(
			Quantity<IMeasurable> thisQuantity,
			Quantity<IMeasurable> thatQuantity,
			String operation
	) {
		this.thisValue = thisQuantity.getValue();
		this.thisUnit = thisQuantity.getUnit().getUnitName();
		this.thisMeasurementType =
				thisQuantity.getUnit().getClass().getSimpleName();

		this.thatValue = thatQuantity.getValue();
		this.thatUnit = thatQuantity.getUnit().getUnitName();
		this.thatMeasurementType =
				thatQuantity.getUnit().getClass().getSimpleName();

		this.operation = operation;
	}

	private QuantityMeasurementEntity(
			Quantity<IMeasurable> thisQuantity,
			String operation
	) {
		this.thisValue = thisQuantity.getValue();
		this.thisUnit = thisQuantity.getUnit().getUnitName();
		this.thisMeasurementType =
				thisQuantity.getUnit().getClass().getSimpleName();
		this.operation = operation;
	}

	public QuantityMeasurementEntity(Quantity<IMeasurable> thisQuantity,
	                                 Quantity<IMeasurable> thatQuantity,
	                                 String operation,
	                                 String result
	) {
		this(thisQuantity, thatQuantity, operation);
		this.resultString = result;
	}

	public QuantityMeasurementEntity(Quantity<IMeasurable> thisQuantity,
	                                 Quantity<IMeasurable> thatQuantity,
	                                 String operation,
	                                 Quantity<IMeasurable> result
	) {
		this(thisQuantity, thatQuantity, operation);
		this.resultValue = result.getValue();
		this.resultUnit = result.getUnit().getUnitName();
		this.resultMeasurementType =
				result.getUnit().getClass().getSimpleName();
		this.resultString = result.toString();
	}

	public QuantityMeasurementEntity(Quantity<IMeasurable> thisQuantity,
	                                 String operation,
	                                 Quantity<IMeasurable> result
	) {
		this(thisQuantity, operation);
		this.resultValue = result.getValue();
		this.resultUnit = result.getUnit().getUnitName();
		this.resultMeasurementType =
				result.getUnit().getClass().getSimpleName();
		this.resultString = result.toString();
	}

	public QuantityMeasurementEntity(Quantity<IMeasurable> thisQuantity,
	                                 Quantity<IMeasurable> thatQuantity,
	                                 String operation,
	                                 String errorMessage,
	                                 boolean isError
	) {
		this(thisQuantity, thatQuantity, operation);
		this.errorMessage = errorMessage;
		this.isError = isError;
	}
}