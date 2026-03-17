package org.quantitymeasurement.app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;

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


    /* =========================================================
       CUSTOM CONSTRUCTORS (KEEP YOUR LOGIC)
       ========================================================= */

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

	public QuantityMeasurementEntity(
			Quantity<IMeasurable> thisQuantity,
			Quantity<IMeasurable> thatQuantity,
			String operation,
			String result
	) {
		this(thisQuantity, thatQuantity, operation);
		this.resultString = result;
	}

	public QuantityMeasurementEntity(
			Quantity<IMeasurable> thisQuantity,
			Quantity<IMeasurable> thatQuantity,
			String operation,
			Quantity<IMeasurable> result
	) {
		this(thisQuantity, thatQuantity, operation);
		this.resultValue = result.getValue();
		this.resultUnit = result.getUnit().getUnitName();
		this.resultMeasurementType =
				result.getUnit().getClass().getSimpleName();
	}

	public QuantityMeasurementEntity(
			Quantity<IMeasurable> thisQuantity,
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