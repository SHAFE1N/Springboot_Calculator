package com.example.calculator.controller;

import com.example.calculator.service.CalculatorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/calc")
public class CalculatorController {

    private final CalculatorService service;

    public CalculatorController(CalculatorService service) {
        this.service = service;
    }

    // GET /api/calc/add?a=2&b=3
    @GetMapping("/add")
    public Map<String, Object> add(@RequestParam double a, @RequestParam double b) {
        return result("add", service.add(a, b));
    }

    @GetMapping("/subtract")
    public Map<String, Object> subtract(@RequestParam double a, @RequestParam double b) {
        return result("subtract", service.subtract(a, b));
    }

    @GetMapping("/multiply")
    public Map<String, Object> multiply(@RequestParam double a, @RequestParam double b) {
        return result("multiply", service.multiply(a, b));
    }

    @GetMapping("/divide")
    public Map<String, Object> divide(@RequestParam double a, @RequestParam double b) {
        return result("divide", service.divide(a, b));
    }

    @GetMapping("/power")
    public Map<String, Object> power(@RequestParam double a, @RequestParam double b) {
        return result("power", service.power(a, b));
    }

    @GetMapping("/sqrt")
    public Map<String, Object> sqrt(@RequestParam double a) {
        return result("sqrt", service.sqrt(a));
    }

    // GET /api/calc/trig/sin?value=30&unit=deg   (unit: deg | rad, default deg)
    @GetMapping("/trig/{function}")
    public Map<String, Object> trig(@PathVariable String function,
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
            default -> throw new IllegalArgumentException("Unknown trig function: " + function);
        };
        return result(function.toLowerCase(), r);
    }

    private Map<String, Object> result(String operation, double value) {
        // Clean floating point noise such as 0.49999999999999994 -> 0.5
        double cleaned = Math.round(value * 1e10) / 1e10;
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("operation", operation);
        body.put("result", cleaned);
        return body;
    }
}
