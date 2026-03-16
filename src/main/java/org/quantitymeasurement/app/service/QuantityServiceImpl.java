package org.quantitymeasurement.app.service;

import org.quantitymeasurement.app.entity.IMeasurable;
import org.quantitymeasurement.app.entity.Quantity;

public class QuantityServiceImpl implements QuantityService {

	@Override
	public <U extends IMeasurable> Quantity<U> add(Quantity<U> q1, Quantity<U> q2) {
		return q1.add(q2);
	}

	@Override
	public <U extends IMeasurable> Quantity<U> add(Quantity<U> q1, Quantity<U> q2, U targetUnit) {
		return q1.add(q2, targetUnit);
	}

	@Override
	public <U extends IMeasurable> Quantity<U> subtract(Quantity<U> q1, Quantity<U> q2) {
		return q1.subtract(q2);
	}

	@Override
	public <U extends IMeasurable> Quantity<U> subtract(Quantity<U> q1, Quantity<U> q2, U targetUnit) {
		return q1.subtract(q2, targetUnit);
	}

	@Override
	public <U extends IMeasurable> double divide(Quantity<U> q1, Quantity<U> q2) {
		return q1.divide(q2);
	}

	@Override
	public <U extends IMeasurable> Quantity<U> convert(Quantity<U> q, U targetUnit) {
		return q.convertTo(targetUnit);
	}
}