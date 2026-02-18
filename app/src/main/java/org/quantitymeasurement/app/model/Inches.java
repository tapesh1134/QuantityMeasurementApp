package org.quantitymeasurement.app.model;

public class Inches {
	private Double value;

	public Inches(Double value) {
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
		Inches inches = (Inches) obj;
		return Double.compare(this.value, inches.value) == 0;
	}
}
