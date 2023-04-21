package com.mastercard;

import lombok.SneakyThrows;
import org.hamcrest.Matchers;
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
public class AccountsControllerWMTest {
    @Autowired
    private MockMvc mockMvc;

    @SneakyThrows
    @Test
    void getAccountBalance() {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/1/balance")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.account-Id").value("1"))
                .andExpect(jsonPath("$.balance").value("1000"))
                .andExpect(jsonPath("$.currency").value("EUR"))
                .andReturn();
    }

    @SneakyThrows
    @Test
    void invalidAccountNumber() {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/1000/balance")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andReturn();
    }

    @SneakyThrows
    @Test
    void getMiniTransactions() {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/1/statements/mini")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(3)))
                .andReturn();
    }

    @SneakyThrows
    @Test
    void invalidAccountNumberWhenGetMiniTransactions() {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/1000/statements/mini")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Invalid account details"))
                .andReturn();
    }
}

