package com.example.calculator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CalculatorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void addEndpoint() throws Exception {
        mockMvc.perform(get("/api/calc/add").param("a", "2").param("b", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(5.0));
    }

    @Test
    void sinEndpointInDegrees() throws Exception {
        mockMvc.perform(get("/api/calc/trig/sin").param("value", "30").param("unit", "deg"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(0.5));
    }

    @Test
    void divideByZeroReturns400() throws Exception {
        mockMvc.perform(get("/api/calc/divide").param("a", "1").param("b", "0"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Cannot divide by zero"));
    }

    @Test
    void unknownTrigFunctionReturns400() throws Exception {
        mockMvc.perform(get("/api/calc/trig/foo").param("value", "1"))
                .andExpect(status().isBadRequest());
    }
}
