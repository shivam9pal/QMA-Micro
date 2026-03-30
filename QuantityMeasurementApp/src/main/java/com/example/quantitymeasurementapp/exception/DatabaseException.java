package com.example.quantitymeasurementapp.exception;

public class DatabaseException extends QuantityMeasurementException {

    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public static DatabaseException connectionFailed(String details, Throwable cause) {
        return new DatabaseException("DB connection failed: " + details, cause);
    }

    public static DatabaseException queryFailed(String query, Throwable cause) {
        return new DatabaseException("Query failed: " + query, cause);
    }

    public static DatabaseException transactionFailed(String op, Throwable cause) {
        return new DatabaseException("Transaction failed during: " + op, cause);
    }
}
