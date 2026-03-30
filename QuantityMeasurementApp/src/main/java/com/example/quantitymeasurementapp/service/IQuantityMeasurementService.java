package com.example.quantitymeasurementapp.service;

import java.util.List;

import com.example.quantitymeasurementapp.entity.QuantityDTO;
import com.example.quantitymeasurementapp.entity.QuantityMeasurementEntity;

public interface IQuantityMeasurementService {

    boolean     compare (QuantityDTO thisQuantity, QuantityDTO thatQuantity);

    QuantityDTO convert (QuantityDTO quantity, String targetUnit);

    QuantityDTO add     (QuantityDTO thisQuantity, QuantityDTO thatQuantity);
    QuantityDTO add     (QuantityDTO thisQuantity, QuantityDTO thatQuantity, String targetUnit);

    QuantityDTO subtract(QuantityDTO thisQuantity, QuantityDTO thatQuantity);
    QuantityDTO subtract(QuantityDTO thisQuantity, QuantityDTO thatQuantity, String targetUnit);

    double      divide  (QuantityDTO thisQuantity, QuantityDTO thatQuantity);

    List<QuantityMeasurementEntity> getAllMeasurements();
    List<QuantityMeasurementEntity> getMeasurementsByOperation(String operation);

}
