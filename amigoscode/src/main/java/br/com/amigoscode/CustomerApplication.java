package br.com.amigoscode;

import br.com.rabbit.RabbitMQMessageProducer;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@EnableFeignClients(basePackages = "br.com.amigoscode.clients")
@EnableEurekaClient
@SpringBootApplication
public class CustomerApplication {
    public static void main(String[] args) {
        SpringApplication.run(CustomerApplication.class, args);
    }

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Bean
    public RabbitMQMessageProducer rabbitMQMessageProducer(){
        return new RabbitMQMessageProducer(amqpTemplate);
    }
}
