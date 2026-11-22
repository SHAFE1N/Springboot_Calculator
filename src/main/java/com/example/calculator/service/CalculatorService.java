package com.example.calculator.service;

import org.springframework.stereotype.Service;

@Service
public class CalculatorService {

    // ---------- Basic operations ----------

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
            throw new ArithmeticException("Cannot take square root of a negative number");
        }
        return Math.sqrt(a);
    }

    // ---------- Trigonometric operations ----------

    public double sin(double value, boolean degrees) {
        return Math.sin(toRadians(value, degrees));
    }

    public double cos(double value, boolean degrees) {
        return Math.cos(toRadians(value, degrees));
    }

    public double tan(double value, boolean degrees) {
        double rad = toRadians(value, degrees);
        if (Math.abs(Math.cos(rad)) < 1e-12) {
            throw new ArithmeticException("tan is undefined for this angle");
        }
        return Math.tan(rad);
    }

    public double asin(double value, boolean degrees) {
        if (value < -1 || value > 1) {
            throw new ArithmeticException("asin input must be between -1 and 1");
        }
        return fromRadians(Math.asin(value), degrees);
    }

    public double acos(double value, boolean degrees) {
        if (value < -1 || value > 1) {
            throw new ArithmeticException("acos input must be between -1 and 1");
        }
        return fromRadians(Math.acos(value), degrees);
    }

    public double atan(double value, boolean degrees) {
        return fromRadians(Math.atan(value), degrees);
    }

    // ---------- Helpers ----------

    private double toRadians(double value, boolean degrees) {
        return degrees ? Math.toRadians(value) : value;
    }

    private double fromRadians(double radians, boolean degrees) {
        return degrees ? Math.toDegrees(radians) : radians;
    }
}
