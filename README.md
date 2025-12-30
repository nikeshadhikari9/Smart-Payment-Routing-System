# Smart Payment Routing System

A Spring Boot application simulating intelligent wallet payment routing across multiple payment gateways. This project demonstrates rule-based gateway selection, transaction persistence, retry for failed payments, and health-based gateway management.

---

## Features

- Routes payments based on amount, priority, and gateway reliability.
- Persists transactions in an H2 in-memory database.
- Simulates success/failure of payments for testing purposes.
- Automatically retries failed transactions.
- Provides a REST API to test payments.
- Abstracted gateway simulation (eSewa, khalti, imepay).

---

## Tech Stack

- **Java 25**
- **Spring Boot 3.14**
- **Spring Web**
- **Spring Data JPA**
- **H2 Database** (in-memory database)
- **Maven** (build tool)

---

## Getting Started

### Prerequisites

Before you begin, ensure you have the following installed on your machine:

- **Java 24** or higher
- **Maven**
- **IDE** (IntelliJ IDEA)

### Setup

1. **Clone the repository**:
   ```bash
   git clone https://github.com/nikeshadhikari9/Smart-Payment-Routing-System.git
   ```

2. **Build the project**:
   ```bash
   mvn clean install
   ```

3. **Run the application**:
   ```bash
   mvn spring-boot:run
   ```

   The app will run on [http://localhost:8080](http://localhost:8080).

### H2 Database Console

- Access the H2 database console via [http://localhost:8080/h2-console](http://localhost:8080/h2-console).
- JDBC URL: `jdbc:h2:mem:payment`
- Username: `sa`
- Password: (leave empty)

You can inspect the `PAYMENT_TRANSACTION` and `ROUTING_RULE` tables here.

---

## API Usage

### Process a Payment

- **POST** `/payments`

  **Query Parameters**:
  - `amount`: Amount to be paid (e.g., `1500`).
  - `requestId`: Unique identifier for idempotency (e.g., `req1`).

  **Example**:
  ```bash
  curl -X POST "http://localhost:8080/payments?amount=1500&requestId=req1"
  ```

  **Response**:
  - `SUCCESS`: Payment successfully processed.
  - `FAILED`: Payment failed.

---

### Routing Rules Setup

You can configure routing rules using SQL in the H2 console. Below is an example of inserting routing rules:

**Example SQL**:
```sql
INSERT INTO ROUTING_RULE (min_amount, max_amount, gateway, priority) VALUES
(0, 100, 'imepay', 1),
(101, 500, 'khalti', 1),
(501, 999999, 'eSewa', 1);
```

This rule will route payments to:
- `imepay` for amounts between `0` and `100`.
- `khalti` for amounts between `101` and `500`.
- `eSewa` for amounts between `501` and `999999`.

---

## How It Works

1. **Payment Request**: The payment request arrives at the `PaymentController`.
2. **Idempotency Check**: The `PaymentService` ensures that the same request is not processed multiple times by checking its `requestId`.
3. **Gateway Selection**: The `RoutingRuleService` selects the appropriate gateway based on predefined rules.
4. **Gateway Health Check**: The `GatewayHealthService` checks whether the selected gateway is healthy and able to process the payment.
5. **Transaction Persistence**: The transaction details are stored in the H2 database.
6. **Retry Mechanism**: Failed transactions are automatically retried by the `RetryScheduler`.

---

## Notes

- **Simulation**: This project simulates the payment routing process. No real payments are processed.
- **Gateway Simulation**: The gateways are abstracted (imepay, khalti, eSewa).
- **Demonstration**: This project is designed to demonstrate object-oriented programming (OOP), Spring Boot, JDBC, and backend simulation logic.

---

## License

This project is licensed under the **MIT License**.
