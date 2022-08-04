package br.com.amigoscode.persistence;

import br.com.amigoscode.persistence.FraudCheckHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FraudFraudCheckHistory extends JpaRepository<FraudCheckHistory, Long> {
}
