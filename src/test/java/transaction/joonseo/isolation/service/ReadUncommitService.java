package transaction.joonseo.isolation.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import transaction.joonseo.domain.user.entity.User;
import transaction.joonseo.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class ReadUncommitService {

    private final UserRepository userRepository;
    private final EntityManager entityManager;

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public String readUserName(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("user not found"));

        return user.getName();
    }

    @Transactional
    public void updateUserName(Long userId, String name) throws InterruptedException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("user not found"));

        user.updateName(name);
        entityManager.flush();

        Thread.sleep(3000); // 커밋되지 않은 상황을 구현

        throw new RuntimeException("Rollback for update");
    }
}
