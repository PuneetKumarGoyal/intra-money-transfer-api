package com.mastercard;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureWebMvc
public class TransactionsControllerWMTest {
    @Autowired
    private MockMvc mockMvc;

    @SneakyThrows
    @Test
    void transferAmount() {

        var json = "{ \"fromAccount\": \"1\", \"toAccount\": \"2\", \"amount\": 50 }";

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$").value("Transaction successfully executed"))
                .andReturn();
    }

    @SneakyThrows
    @Test
    void insufficientFundsWhentransferAmount() {

        var json = "{ \"fromAccount\": \"1\", \"toAccount\": \"2\", \"amount\": 50000 }";

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.message").value("Insufficient funds"))
                .andReturn();
    }

}
