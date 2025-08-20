package transaction.joonseo.rollback.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import transaction.joonseo.domain.user.entity.User;
import transaction.joonseo.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class RollbackService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public String readUserName(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("user not found"))
                .getName();
    }

    @Transactional(
            rollbackFor = IllegalAccessException.class,
            noRollbackFor = IllegalArgumentException.class
    )
    public void updateUserName(Long userId, String name, int triggerId) throws IllegalAccessException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("user not found"));

        user.updateName(name);

        switch (triggerId){
            case 1 -> throw new IllegalAccessException("롤백됨");
            case 2 -> throw new IllegalArgumentException("롤백되지 않음");
        }
    }
}
