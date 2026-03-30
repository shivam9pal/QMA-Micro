# Quantity Measurement API Documentation

## Overview
The Quantity Measurement Application is a Spring Boot REST API that provides functionality to compare, convert, and perform arithmetic operations on various physical quantities (length, weight, volume, and temperature).

---

## Base URL
```
http://localhost:8080
```

---

## Authentication

### 1. User Login
**Endpoint:** `POST /auth/login`

**Description:** Authenticate a user and receive a JWT token for accessing protected resources.

**Request Body:**
```json
{
  "username": "string",
  "password": "string"
}
```

**Example Request:**
```json
{
  "username": "john_doe",
  "password": "securePassword123"
}
```

**Response (200 OK):**
```json
{
  "jwt": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "id": 1
}
```

**Error Responses:**
- `401 Unauthorized` - Invalid credentials
- `400 Bad Request` - Invalid request format

---

### 2. User Signup
**Endpoint:** `POST /auth/signup`

**Description:** Register a new user account.

**Request Body:**
```json
{
  "username": "string",
  "password": "string"
}
```

**Example Request:**
```json
{
  "username": "jane_doe",
  "password": "securePassword123"
}
```

**Response (200 OK):**
```json
{
  "id": 2,
  "username": "jane_doe"
}
```

**Error Responses:**
- `400 Bad Request` - User already exists or invalid input
- `422 Unprocessable Entity` - Invalid data format

---

## Quantity Measurement Endpoints

### QuantityDTO Structure
All measurement endpoints use the QuantityDTO for input and output. The structure is:

```json
{
  "value": number,
  "unit": "string",
  "measurementType": "string"
}
```

**Supported Units:**

#### Length Units
- `FEET`, `INCHES`, `YARDS`, `CENTIMETERS`
- Measurement Type: `LengthUnit`

#### Weight Units
- `KILOGRAMS`, `GRAMS`, `POUNDS`
- Measurement Type: `WeightUnit`

#### Volume Units
- `LITRE`, `MILLILITRE`, `GALLON`
- Measurement Type: `VolumeUnit`

#### Temperature Units
- `CELSIUS`, `FAHRENHEIT`
- Measurement Type: `TemperatureUnit`

---

### 1. Compare Two Quantities
**Endpoint:** `POST /api/measurements/compare`

**Description:** Compare if two quantities are equal.

**Request Body:**
```json
{
  "thisQuantity": {
    "value": number,
    "unit": "string",
    "measurementType": "string"
  },
  "thatQuantity": {
    "value": number,
    "unit": "string",
    "measurementType": "string"
  }
}
```

**Example Request:**
```json
{
  "thisQuantity": {
    "value": 1,
    "unit": "FEET",
    "measurementType": "LengthUnit"
  },
  "thatQuantity": {
    "value": 12,
    "unit": "INCHES",
    "measurementType": "LengthUnit"
  }
}
```

**Response (200 OK):**
```json
true
```

---

### 2. Convert Quantity
**Endpoint:** `POST /api/measurements/convert`

**Description:** Convert a quantity from one unit to another.

**Request Body:**
```json
{
  "quantity": {
    "value": number,
    "unit": "string",
    "measurementType": "string"
  },
  "targetUnit": "string"
}
```

**Example Request:**
```json
{
  "quantity": {
    "value": 5,
    "unit": "FEET",
    "measurementType": "LengthUnit"
  },
  "targetUnit": "INCHES"
}
```

**Response (200 OK):**
```json
{
  "value": 60,
  "unit": "INCHES",
  "measurementType": "LengthUnit"
}
```

---

### 3. Add Two Quantities
**Endpoint:** `POST /api/measurements/add`

**Description:** Add two quantities. If targetUnit is provided, result is converted to that unit.

**Request Body:**
```json
{
  "thisQuantity": {
    "value": number,
    "unit": "string",
    "measurementType": "string"
  },
  "thatQuantity": {
    "value": number,
    "unit": "string",
    "measurementType": "string"
  },
  "targetUnit": "string (optional)"
}
```

**Example Request (without targetUnit):**
```json
{
  "thisQuantity": {
    "value": 5,
    "unit": "FEET",
    "measurementType": "LengthUnit"
  },
  "thatQuantity": {
    "value": 1,
    "unit": "FEET",
    "measurementType": "LengthUnit"
  }
}
```

**Response (200 OK):**
```json
{
  "value": 6,
  "unit": "FEET",
  "measurementType": "LengthUnit"
}
```

**Example Request (with targetUnit):**
```json
{
  "thisQuantity": {
    "value": 1,
    "unit": "FEET",
    "measurementType": "LengthUnit"
  },
  "thatQuantity": {
    "value": 12,
    "unit": "INCHES",
    "measurementType": "LengthUnit"
  },
  "targetUnit": "INCHES"
}
```

**Response (200 OK):**
```json
{
  "value": 24,
  "unit": "INCHES",
  "measurementType": "LengthUnit"
}
```

---

### 4. Subtract Two Quantities
**Endpoint:** `POST /api/measurements/subtract`

**Description:** Subtract one quantity from another. If targetUnit is provided, result is converted to that unit.

**Request Body:**
```json
{
  "thisQuantity": {
    "value": number,
    "unit": "string",
    "measurementType": "string"
  },
  "thatQuantity": {
    "value": number,
    "unit": "string",
    "measurementType": "string"
  },
  "targetUnit": "string (optional)"
}
```

**Example Request:**
```json
{
  "thisQuantity": {
    "value": 10,
    "unit": "FEET",
    "measurementType": "LengthUnit"
  },
  "thatQuantity": {
    "value": 1,
    "unit": "FEET",
    "measurementType": "LengthUnit"
  }
}
```

