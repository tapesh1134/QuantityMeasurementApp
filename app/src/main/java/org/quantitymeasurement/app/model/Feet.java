package org.quantitymeasurement.app.model;

public class Feet {
	private Double value;

	public Feet(Double value) {
		if (value == null || value.isNaN()) {
			throw new IllegalArgumentException("Feet value must be numeric and not null");
		}
		this.value = value;
	}

	public Double getValue() {
		return value;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;

		Feet feet = (Feet) obj;

		return Double.compare(this.value, feet.value) == 0;
	}
}
