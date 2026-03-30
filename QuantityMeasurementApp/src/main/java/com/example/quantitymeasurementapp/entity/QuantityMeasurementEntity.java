package com.example.quantitymeasurementapp.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "quantity_measurement_entity")
public class QuantityMeasurementEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "this_value", nullable = false)
    private double thisValue;

    @Column(name = "this_unit", nullable = false, length = 50)
    private String thisUnit;

    @Column(name = "this_measurement_type", nullable = false, length = 50)
    private String thisMeasurementType;

    @Column(name = "that_value")
    private Double thatValue;

    @Column(name = "that_unit", length = 50)
    private String thatUnit;

    @Column(name = "that_measurement_type", length = 50)
    private String thatMeasurementType;

    @Column(name = "operation", nullable = false, length = 20)
    private String operation;

    @Column(name = "result_value")
    private Double resultValue;

    @Column(name = "result_unit", length = 50)
    private String resultUnit;

    @Column(name = "result_measurement_type", length = 50)
    private String resultMeasurementType;

    @Column(name = "result_string", length = 255)
    private String resultString;

    @Column(name = "is_error")
    private boolean isError;

    @Column(name = "error_message", length = 500)
    private String errorMessage;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // ── Required by JPA ───────────────────────────────────────────────────────
    public QuantityMeasurementEntity() {}

    // ── Private constructor used by Builder ───────────────────────────────────
    private QuantityMeasurementEntity(Builder builder) {
        this.thisValue             = builder.thisValue;
        this.thisUnit              = builder.thisUnit;
        this.thisMeasurementType   = builder.thisMeasurementType;
        this.thatValue             = builder.thatValue;
        this.thatUnit              = builder.thatUnit;
        this.thatMeasurementType   = builder.thatMeasurementType;
        this.operation             = builder.operation;
        this.resultValue           = builder.resultValue;
        this.resultUnit            = builder.resultUnit;
        this.resultMeasurementType = builder.resultMeasurementType;
        this.resultString          = builder.resultString;
        this.isError               = builder.isError;
        this.errorMessage          = builder.errorMessage;
    }

    // ── Static entry point ────────────────────────────────────────────────────
    public static Builder builder() {
        return new Builder();
    }

    // =========================================================================
    // BUILDER
    // =========================================================================
    public static class Builder {

        private double  thisValue;
        private String  thisUnit;
        private String  thisMeasurementType;
        private Double  thatValue;
        private String  thatUnit;
        private String  thatMeasurementType;
        private String  operation;
        private Double  resultValue;
        private String  resultUnit;
        private String  resultMeasurementType;
        private String  resultString;
        private boolean isError;
        private String  errorMessage;

        public Builder thisValue(double thisValue) {
            this.thisValue = thisValue; return this;
        }
        public Builder thisUnit(String thisUnit) {
            this.thisUnit = thisUnit; return this;
        }
        public Builder thisMeasurementType(String thisMeasurementType) {
            this.thisMeasurementType = thisMeasurementType; return this;
        }
        public Builder thatValue(Double thatValue) {
            this.thatValue = thatValue; return this;
        }
        public Builder thatUnit(String thatUnit) {
            this.thatUnit = thatUnit; return this;
        }
        public Builder thatMeasurementType(String thatMeasurementType) {
            this.thatMeasurementType = thatMeasurementType; return this;
        }
        public Builder operation(String operation) {
            this.operation = operation; return this;
        }
        public Builder resultValue(Double resultValue) {
            this.resultValue = resultValue; return this;
        }
        public Builder resultUnit(String resultUnit) {
            this.resultUnit = resultUnit; return this;
        }
        public Builder resultMeasurementType(String resultMeasurementType) {
            this.resultMeasurementType = resultMeasurementType; return this;
        }
        public Builder resultString(String resultString) {
            this.resultString = resultString; return this;
        }
        public Builder isError(boolean isError) {
            this.isError = isError; return this;
        }
        public Builder errorMessage(String errorMessage) {
            this.errorMessage = errorMessage; return this;
        }

        public QuantityMeasurementEntity build() {
            return new QuantityMeasurementEntity(this);
        }
    }

    // =========================================================================
    // GETTERS & SETTERS
    // =========================================================================

    public Long getId()                        { return id; }
    public void setId(Long id)                 { this.id = id; }

    public double getThisValue()               { return thisValue; }
    public void setThisValue(double thisValue) { this.thisValue = thisValue; }

    public String getThisUnit()                { return thisUnit; }
    public void setThisUnit(String thisUnit)   { this.thisUnit = thisUnit; }

    public String getThisMeasurementType()     { return thisMeasurementType; }
    public void setThisMeasurementType(String thisMeasurementType) {
        this.thisMeasurementType = thisMeasurementType;
    }

    public Double getThatValue()               { return thatValue; }
    public void setThatValue(Double thatValue) { this.thatValue = thatValue; }

    public String getThatUnit()                { return thatUnit; }
    public void setThatUnit(String thatUnit)   { this.thatUnit = thatUnit; }

    public String getThatMeasurementType()     { return thatMeasurementType; }
    public void setThatMeasurementType(String thatMeasurementType) {
        this.thatMeasurementType = thatMeasurementType;
    }

    public String getOperation()               { return operation; }
    public void setOperation(String operation) { this.operation = operation; }

    public Double getResultValue()             { return resultValue; }
    public void setResultValue(Double resultValue) { this.resultValue = resultValue; }

    public String getResultUnit()              { return resultUnit; }
    public void setResultUnit(String resultUnit) { this.resultUnit = resultUnit; }

    public String getResultMeasurementType()   { return resultMeasurementType; }
    public void setResultMeasurementType(String resultMeasurementType) {
        this.resultMeasurementType = resultMeasurementType;
    }

    public String getResultString()            { return resultString; }
    public void setResultString(String resultString) { this.resultString = resultString; }

    public boolean isError()                   { return isError; }
    public void setError(boolean isError)      { this.isError = isError; }

    public String getErrorMessage()            { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }

    public LocalDateTime getCreatedAt()        { return createdAt; }
    public LocalDateTime getUpdatedAt()        { return updatedAt; }

    @Override
    public String toString() {
        return "QuantityMeasurementEntity{" +
                "id=" + id +
                ", operation='" + operation + '\'' +
                ", thisValue=" + thisValue +
                ", thisUnit='" + thisUnit + '\'' +
                ", resultValue=" + resultValue +
                ", isError=" + isError +
                '}';
    }
}