**Response (200 OK):**
```json
{
  "value": 9,
  "unit": "FEET",
  "measurementType": "LengthUnit"
}
```

---

### 5. Divide Two Quantities
**Endpoint:** `POST /api/measurements/divide`

**Description:** Divide one quantity by another and return the numeric result.

**Request Body:**
```json
{
  "thisQuantity": {
    "value": number,
    "unit": "string",
    "measurementType": "string"
  },
  "thatQuantity": {
    "value": number,
    "unit": "string",
    "measurementType": "string"
  }
}
```

**Example Request:**
```json
{
  "thisQuantity": {
    "value": 10,
    "unit": "FEET",
    "measurementType": "LengthUnit"
  },
  "thatQuantity": {
    "value": 2,
    "unit": "FEET",
    "measurementType": "LengthUnit"
  }
}
```

**Response (200 OK):**
```json
5.0
```

---

### 6. Get All Measurements History
**Endpoint:** `GET /api/measurements/history`

**Description:** Retrieve the complete history of all measurement operations performed.

**Response (200 OK):**
```json
[
  {
    "id": 1,
    "thisValue": 1,
    "thisUnit": "FEET",
    "thisMeasurementType": "LengthUnit",
    "thatValue": 12,
    "thatUnit": "INCHES",
    "thatMeasurementType": "LengthUnit",
    "operation": "COMPARE",
    "resultValue": null,
    "resultUnit": null,
    "resultMeasurementType": null,
    "resultString": "true",
    "isError": false
  },
  {
    "id": 2,
    "thisValue": 5,
    "thisUnit": "FEET",
    "thisMeasurementType": "LengthUnit",
    "thatValue": null,
    "thatUnit": null,
    "thatMeasurementType": null,
    "operation": "CONVERT",
    "resultValue": 60,
    "resultUnit": "INCHES",
    "resultMeasurementType": "LengthUnit",
    "resultString": "60.0 INCHES",
    "isError": false
  }
]
```

---

### 7. Get Measurements History by Operation
**Endpoint:** `GET /api/measurements/history/{operation}`

**Description:** Retrieve measurement history filtered by operation type.

**Path Parameters:**
- `operation` (string) - The operation type to filter by: `COMPARE`, `CONVERT`, `ADD`, `SUBTRACT`, `DIVIDE`

**Example Request:**
```
GET /api/measurements/history/CONVERT
```

**Response (200 OK):**
```json
[
  {
    "id": 2,
    "thisValue": 5,
    "thisUnit": "FEET",
    "thisMeasurementType": "LengthUnit",
    "thatValue": null,
    "thatUnit": null,
    "thatMeasurementType": null,
    "operation": "CONVERT",
    "resultValue": 60,
    "resultUnit": "INCHES",
    "resultMeasurementType": "LengthUnit",
    "resultString": "60.0 INCHES",
    "isError": false
  }
]
```

---

## Common Error Responses

### 400 Bad Request
```json
{
  "error": "Invalid request format",
  "message": "Required field missing or invalid"
}
```

### 401 Unauthorized
```json
{
  "error": "Authentication failed",
  "message": "Invalid credentials or missing token"
}
```

### 404 Not Found
```json
{
  "error": "Resource not found",
  "message": "The requested resource does not exist"
}
```

### 500 Internal Server Error
```json
{
  "error": "Internal server error",
  "message": "An unexpected error occurred"
}
```

---

## Testing with cURL

### Login Example
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john_doe",
    "password": "securePassword123"
  }'
```

### Compare Quantities Example
```bash
curl -X POST http://localhost:8080/api/measurements/compare \
  -H "Content-Type: application/json" \
  -d '{
    "thisQuantity": {
      "value": 1,
      "unit": "FEET",
      "measurementType": "LengthUnit"
    },
    "thatQuantity": {
      "value": 12,
      "unit": "INCHES",
      "measurementType": "LengthUnit"
    }
  }'
```

### Convert Unit Example
```bash
curl -X POST http://localhost:8080/api/measurements/convert \
  -H "Content-Type: application/json" \
  -d '{
    "quantity": {
      "value": 5,
      "unit": "FEET",
      "measurementType": "LengthUnit"
    },
    "targetUnit": "INCHES"
  }'
```

### Get History Example
```bash
curl -X GET http://localhost:8080/api/measurements/history
```

### Get History by Operation Example
```bash
curl -X GET http://localhost:8080/api/measurements/history/CONVERT
```

---

## Notes

1. **Authentication:** Most endpoints may require JWT token in the Authorization header:
   ```
   Authorization: Bearer <JWT_TOKEN>
   ```

2. **Unit Conversion:** Units can only be converted between the same measurement type. For example, you cannot convert FEET (LengthUnit) to KILOGRAMS (WeightUnit).

3. **Error Handling:** The API includes comprehensive error handling. Check the response status code and error message for more details.

4. **Response Format:** All responses are in JSON format.

5. **Database Logging:** All operations are logged to the database for audit and history tracking.

---

## Database Entity Structure

### QuantityMeasurementEntity
- `id` - Unique identifier
- `thisValue`, `thisUnit`, `thisMeasurementType` - First quantity
- `thatValue`, `thatUnit`, `thatMeasurementType` - Second quantity (if applicable)
- `operation` - The operation performed (COMPARE, CONVERT, ADD, SUBTRACT, DIVIDE)
- `resultValue`, `resultUnit`, `resultMeasurementType` - Result of the operation
- `resultString` - String representation of result
- `isError` - Whether the operation resulted in an error
