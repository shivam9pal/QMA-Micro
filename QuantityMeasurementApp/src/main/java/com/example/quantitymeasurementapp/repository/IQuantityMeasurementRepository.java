package com.example.quantitymeasurementapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.quantitymeasurementapp.entity.QuantityMeasurementEntity;




public interface IQuantityMeasurementRepository
        extends JpaRepository<QuantityMeasurementEntity, Long> {

    List<QuantityMeasurementEntity> findByOperation(String operation);
    List<QuantityMeasurementEntity> findByThisMeasurementType(String type);
    List<QuantityMeasurementEntity> findByIsError(boolean isError);
    long countByOperation(String operation);
}
