package transaction.joonseo.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import transaction.joonseo.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
