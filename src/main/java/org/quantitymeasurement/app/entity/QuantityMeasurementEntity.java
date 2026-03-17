package org.quantitymeasurement.app.entity;

import java.io.Serializable;

public class QuantityMeasurementEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	public double thisValue;
	public String thisUnit;
	public String thisMeasurementType;

	public double thatValue;
	public String thatUnit;
	public String thatMeasurementType;

	public String operation;

	public double resultValue;
	public String resultUnit;
	public String resultMeasurementType;

	public String resultString;

	public boolean isError;

	public String errorMessage;

	private QuantityMeasurementEntity(Quantity<IMeasurable> thisQuantity, Quantity<IMeasurable> thatQuantity,
			String operation) {

		this.thisValue = thisQuantity.getValue();
		this.thisUnit = thisQuantity.getUnit().getUnitName();
		this.thisMeasurementType = thisQuantity.getUnit().getClass().getSimpleName();

		this.thatValue = thatQuantity.getValue();
		this.thatUnit = thatQuantity.getUnit().getUnitName();
		this.thatMeasurementType = thatQuantity.getUnit().getClass().getSimpleName();

		this.operation = operation;
	}

	public QuantityMeasurementEntity(Quantity<IMeasurable> thisQuantity, Quantity<IMeasurable> thatQuantity,
			String operation, String result) {

		this(thisQuantity, thatQuantity, operation);

		this.resultString = result;
	}

	public QuantityMeasurementEntity(Quantity<IMeasurable> thisQuantity, Quantity<IMeasurable> thatQuantity,
			String operation, Quantity<IMeasurable> result) {

		this(thisQuantity, thatQuantity, operation);

		this.resultValue = result.getValue();
		this.resultUnit = result.getUnit().getUnitName();
		this.resultMeasurementType = result.getUnit().getClass().getSimpleName();
	}

	public QuantityMeasurementEntity(Quantity<IMeasurable> thisQuantity, Quantity<IMeasurable> thatQuantity,
			String operation, String errorMessage, boolean isError) {

		this(thisQuantity, thatQuantity, operation);

		this.errorMessage = errorMessage;
		this.isError = isError;
	}

	public QuantityMeasurementEntity() {

	}
}