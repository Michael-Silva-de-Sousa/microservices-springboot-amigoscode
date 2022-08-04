package br.com.amigoscode.service;

import br.com.amigoscode.persistence.FraudCheckHistory;
import br.com.amigoscode.persistence.FraudFraudCheckHistory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class FraudCheckService {

    private final FraudFraudCheckHistory fraudFraudCheckHistory;

    public boolean isFraudulentCustomer(Integer customerId) {
        fraudFraudCheckHistory.save(
                FraudCheckHistory.builder()
                        .customerId(customerId)
                        .isFraudster(false)
                        .createdAt(LocalDateTime.now())
                        .build()
                );
        return false;
    }


}
