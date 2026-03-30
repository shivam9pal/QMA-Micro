package com.example.quantitymeasurementapp.entity;

import com.example.quantitymeasurementapp.unit.IMeasurable;



public class QuantityModel<U extends IMeasurable> {

    private double value;
    private U unit;

    

    public QuantityModel() {}

    public QuantityModel(double value, U unit) {
        this.value = value;
        this.unit  = unit;
    }

    

    public double getValue() { return value; }
    public void setValue(double value) { this.value = value; }

    public U getUnit() { return unit; }
    public void setUnit(U unit) { this.unit = unit; }

    

    @Override
    public String toString() {
        return "QuantityModel(" + value + ", " + unit.getUnitName() + ")";
    }
}
