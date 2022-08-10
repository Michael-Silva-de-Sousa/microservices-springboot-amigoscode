package br.com.amigoscode.service;

import br.com.amigoscode.persistence.Customer;
import br.com.amigoscode.persistence.CustomerRepository;
import br.com.amigoscode.web.CustomerRegistrationRequest;
import br.com.amigoscode.web.FraudCheckResponse;
import br.com.amigoscode.web.NotificationRequest;
import br.com.rabbit.RabbitMQMessageProducer;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final RestTemplate restTemplate;
    private final RabbitMQMessageProducer rabbitMQMessageProducer;

    public void registerCustomer(CustomerRegistrationRequest request) {

        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();

        customerRepository.saveAndFlush(customer);

        ResponseEntity<FraudCheckResponse> fraudCheckResponse = restTemplate.getForEntity(
                "http://localhost:8081/api/v1/fraud-check/{customerId}",
                FraudCheckResponse.class,
                customer.getId()
        );

        if(fraudCheckResponse.getStatusCode() == HttpStatus.OK){
            throw new IllegalStateException("fraudster");
        } else {
            publicarNotificacao(customer);
        }
    }

    private void publicarNotificacao(Customer customer) {
        var notificationRequest = new NotificationRequest(
                customer.getId(),
                customer.getEmail(),
                String.format("Hi %s, welcome to Amigoscode...",
                        customer.getFirstName())
        );

        rabbitMQMessageProducer.publish(
                notificationRequest,
                "internal.exchange",
                "internal.notification.routing-key"
        );
    }
}
