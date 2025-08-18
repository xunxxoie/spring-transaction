package transaction.joonseo.domain.transaction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import transaction.joonseo.domain.transaction.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
