package br.com.amigoscode;

import org.springframework.stereotype.Service;

@Service
public record CustomerService() {

    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lstName())
                .email(request.email())
                .build();
    }
}
