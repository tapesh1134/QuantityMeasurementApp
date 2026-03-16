package org.quantitymeasurement.app.service;

import org.quantitymeasurement.app.entity.IMeasurable;
import org.quantitymeasurement.app.entity.Quantity;

public interface QuantityService {

	<U extends IMeasurable> Quantity<U> add(Quantity<U> q1, Quantity<U> q2);

	<U extends IMeasurable> Quantity<U> add(Quantity<U> q1, Quantity<U> q2, U targetUnit);

	<U extends IMeasurable> Quantity<U> subtract(Quantity<U> q1, Quantity<U> q2);

	<U extends IMeasurable> Quantity<U> subtract(Quantity<U> q1, Quantity<U> q2, U targetUnit);

	<U extends IMeasurable> double divide(Quantity<U> q1, Quantity<U> q2);

	<U extends IMeasurable> Quantity<U> convert(Quantity<U> q, U targetUnit);
}