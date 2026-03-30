package com.example.quantitymeasurementapp.dto.request;   

import com.example.quantitymeasurementapp.entity.QuantityDTO;

public class ConvertRequestDTO {

    private QuantityDTO quantity;
    private String targetUnit;

    public ConvertRequestDTO() {}

    public ConvertRequestDTO(QuantityDTO quantity, String targetUnit) {
        this.quantity   = quantity;
        this.targetUnit = targetUnit;
    }

    public QuantityDTO getQuantity() { return quantity; }
    public void setQuantity(QuantityDTO quantity) { this.quantity = quantity; }

    public String getTargetUnit() { return targetUnit; }
    public void setTargetUnit(String targetUnit) { this.targetUnit = targetUnit; }
}
