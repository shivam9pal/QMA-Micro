package com.example.quantitymeasurementapp.domain;

import com.example.quantitymeasurementapp.unit.IMeasurable;
import com.example.quantitymeasurementapp.unit.TemperatureUnit;

public class Quantity<U extends IMeasurable> {

    private final double value;
    private final U unit;
    private static final double EPSILON = 1e-5;

    public Quantity(double value, U unit) {
        if (unit == null)
            throw new IllegalArgumentException("Unit cannot be null");
        if (!Double.isFinite(value))
            throw new IllegalArgumentException("Value must be finite");
        this.value = value;
        this.unit = unit;
    }

    public double getValue() { return value; }
    public U getUnit()       { return unit; }

    public double toBaseUnit() {
        return unit.convertToBaseUnit(value);
    }

    // ── Arithmetic Operation Enum ─────────────────────────────────────────────

    private enum ArithmeticOperation {
        ADD {
            @Override double compute(double a, double b) { return a + b; }
        },
        SUBTRACT {
            @Override double compute(double a, double b) { return a - b; }
        },
        DIVIDE {
            @Override double compute(double a, double b) {
                if (Math.abs(b) < 1e-5)
                    throw new ArithmeticException("Division by zero");
                return a / b;
            }
        };
        abstract double compute(double a, double b);
    }

    // ── Validation ────────────────────────────────────────────────────────────

    private void validateArithmeticOperands(Quantity<U> other,
                                            U targetUnit,
                                            boolean targetRequired) {
        if (other == null)
            throw new IllegalArgumentException("Other quantity cannot be null");
        if (targetRequired && targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null");
        if (!this.unit.getClass().equals(other.unit.getClass()))
            throw new IllegalArgumentException(
                    "Cross-category operation not allowed: "
                    + this.unit.getMeasurementType()
                    + " vs " + other.unit.getMeasurementType());
        if (!Double.isFinite(this.value) || !Double.isFinite(other.value))
            throw new IllegalArgumentException("Values must be finite");
    }

    private double performBaseArithmetic(Quantity<U> other, ArithmeticOperation op) {
        this.unit.validateOperationSupport(op.name());
        other.unit.validateOperationSupport(op.name());
        return op.compute(this.toBaseUnit(), other.toBaseUnit());
    }

    // ── Convert ───────────────────────────────────────────────────────────────

    @SuppressWarnings("unchecked")
    public Quantity<U> convertTo(U targetUnit) {
        if (targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null");

        // ✅ Fully qualified to avoid QuantityDTO.TemperatureUnit ambiguity
        if (unit instanceof TemperatureUnit tempUnit) {
            TemperatureUnit target = (TemperatureUnit) targetUnit;
            double converted = tempUnit.convertTo(value, target);
            return new Quantity<>(converted, targetUnit);
        }

        double converted = targetUnit.convertFromBaseUnit(toBaseUnit());
        return new Quantity<>(round(converted), targetUnit);
    }

    // ── Add ───────────────────────────────────────────────────────────────────

    public Quantity<U> add(Quantity<U> other) {
        validateArithmeticOperands(other, null, false);
        double result = unit.convertFromBaseUnit(
                performBaseArithmetic(other, ArithmeticOperation.ADD));
        return new Quantity<>(round(result), unit);
    }

    public Quantity<U> add(Quantity<U> other, U targetUnit) {
        validateArithmeticOperands(other, targetUnit, true);
        double result = targetUnit.convertFromBaseUnit(
                performBaseArithmetic(other, ArithmeticOperation.ADD));
        return new Quantity<>(round(result), targetUnit);
    }

    // ── Subtract ──────────────────────────────────────────────────────────────

    public Quantity<U> subtract(Quantity<U> other) {
        validateArithmeticOperands(other, null, false);
        double result = unit.convertFromBaseUnit(
                performBaseArithmetic(other, ArithmeticOperation.SUBTRACT));
        return new Quantity<>(round(result), unit);
    }

    public Quantity<U> subtract(Quantity<U> other, U targetUnit) {
        validateArithmeticOperands(other, targetUnit, true);
        double result = targetUnit.convertFromBaseUnit(
                performBaseArithmetic(other, ArithmeticOperation.SUBTRACT));
        return new Quantity<>(round(result), targetUnit);
    }

    // ── Divide ────────────────────────────────────────────────────────────────

    public double divide(Quantity<U> other) {
        validateArithmeticOperands(other, null, false);
        return performBaseArithmetic(other, ArithmeticOperation.DIVIDE);
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Quantity<?> other)) return false;
        if (!unit.getClass().equals(other.unit.getClass())) return false;
        return Math.abs(this.toBaseUnit() - other.toBaseUnit()) <= EPSILON;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(Math.round(toBaseUnit() / EPSILON));
    }

    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit.getUnitName() + ")";
    }
}
