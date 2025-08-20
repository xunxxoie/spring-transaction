package transaction.joonseo.rollback;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import transaction.joonseo.domain.user.entity.User;
import transaction.joonseo.domain.user.repository.UserRepository;
import transaction.joonseo.rollback.service.RollbackService;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RollbackTest {

    @Autowired
    private RollbackService rollbackService;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("rollbackFor로 지정한 체크 예외가 발생하면 롤백된다.")
    void rollbackForTest(){
        //given
        User user = userRepository.save(User.create("한준서"));
        Long userId = user.getId();

        int triggerId = 1; // IllegalAccessException 트리거

        //when
        try{
            rollbackService.updateUserName(userId, "강민정", triggerId);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        String name = rollbackService.readUserName(userId);

        //then
        assertThat(name).isEqualTo("한준서");
    }

    @Test
    @DisplayName("noRollbackFor로 지정한 언체크 예외가 발생하면 롤백되지 않는다.")
    void noRollbackForTest(){
        //given
        User user = userRepository.save(User.create("한준서"));
        Long userId = user.getId();

        int triggerId = 2; // IllegalAccessException 트리거

        //when
        try{
            rollbackService.updateUserName(userId, "강민정", triggerId);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        String name = rollbackService.readUserName(userId); // IllegalArgumentException 트리거

        //then
        assertThat(name).isEqualTo("강민정");
    }

}
