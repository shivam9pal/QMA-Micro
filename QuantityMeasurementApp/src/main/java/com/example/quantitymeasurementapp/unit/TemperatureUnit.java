package com.example.quantitymeasurementapp.unit;

import java.util.function.Function;

public enum TemperatureUnit implements IMeasurable {

    CELSIUS(false),
    FAHRENHEIT(true);

    private final Function<Double, Double> toBase;
    private final SupportsArithmetic supportsArithmetic = () -> false;

    TemperatureUnit(boolean isFahrenheit) {
        if (isFahrenheit) {
            this.toBase = f -> (f - 32.0) * 5.0 / 9.0;
        } else {
            this.toBase = c -> c;
        }
    }

    @Override
    public double getConversionFactor() {
        return 1.0;
    }

    @Override
    public double convertToBaseUnit(double value) {
        return toBase.apply(value);
    }

    @Override
    public double convertFromBaseUnit(double baseValue) {
        if (this == FAHRENHEIT) return (baseValue * 9.0 / 5.0) + 32.0;
        return baseValue;
    }

    @Override
    public String getUnitName() {
        return this.name();
    }

    @Override
    public String getMeasurementType() {
        return this.getClass().getSimpleName();
    }

    @Override
    public boolean supportsArithmetic() {
        return supportsArithmetic.isSupported();
    }

    @Override
    public void validateOperationSupport(String operation) {
        if (!supportsArithmetic()) {
            throw new UnsupportedOperationException(
                    this.name() + " does not support " + operation + " operations."
            );
        }
    }

    public double convertTo(double value, TemperatureUnit targetUnit) {
        double base = this.convertToBaseUnit(value);
        return targetUnit.convertFromBaseUnit(base);
    }

    @Override
    public IMeasurable getUnitInstance(String unitName) {
        for (TemperatureUnit unit : TemperatureUnit.values()) {
            if (unit.name().equalsIgnoreCase(unitName)) return unit;
        }
        throw new IllegalArgumentException("Invalid TemperatureUnit: " + unitName);
    }
}
