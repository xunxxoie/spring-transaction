package transaction.joonseo.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import transaction.joonseo.domain.user.entity.User;
import transaction.joonseo.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User signUp(String name){
        return userRepository.save(User.create(name));
    }
}
