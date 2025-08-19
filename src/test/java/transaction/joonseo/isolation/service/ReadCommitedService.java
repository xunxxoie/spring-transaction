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
public class ReadCommitedService {

    private final UserRepository userRepository;
    private final EntityManager entityManager;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public String[] readUserNameTwice(Long userId) throws InterruptedException {
        // 첫 번째 읽기
        String name1 = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("user not found"))
                .getName();

        Thread.sleep(3000); // 다른 트랜잭션에서 수정할 시간 확보
        entityManager.clear();

        // 두 번째 읽기 (같은 트랜잭션 내)
        String name2 = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("user not found"))
                .getName();

        return new String[]{name1, name2};
    }

    @Transactional
    public void updateUserName(Long userId, String name){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("user not found"));

        user.updateName(name);
    }
}
