package com.example.quantitymeasurementapp.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.quantitymeasurementapp.dto.request.ArithmeticRequestDTO;
import com.example.quantitymeasurementapp.dto.request.CompareRequestDTO;
import com.example.quantitymeasurementapp.dto.request.ConvertRequestDTO;
import com.example.quantitymeasurementapp.entity.QuantityDTO;
import com.example.quantitymeasurementapp.entity.QuantityMeasurementEntity;
import com.example.quantitymeasurementapp.service.IQuantityMeasurementService;

@RestController
@RequestMapping("/api/measurements")
public class QuantityMeasurementController {

    private static final Logger logger =
            LoggerFactory.getLogger(QuantityMeasurementController.class);

    private final IQuantityMeasurementService service;

    public QuantityMeasurementController(IQuantityMeasurementService service) {
        this.service = service;
        logger.info("QuantityMeasurementController initialised");
    }

    // POST /api/measurements/compare
    @PostMapping("/compare")
    public ResponseEntity<Boolean> compare(@RequestBody CompareRequestDTO request) {
        logger.debug("POST /compare → {}", request);
        boolean result = service.compare(
                request.getThisQuantity(), request.getThatQuantity());
        return ResponseEntity.ok(result);
    }

    // POST /api/measurements/convert
    @PostMapping("/convert")
    public ResponseEntity<QuantityDTO> convert(@RequestBody ConvertRequestDTO request) {
        logger.debug("POST /convert → {}", request);
        QuantityDTO result = service.convert(
                request.getQuantity(), request.getTargetUnit());
        return ResponseEntity.ok(result);
    }

    // POST /api/measurements/add
    @PostMapping("/add")
    public ResponseEntity<QuantityDTO> add(@RequestBody ArithmeticRequestDTO request) {
        logger.debug("POST /add → {}", request);
        QuantityDTO result = (request.getTargetUnit() != null)
                ? service.add(request.getThisQuantity(),
                              request.getThatQuantity(),
                              request.getTargetUnit())
                : service.add(request.getThisQuantity(),
                              request.getThatQuantity());
        return ResponseEntity.ok(result);
    }

    // POST /api/measurements/subtract
    @PostMapping("/subtract")
    public ResponseEntity<QuantityDTO> subtract(@RequestBody ArithmeticRequestDTO request) {
        logger.debug("POST /subtract → {}", request);
        QuantityDTO result = (request.getTargetUnit() != null)
                ? service.subtract(request.getThisQuantity(),
                                   request.getThatQuantity(),
                                   request.getTargetUnit())
                : service.subtract(request.getThisQuantity(),
                                   request.getThatQuantity());
        return ResponseEntity.ok(result);
    }

    // POST /api/measurements/divide
    @PostMapping("/divide")
    public ResponseEntity<Double> divide(@RequestBody ArithmeticRequestDTO request) {
        logger.debug("POST /divide → {}", request);
        double result = service.divide(
                request.getThisQuantity(), request.getThatQuantity());
        return ResponseEntity.ok(result);
    }

    // GET /api/measurements/history
    @GetMapping("/history")
    public ResponseEntity<List<QuantityMeasurementEntity>> getHistory() {
        logger.debug("GET /history");
        return ResponseEntity.ok(service.getAllMeasurements());
    }

    // GET /api/measurements/history/{operation}
    @GetMapping("/history/{operation}")
    public ResponseEntity<List<QuantityMeasurementEntity>> getByOperation(
            @PathVariable String operation) {
        logger.debug("GET /history/{}", operation);
        return ResponseEntity.ok(
                service.getMeasurementsByOperation(operation.toUpperCase()));
    }
}
