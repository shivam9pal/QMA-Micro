package com.example.quantitymeasurementapp.unit;

@FunctionalInterface
interface SupportsArithmetic {
    boolean isSupported();
}

public interface IMeasurable {

    SupportsArithmetic supportsArithmetic = () -> true;

    double getConversionFactor();
    double convertToBaseUnit(double value);
    double convertFromBaseUnit(double baseValue);
    String getUnitName();

    default String getMeasurementType() {
        return this.getClass().getSimpleName();
    }

    default boolean supportsArithmetic() {
        return supportsArithmetic.isSupported();
    }

    default void validateOperationSupport(String operation) {
        // Default: allow all operations
    }

    // Used by service layer to resolve unit from string name
    default IMeasurable getUnitInstance(String unitName) {
        throw new UnsupportedOperationException(
                "getUnitInstance() not implemented for " + getClass().getSimpleName()
        );
    }
}
