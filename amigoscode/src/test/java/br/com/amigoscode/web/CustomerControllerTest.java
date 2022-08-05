package br.com.amigoscode.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /api/v1/customers success")
    void registerCustomer_then200() throws Exception {

        // Cria o objeto da body.
        CustomerRegistrationRequest customerRegistrationRequest = CustomerRegistrationRequest
                .builder()
                .firstName("Jorginho")
                .lastName("Sousa")
                .email("jorginho.sousa@email.com")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String customerJsonFormat = objectMapper.writeValueAsString(customerRegistrationRequest);

        // Executa a reqeust POST.
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customerJsonFormat))
                // Valida o response e content type.
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
