package br.com.amigoscode.service;

import br.com.amigoscode.clients.FraudClient;
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

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final FraudClient fraudClient;
    private final RabbitMQMessageProducer rabbitMQMessageProducer;

    @Transactional
    public void registerCustomer(CustomerRegistrationRequest request) {

        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();

        customerRepository.saveAndFlush(customer);

        gravarClienteFraudador(customer);
    }

    private void gravarClienteFraudador(Customer customer) {
        ResponseEntity<FraudCheckResponse> fraudCheckResponse = fraudClient.isFraudster(customer.getId());

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
