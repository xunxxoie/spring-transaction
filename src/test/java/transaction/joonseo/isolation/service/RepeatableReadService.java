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
public class RepeatableReadService {

    private final UserRepository userRepository;
    private final EntityManager entityManager;

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public String[] readUserNameAndSize(Long userId) throws InterruptedException {
        // 첫번째 읽기 - 로우와 로우 크기 읽기
        String name1 = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("user not found"))
                .getName();

        long size1 = userRepository.count();

        Thread.sleep(5000); // 테이블에 유저를 추가하기 전까지 대기
        entityManager.clear();

        // 두번째 읽기 - 첫번째로 읽은 로우와 동일하되, 크기 바뀜
        String name2 = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("user not found"))
                .getName();

        long size2 = userRepository.count();

        return new String[]{name1 + size1, name2 + size2};
    }

    @Transactional
    public void createUser(String name) {
        userRepository.save(User.create(name));
    }

    @Transactional
    public void updateUserName(Long userId, String name){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("user not found"));

        user.updateName(name);
    }
}
