package com.example.quantitymeasurementapp.dto.request;   
import com.example.quantitymeasurementapp.entity.QuantityDTO;

public class ArithmeticRequestDTO {

    private QuantityDTO thisQuantity;
    private QuantityDTO thatQuantity;
    private String targetUnit; 

    public ArithmeticRequestDTO() {}

    public ArithmeticRequestDTO(QuantityDTO thisQuantity,
                                QuantityDTO thatQuantity,
                                String targetUnit) {
        this.thisQuantity = thisQuantity;
        this.thatQuantity = thatQuantity;
        this.targetUnit   = targetUnit;
    }

    public QuantityDTO getThisQuantity() { return thisQuantity; }
    public void setThisQuantity(QuantityDTO thisQuantity) { this.thisQuantity = thisQuantity; }

    public QuantityDTO getThatQuantity() { return thatQuantity; }
    public void setThatQuantity(QuantityDTO thatQuantity) { this.thatQuantity = thatQuantity; }

    public String getTargetUnit() { return targetUnit; }
    public void setTargetUnit(String targetUnit) { this.targetUnit = targetUnit; }
}
