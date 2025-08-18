package transaction.joonseo.domain.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import transaction.joonseo.domain.account.entity.Account;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUserId(Long userId);
}
