
# Spring Boot Calculator

**Name:** Shahrier Mokammel\
**ID:** IT24050

A web-based calculator built with Spring Boot, Java 17, and a simple
HTML/CSS/JavaScript interface.
:::

------------------------------------------------------------------------

## Screenshots

> Before pushing to GitHub, create a folder named `screenshots` in the
> project root and place the two screenshot files inside it using the
> filenames shown below.

### IntelliJ IDEA -- Project / Console

![IntelliJ IDEA Screenshot](screenshots/Screenshot-2026-11-22-235536.png)

![Spring Boot Calculator Screenshot](screenshots/Screenshot-2026-11-22-235837.png)

### Spring Boot Calculator -- Browser

![Spring Boot Calculator
Screenshot](screenshots/Screenshot-2026-11-22-235837.png)

------------------------------------------------------------------------

## Project Overview

This project is a calculator application developed using **Spring
Boot**. It provides a browser-based calculator interface and a REST API
to perform basic arithmetic and trigonometric calculations.

## Features

-   Addition, subtraction, multiplication, and division
-   Power and square-root calculations
-   Trigonometric functions: `sin`, `cos`, `tan`
-   Inverse trigonometric functions: `asin`, `acos`, `atan`
-   Angle selection in degrees or radians
-   Error handling for invalid operations, such as division by zero
-   Responsive calculator interface
-   Unit and controller tests using JUnit 5 and MockMvc

## Technologies Used

  Technology              Purpose
  ----------------------- ---------------------------------
  Java 17                 Programming language
  Spring Boot 3.3.5       Backend framework
  Spring Web              REST API
  Maven                   Dependency and build management
  HTML, CSS, JavaScript   Frontend interface
  JUnit 5                 Unit testing
  MockMvc                 Controller testing
  IntelliJ IDEA           Development environment

## Project Structure

``` text
calculator/
├── src/
│   ├── main/
│   │   ├── java/com/example/calculator/
│   │   │   ├── CalculatorApplication.java
│   │   │   ├── controller/
│   │   │   │   ├── CalculatorController.java
│   │   │   │   └── GlobalExceptionHandler.java
│   │   │   └── service/
│   │   │       └── CalculatorService.java
│   │   └── resources/
│   │       └── static/
│   │           └── index.html
│   └── test/
│       └── java/com/example/calculator/
│           ├── CalculatorControllerTest.java
│           └── CalculatorServiceTest.java
├── screenshots/
├── pom.xml
└── README.md
```

## How to Run the Project

### Requirements

-   Java JDK 17 or later
-   IntelliJ IDEA (or another Java IDE)
-   Maven

### Run using IntelliJ IDEA

1.  Download or clone this repository.
2.  Open the project folder containing `pom.xml` in IntelliJ IDEA.
3.  Wait for Maven to download the dependencies.
4.  Open `CalculatorApplication.java`.
5.  Click the Run button beside the `main()` method.
6.  Open your browser and visit:

``` text
http://localhost:8080
```

### Run using the terminal

Run the following command from the project directory:

``` bash
mvn spring-boot:run
```

## REST API Endpoints

All calculator endpoints use the base path `/api/calc`.

  Operation         HTTP GET endpoint
  ----------------- ------------------------------------------
  Addition          `/api/calc/add?a=10&b=5`
  Subtraction       `/api/calc/subtract?a=10&b=5`
  Multiplication    `/api/calc/multiply?a=10&b=5`
  Division          `/api/calc/divide?a=10&b=5`
  Power             `/api/calc/power?a=2&b=3`
  Square root       `/api/calc/sqrt?a=16`
  Sine              `/api/calc/trig/sin?value=30&unit=deg`
  Cosine            `/api/calc/trig/cos?value=60&unit=deg`
  Tangent           `/api/calc/trig/tan?value=45&unit=deg`
  Inverse sine      `/api/calc/trig/asin?value=0.5&unit=deg`
  Inverse cosine    `/api/calc/trig/acos?value=0.5&unit=deg`
  Inverse tangent   `/api/calc/trig/atan?value=1&unit=deg`

For trigonometric endpoints, `unit` can be `deg` or `rad`. If omitted,
it defaults to `deg`.

### Example API response

Request:

``` text
GET http://localhost:8080/api/calc/add?a=2&b=3
```

Response:

``` json
{
  "operation": "add",
  "result": 5.0
}
```

Errors are returned with HTTP status `400 Bad Request` and an error
message in JSON format.

## Source Code

The following sections show the main Java source files directly in this
README.

### 1. CalculatorApplication.java

``` java
package com.example.calculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CalculatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(CalculatorApplication.class, args);
    }
}
```

### 2. CalculatorService.java

