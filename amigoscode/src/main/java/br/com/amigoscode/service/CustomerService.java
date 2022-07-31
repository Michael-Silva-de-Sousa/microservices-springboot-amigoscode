package br.com.amigoscode.service;

import br.com.amigoscode.persistence.Customer;
import br.com.amigoscode.persistence.CustomerRepository;
import br.com.amigoscode.web.CustomerRegistrationRequest;
import org.springframework.stereotype.Service;

@Service
public record CustomerService(CustomerRepository customerRepository) {

    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();

        customerRepository.save(customer);
    }
}
