package br.com.amigoscode.service;

import br.com.amigoscode.web.CustomerRegistrationRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/***
 * Teste do CustomerService.
 */
@SpringBootTest
class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @Test
    void saveCustomer(){
        customerService.registerCustomer(getCustomer());
    }

    CustomerRegistrationRequest getCustomer(){
        return CustomerRegistrationRequest.builder()
                .firstName("Michael")
                .lastName("Sousa")
                .email("michael.sp.contato@gmail.com")
                .build();
    }
}
