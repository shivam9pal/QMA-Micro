package com.example.quantitymeasurementapp.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.quantitymeasurementapp.domain.Quantity;
import com.example.quantitymeasurementapp.entity.QuantityDTO;
import com.example.quantitymeasurementapp.entity.QuantityMeasurementEntity;
import com.example.quantitymeasurementapp.entity.QuantityModel;
import com.example.quantitymeasurementapp.exception.QuantityMeasurementException;
import com.example.quantitymeasurementapp.repository.IQuantityMeasurementRepository;
import com.example.quantitymeasurementapp.unit.IMeasurable;
import com.example.quantitymeasurementapp.unit.LengthUnit;
import com.example.quantitymeasurementapp.unit.TemperatureUnit;
import com.example.quantitymeasurementapp.unit.VolumeUnit;
import com.example.quantitymeasurementapp.unit.WeightUnit;

@Service
public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

    private static final Logger logger =
            LoggerFactory.getLogger(QuantityMeasurementServiceImpl.class);

    private final IQuantityMeasurementRepository repository;

    public QuantityMeasurementServiceImpl(IQuantityMeasurementRepository repository) {
        this.repository = repository;
        logger.info("QuantityMeasurementServiceImpl initialised");
    }

    // ── Unit Resolution ───────────────────────────────────────────────────────

    private IMeasurable resolveUnit(String measurementType, String unitName) {
        logger.debug("resolveUnit: type={}, unit={}", measurementType, unitName);
        return switch (measurementType) {
            case "LengthUnit"      -> LengthUnit.FEET.getUnitInstance(unitName);
            case "WeightUnit"      -> WeightUnit.KILOGRAMS.getUnitInstance(unitName);
            case "VolumeUnit"      -> VolumeUnit.LITRE.getUnitInstance(unitName);
            case "TemperatureUnit" -> TemperatureUnit.CELSIUS.getUnitInstance(unitName);
            default -> throw new QuantityMeasurementException(
                    "Unknown measurement type: " + measurementType);
        };
    }

    private QuantityModel<IMeasurable> toModel(QuantityDTO dto) {
        if (dto == null)
            throw new QuantityMeasurementException("QuantityDTO cannot be null");
        IMeasurable unit = resolveUnit(dto.getMeasurementType(), dto.getUnit());
        return new QuantityModel<IMeasurable>(dto.getValue(), unit);
    }

    private QuantityDTO toDTO(QuantityModel<IMeasurable> model) {
        return new QuantityDTO(
                model.getValue(),
                model.getUnit().getUnitName(),
                model.getUnit().getMeasurementType()
        );
    }

    

    private QuantityMeasurementEntity buildEntity(
        QuantityModel<IMeasurable> m1,
        QuantityModel<IMeasurable> m2,
        String operation,
        String resultString,
        QuantityModel<IMeasurable> resultModel,
        boolean isError,
        String errorMessage) {

    
    QuantityMeasurementEntity.Builder b =
            QuantityMeasurementEntity.builder()
                    .thisValue(m1.getValue())
                    .thisUnit(m1.getUnit().getUnitName())
                    .thisMeasurementType(m1.getUnit().getMeasurementType())
                    .operation(operation)
                    .isError(isError)
                    .errorMessage(errorMessage)
                    .resultString(resultString);

    if (m2 != null) {
        b.thatValue(m2.getValue())
         .thatUnit(m2.getUnit().getUnitName())
         .thatMeasurementType(m2.getUnit().getMeasurementType());
    }
    if (resultModel != null) {
        b.resultValue(resultModel.getValue())
         .resultUnit(resultModel.getUnit().getUnitName())
         .resultMeasurementType(resultModel.getUnit().getMeasurementType());
    }

    return b.build();
}


    

    @Override
    public boolean compare(QuantityDTO dto1, QuantityDTO dto2) {
        logger.debug("compare: {} vs {}", dto1, dto2);
        QuantityModel<IMeasurable> m1 = toModel(dto1);
        QuantityModel<IMeasurable> m2 = toModel(dto2);
        try {
            if (!m1.getUnit().getClass().equals(m2.getUnit().getClass())) {
                repository.save(buildEntity(m1, m2, "COMPARE", "Not Equal", null, false, null));
                return false;
            }
            boolean result = Math.abs(
                    m1.getUnit().convertToBaseUnit(m1.getValue()) -
                    m2.getUnit().convertToBaseUnit(m2.getValue())) <= 1e-5;

            repository.save(buildEntity(m1, m2, "COMPARE",
                    result ? "Equal" : "Not Equal", null, false, null));
            logger.debug("compare result: {}", result);
            return result;
        } catch (Exception e) {
            logger.error("compare failed: {}", e.getMessage());
            repository.save(buildEntity(m1, m2, "COMPARE", null, null, true, e.getMessage()));
            throw new QuantityMeasurementException("Comparison failed: " + e.getMessage(), e);
        }
    }

    

    @Override
    @SuppressWarnings("unchecked")
    public QuantityDTO convert(QuantityDTO dto, String targetUnitName) {
        logger.debug("convert: {} → {}", dto, targetUnitName);
        QuantityModel<IMeasurable> m = toModel(dto);
        try {
            IMeasurable targetUnit = resolveUnit(dto.getMeasurementType(), targetUnitName);
            Quantity<IMeasurable> q = new Quantity<IMeasurable>(m.getValue(), m.getUnit());
            Quantity<IMeasurable> converted = q.convertTo(targetUnit);

            QuantityModel<IMeasurable> resultModel =
                    new QuantityModel<IMeasurable>(converted.getValue(), converted.getUnit());
            QuantityModel<IMeasurable> targetModel =
                    new QuantityModel<IMeasurable>(0.0, targetUnit);

            repository.save(buildEntity(m, targetModel, "CONVERT", null, resultModel, false, null));
            logger.debug("convert result: {}", resultModel);
            return toDTO(resultModel);
        } catch (Exception e) {
            logger.error("convert failed: {}", e.getMessage());
            repository.save(buildEntity(m, null, "CONVERT", null, null, true, e.getMessage()));
            throw new QuantityMeasurementException("Conversion failed: " + e.getMessage(), e);
        }
    }

    

    @Override
    public QuantityDTO add(QuantityDTO dto1, QuantityDTO dto2) {
        logger.debug("add: {} + {}", dto1, dto2);
        QuantityModel<IMeasurable> m1 = toModel(dto1);
        QuantityModel<IMeasurable> m2 = toModel(dto2);
        try {
            Quantity<IMeasurable> result =
                    new Quantity<IMeasurable>(m1.getValue(), m1.getUnit())
                    .add(new Quantity<IMeasurable>(m2.getValue(), m2.getUnit()));

            QuantityModel<IMeasurable> resultModel =
                    new QuantityModel<IMeasurable>(result.getValue(), result.getUnit());
            repository.save(buildEntity(m1, m2, "ADD", null, resultModel, false, null));
            return toDTO(resultModel);
        } catch (Exception e) {
            logger.error("add failed: {}", e.getMessage());
            repository.save(buildEntity(m1, m2, "ADD", null, null, true, e.getMessage()));
            throw new QuantityMeasurementException("Addition failed: " + e.getMessage(), e);
        }
    }

    @Override
    public QuantityDTO add(QuantityDTO dto1, QuantityDTO dto2, String targetUnitName) {
        logger.debug("add with target: {} + {} → {}", dto1, dto2, targetUnitName);
        QuantityModel<IMeasurable> m1 = toModel(dto1);
        QuantityModel<IMeasurable> m2 = toModel(dto2);
        try {
            IMeasurable targetUnit = resolveUnit(dto1.getMeasurementType(), targetUnitName);
            Quantity<IMeasurable> result =
                    new Quantity<IMeasurable>(m1.getValue(), m1.getUnit())
                    .add(new Quantity<IMeasurable>(m2.getValue(), m2.getUnit()), targetUnit);

            QuantityModel<IMeasurable> resultModel =
                    new QuantityModel<IMeasurable>(result.getValue(), result.getUnit());
            repository.save(buildEntity(m1, m2, "ADD", null, resultModel, false, null));
            return toDTO(resultModel);
        } catch (Exception e) {
            logger.error("add with target failed: {}", e.getMessage());
            repository.save(buildEntity(m1, m2, "ADD", null, null, true, e.getMessage()));
            throw new QuantityMeasurementException("Addition failed: " + e.getMessage(), e);
        }
    }

    

    @Override
    public QuantityDTO subtract(QuantityDTO dto1, QuantityDTO dto2) {
        logger.debug("subtract: {} - {}", dto1, dto2);
        QuantityModel<IMeasurable> m1 = toModel(dto1);
        QuantityModel<IMeasurable> m2 = toModel(dto2);
        try {
            Quantity<IMeasurable> result =
                    new Quantity<IMeasurable>(m1.getValue(), m1.getUnit())
                    .subtract(new Quantity<IMeasurable>(m2.getValue(), m2.getUnit()));

            QuantityModel<IMeasurable> resultModel =
                    new QuantityModel<IMeasurable>(result.getValue(), result.getUnit());
            repository.save(buildEntity(m1, m2, "SUBTRACT", null, resultModel, false, null));
            return toDTO(resultModel);
        } catch (Exception e) {
            logger.error("subtract failed: {}", e.getMessage());
            repository.save(buildEntity(m1, m2, "SUBTRACT", null, null, true, e.getMessage()));
            throw new QuantityMeasurementException("Subtraction failed: " + e.getMessage(), e);
        }
    }

    @Override
    public QuantityDTO subtract(QuantityDTO dto1, QuantityDTO dto2, String targetUnitName) {
        logger.debug("subtract with target: {} - {} → {}", dto1, dto2, targetUnitName);
        QuantityModel<IMeasurable> m1 = toModel(dto1);
        QuantityModel<IMeasurable> m2 = toModel(dto2);
        try {
            IMeasurable targetUnit = resolveUnit(dto1.getMeasurementType(), targetUnitName);
            Quantity<IMeasurable> result =
                    new Quantity<IMeasurable>(m1.getValue(), m1.getUnit())
                    .subtract(new Quantity<IMeasurable>(m2.getValue(), m2.getUnit()), targetUnit);

            QuantityModel<IMeasurable> resultModel =
                    new QuantityModel<IMeasurable>(result.getValue(), result.getUnit());
            repository.save(buildEntity(m1, m2, "SUBTRACT", null, resultModel, false, null));
            return toDTO(resultModel);
        } catch (Exception e) {
            logger.error("subtract with target failed: {}", e.getMessage());
            repository.save(buildEntity(m1, m2, "SUBTRACT", null, null, true, e.getMessage()));
            throw new QuantityMeasurementException("Subtraction failed: " + e.getMessage(), e);
        }
    }

    

    @Override
    public double divide(QuantityDTO dto1, QuantityDTO dto2) {
        logger.debug("divide: {} / {}", dto1, dto2);
        QuantityModel<IMeasurable> m1 = toModel(dto1);
        QuantityModel<IMeasurable> m2 = toModel(dto2);
        try {
            double result =
                    new Quantity<IMeasurable>(m1.getValue(), m1.getUnit())
                    .divide(new Quantity<IMeasurable>(m2.getValue(), m2.getUnit()));

            repository.save(buildEntity(m1, m2, "DIVIDE",
                    String.valueOf(result), null, false, null));
            logger.debug("divide result: {}", result);
            return result;
        } catch (Exception e) {
            logger.error("divide failed: {}", e.getMessage());
            repository.save(buildEntity(m1, m2, "DIVIDE", null, null, true, e.getMessage()));
            throw new QuantityMeasurementException("Division failed: " + e.getMessage(), e);
        }
    }

    
    @Override
    public List<QuantityMeasurementEntity> getAllMeasurements() {
        logger.debug("getAllMeasurements called");
        return repository.findAll();
    }

    @Override
    public List<QuantityMeasurementEntity> getMeasurementsByOperation(String operation) {
        logger.debug("getMeasurementsByOperation: {}", operation);
        return repository.findByOperation(operation);
    }
}