This service contains the calculator's mathematical operations.

``` java
package com.example.calculator.service;

import org.springframework.stereotype.Service;

@Service
public class CalculatorService {

    public double add(double a, double b) {
        return a + b;
    }

    public double subtract(double a, double b) {
        return a - b;
    }

    public double multiply(double a, double b) {
        return a * b;
    }

    public double divide(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("Cannot divide by zero");
        }
        return a / b;
    }

    public double power(double base, double exponent) {
        return Math.pow(base, exponent);
    }

    public double sqrt(double a) {
        if (a < 0) {
            throw new ArithmeticException(
                "Cannot take square root of a negative number"
            );
        }
        return Math.sqrt(a);
    }

    public double sin(double value, boolean degrees) {
        return Math.sin(toRadians(value, degrees));
    }

    public double cos(double value, boolean degrees) {
        return Math.cos(toRadians(value, degrees));
    }

    public double tan(double value, boolean degrees) {
        double rad = toRadians(value, degrees);
        if (Math.abs(Math.cos(rad)) < 1e-12) {
            throw new ArithmeticException(
                "tan is undefined for this angle"
            );
        }
        return Math.tan(rad);
    }

    public double asin(double value, boolean degrees) {
        if (value < -1 || value > 1) {
            throw new ArithmeticException(
                "asin input must be between -1 and 1"
            );
        }
        return fromRadians(Math.asin(value), degrees);
    }

    public double acos(double value, boolean degrees) {
        if (value < -1 || value > 1) {
            throw new ArithmeticException(
                "acos input must be between -1 and 1"
            );
        }
        return fromRadians(Math.acos(value), degrees);
    }

    public double atan(double value, boolean degrees) {
        return fromRadians(Math.atan(value), degrees);
    }

    private double toRadians(double value, boolean degrees) {
        return degrees ? Math.toRadians(value) : value;
    }

    private double fromRadians(double radians, boolean degrees) {
        return degrees ? Math.toDegrees(radians) : radians;
    }
}
```

### 3. CalculatorController.java

This controller connects the REST API endpoints to the calculator
service.

``` java
package com.example.calculator.controller;

import com.example.calculator.service.CalculatorService;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/calc")
public class CalculatorController {

    private final CalculatorService service;

    public CalculatorController(CalculatorService service) {
        this.service = service;
    }

    @GetMapping("/add")
    public Map<String, Object> add(
            @RequestParam double a,
            @RequestParam double b) {
        return result("add", service.add(a, b));
    }

    @GetMapping("/subtract")
    public Map<String, Object> subtract(
            @RequestParam double a,
            @RequestParam double b) {
        return result("subtract", service.subtract(a, b));
    }

    @GetMapping("/multiply")
    public Map<String, Object> multiply(
            @RequestParam double a,
            @RequestParam double b) {
        return result("multiply", service.multiply(a, b));
    }

    @GetMapping("/divide")
    public Map<String, Object> divide(
            @RequestParam double a,
            @RequestParam double b) {
        return result("divide", service.divide(a, b));
    }

    @GetMapping("/power")
    public Map<String, Object> power(
            @RequestParam double a,
            @RequestParam double b) {
        return result("power", service.power(a, b));
    }

    @GetMapping("/sqrt")
    public Map<String, Object> sqrt(@RequestParam double a) {
        return result("sqrt", service.sqrt(a));
    }

    @GetMapping("/trig/{function}")
    public Map<String, Object> trig(
            @PathVariable String function,
            @RequestParam double value,
            @RequestParam(defaultValue = "deg") String unit) {

        boolean degrees = !unit.equalsIgnoreCase("rad");

        double r = switch (function.toLowerCase()) {
            case "sin" -> service.sin(value, degrees);
            case "cos" -> service.cos(value, degrees);
            case "tan" -> service.tan(value, degrees);
            case "asin" -> service.asin(value, degrees);
            case "acos" -> service.acos(value, degrees);
            case "atan" -> service.atan(value, degrees);
            default -> throw new IllegalArgumentException(
                "Unknown trig function: " + function
            );
        };

        return result(function.toLowerCase(), r);
    }

    private Map<String, Object> result(
            String operation, double value) {
        double cleaned = Math.round(value * 1e10) / 1e10;

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("operation", operation);
        body.put("result", cleaned);

        return body;
    }
}
```

## Run Tests

To execute the unit tests and controller tests, run:

``` bash
mvn test
```

The tests cover arithmetic operations, trigonometric calculations,
invalid mathematical inputs, and REST API responses.

------------------------------------------------------------------------

::: {align="center"}
**Developed by Shahrier Mokammel**\
**Student ID: IT24050**
:::
