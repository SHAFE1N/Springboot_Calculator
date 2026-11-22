package com.example.calculator;

import com.example.calculator.service.CalculatorService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CalculatorServiceTest {

    private final CalculatorService service = new CalculatorService();
    private static final double EPS = 1e-9;

    @Test
    void basicOperations() {
        assertEquals(5, service.add(2, 3), EPS);
        assertEquals(-1, service.subtract(2, 3), EPS);
        assertEquals(6, service.multiply(2, 3), EPS);
        assertEquals(2.5, service.divide(5, 2), EPS);
        assertEquals(8, service.power(2, 3), EPS);
        assertEquals(4, service.sqrt(16), EPS);
    }

    @Test
    void divideByZeroThrows() {
        assertThrows(ArithmeticException.class, () -> service.divide(1, 0));
    }

    @Test
    void sqrtOfNegativeThrows() {
        assertThrows(ArithmeticException.class, () -> service.sqrt(-4));
    }

    @Test
    void trigInDegrees() {
        assertEquals(0.5, service.sin(30, true), EPS);
        assertEquals(0.5, service.cos(60, true), EPS);
        assertEquals(1.0, service.tan(45, true), EPS);
    }

    @Test
    void trigInRadians() {
        assertEquals(1.0, service.sin(Math.PI / 2, false), EPS);
        assertEquals(-1.0, service.cos(Math.PI, false), EPS);
    }

    @Test
    void inverseTrig() {
        assertEquals(30, service.asin(0.5, true), EPS);
        assertEquals(60, service.acos(0.5, true), EPS);
        assertEquals(45, service.atan(1, true), EPS);
    }

    @Test
    void tanUndefinedAt90Degrees() {
        assertThrows(ArithmeticException.class, () -> service.tan(90, true));
    }

    @Test
    void inverseTrigOutOfRangeThrows() {
        assertThrows(ArithmeticException.class, () -> service.asin(2, true));
        assertThrows(ArithmeticException.class, () -> service.acos(-2, true));
    }
}
