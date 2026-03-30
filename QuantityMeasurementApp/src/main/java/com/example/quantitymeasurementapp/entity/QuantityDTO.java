package com.example.quantitymeasurementapp.entity;



public class QuantityDTO {

    

    public interface IMeasurableUnit {
        String getUnitName();
        String getMeasurementType();
    }

    public enum LengthUnit implements IMeasurableUnit {
        FEET, INCHES, YARDS, CENTIMETERS;
        @Override public String getUnitName()        { return this.name(); }
        @Override public String getMeasurementType() { return "LengthUnit"; }
    }

    public enum WeightUnit implements IMeasurableUnit {
        KILOGRAMS, GRAMS, POUNDS;
        @Override public String getUnitName()        { return this.name(); }
        @Override public String getMeasurementType() { return "WeightUnit"; }
    }

    public enum VolumeUnit implements IMeasurableUnit {
        LITRE, MILLILITRE, GALLON;
        @Override public String getUnitName()        { return this.name(); }
        @Override public String getMeasurementType() { return "VolumeUnit"; }
    }

    public enum TemperatureUnit implements IMeasurableUnit {
        CELSIUS, FAHRENHEIT;
        @Override public String getUnitName()        { return this.name(); }
        @Override public String getMeasurementType() { return "TemperatureUnit"; }
    }

   

    private double value;
    private String unit;
    private String measurementType;

    

    public QuantityDTO() {}

    public QuantityDTO(double value, IMeasurableUnit unit) {
        this.value           = value;
        this.unit            = unit.getUnitName();
        this.measurementType = unit.getMeasurementType();
    }

    public QuantityDTO(double value, String unit, String measurementType) {
        this.value           = value;
        this.unit            = unit;
        this.measurementType = measurementType;
    }

    

    public double getValue() { return value; }
    public void setValue(double value) { this.value = value; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public String getMeasurementType() { return measurementType; }
    public void setMeasurementType(String measurementType) {
        this.measurementType = measurementType;
    }

   

    @Override
    public String toString() {
        return value + " " + unit + " (" + measurementType + ")";
    }
}
