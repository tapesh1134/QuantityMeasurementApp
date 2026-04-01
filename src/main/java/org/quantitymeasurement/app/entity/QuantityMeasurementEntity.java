package org.quantitymeasurement.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "quantity_measurement_history")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class QuantityMeasurementEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@JsonIgnore
	private Long userId;

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
	@Column(name = "timeStamp", nullable = false, updatable = false)
	private LocalDateTime timestamp;

	@PrePersist
	protected void onCreate() {
		this.timestamp = LocalDateTime.now();
	}

	private QuantityMeasurementEntity(
			Long userId,
			Quantity<IMeasurable> thisQuantity,
			Quantity<IMeasurable> thatQuantity,
			String operation
	) {
		this.userId = userId;
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
			Long userId,
			Quantity<IMeasurable> thisQuantity,
			String operation
	) {
		this.userId = userId;
		this.thisValue = thisQuantity.getValue();
		this.thisUnit = thisQuantity.getUnit().getUnitName();
		this.thisMeasurementType =
				thisQuantity.getUnit().getClass().getSimpleName();
		this.operation = operation;
	}

	public QuantityMeasurementEntity(Long userId,
	                                 Quantity<IMeasurable> thisQuantity,
	                                 Quantity<IMeasurable> thatQuantity,
	                                 String operation,
	                                 String result
	) {
		this(userId,thisQuantity, thatQuantity, operation);
		this.resultString = result;
	}

	public QuantityMeasurementEntity(Long userId,
	                                 Quantity<IMeasurable> thisQuantity,
	                                 Quantity<IMeasurable> thatQuantity,
	                                 String operation,
	                                 Quantity<IMeasurable> result
	) {
		this(userId ,thisQuantity, thatQuantity, operation);
		this.resultValue = result.getValue();
		this.resultUnit = result.getUnit().getUnitName();
		this.resultMeasurementType =
				result.getUnit().getClass().getSimpleName();
		this.resultString = result.toString();
	}

	public QuantityMeasurementEntity(Long userId,
	                                 Quantity<IMeasurable> thisQuantity,
	                                 String operation,
	                                 Quantity<IMeasurable> result
	) {
		this(userId,thisQuantity, operation);
		this.resultValue = result.getValue();
		this.resultUnit = result.getUnit().getUnitName();
		this.resultMeasurementType =
				result.getUnit().getClass().getSimpleName();
		this.resultString = result.toString();
	}

	public QuantityMeasurementEntity(Long userId,
	                                 Quantity<IMeasurable> thisQuantity,
	                                 Quantity<IMeasurable> thatQuantity,
	                                 String operation,
	                                 String errorMessage,
	                                 boolean isError
	) {
		this(userId,thisQuantity, thatQuantity, operation);
		this.errorMessage = errorMessage;
		this.isError = isError;
	}
}