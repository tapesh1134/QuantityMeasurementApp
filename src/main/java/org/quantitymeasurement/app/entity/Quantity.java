
package org.quantitymeasurement.app.entity;

import org.quantitymeasurement.app.entity.units.TemperatureUnit;

import java.util.Objects;
import java.util.function.DoubleBinaryOperator;

public final class Quantity<U extends IMeasurable> {

    private enum ArithmeticOperation {
        ADD((a,b)->a+b),
        SUBTRACT((a,b)->a-b),
        DIVIDE((a,b)->{
            if(Math.abs(b) < EPSILON) throw new ArithmeticException("Divisor can't be 0");
            return a/b;
        });

        private final DoubleBinaryOperator op;
        ArithmeticOperation(DoubleBinaryOperator op){ this.op=op; }
        public double compute(double a,double b){ return op.applyAsDouble(a,b); }
    }

    private final double value;
    private final U unit;
    private static final double EPSILON = 1e-9;

    public Quantity(double value,U unit){
        if(unit==null) throw new IllegalArgumentException("Unit cannot be null");
        if(!Double.isFinite(value)) throw new IllegalArgumentException("Invalid value");
        this.value=value;
        this.unit=unit;
    }

    public double getValue(){ return value; }
    public U getUnit(){ return unit; }

    private double toBase(){
        if (unit instanceof TemperatureUnit) {
            TemperatureUnit tu = (TemperatureUnit) unit;
            return tu.convertToBase(value);
        }
        return unit.convertToBaseUnit(value);
    }

    private double round(double v){
        return Math.round(v*100.0)/100.0;
    }

    private Quantity<U> operate(Quantity<U> that,ArithmeticOperation op){
        if(that==null) throw new IllegalArgumentException("Quantity cannot be null");

        this.unit.validateOperationSupport(op.name());
        that.unit.validateOperationSupport(op.name());

        double resultBase = op.compute(this.toBase(),that.toBase());

        double result;
        if (unit instanceof TemperatureUnit) {
            result = resultBase;
        } else {
            result = resultBase / this.unit.getConversionFactor();
        }

        Quantity<U> out = new Quantity<>(round(result), this.unit);
        return out;
    }

    public Quantity<U> add(Quantity<U> that){ return operate(that,ArithmeticOperation.ADD); }
    public Quantity<U> add(Quantity<U> that, U targetUnit) {

        if (targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null");

        Quantity<U> result = this.add(that);

        return result.convertTo(targetUnit);
    }
    public Quantity<U> subtract(Quantity<U> that){ return operate(that,ArithmeticOperation.SUBTRACT); }
    public Quantity<U> subtract(Quantity<U> that, U targetUnit) {

        if (targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null");

        Quantity<U> result = this.subtract(that);

        return result.convertTo(targetUnit);
    }

    public double divide(Quantity<U> that){
        if (that == null) throw new IllegalArgumentException("Quantity cannot be null");
        this.unit.validateOperationSupport("DIVIDE");
        that.unit.validateOperationSupport("DIVIDE");
        return round(ArithmeticOperation.DIVIDE.compute(this.toBase(),that.toBase()));
    }

    public Quantity<U> convertTo(U targetUnit){
        if(targetUnit==null) throw new IllegalArgumentException("Target unit cannot be null");

        if (this.unit instanceof TemperatureUnit && targetUnit instanceof TemperatureUnit) {
            TemperatureUnit src = (TemperatureUnit) this.unit;
            TemperatureUnit tgt = (TemperatureUnit) targetUnit;
            double converted = src.convertTo(this.value, tgt);
            Quantity<U> out = new Quantity<>(round(converted), targetUnit);
            return out;
        }

        if ((this.unit instanceof TemperatureUnit) ^ (targetUnit instanceof TemperatureUnit)) {
            throw new UnsupportedOperationException("Cannot convert between temperature and non-temperature units");
        }

        double base = this.toBase();
        double converted = base / targetUnit.getConversionFactor();
        Quantity<U> out = new Quantity<>(round(converted), targetUnit);
        return out;
    }

    @Override
    public boolean equals(Object obj){
        if(this==obj) return true;
        if(!(obj instanceof Quantity<?>)) return false;
        Quantity<?> that = (Quantity<?>) obj;
        if(this.unit.getClass()!=that.unit.getClass()) return false;
        return Math.abs(this.toBase()-that.toBase()) < EPSILON;
    }

    @Override
    public int hashCode(){
        return Objects.hash(round(toBase()));
    }

    @Override
    public String toString(){
        return value + " " + unit;
    }
}