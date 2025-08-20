package transaction.joonseo.isolation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import transaction.joonseo.domain.user.entity.User;
import transaction.joonseo.domain.user.repository.UserRepository;
import transaction.joonseo.isolation.service.RepeatableReadService;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class RepeatableReadTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RepeatableReadService repeatableReadService;

    @Test
    @DisplayName("트랜잭션 격리 수준이 REPEATABLE_READ 이면 Dirty read는 발생하지 않지만, Phantom Read는 발생할 수 있다.")
    void phantomRead() throws InterruptedException {
        //given
        User user = userRepository.save(User.create("한준서"));
        Long userId = user.getId();

        ExecutorService executorService = Executors.newFixedThreadPool(3);
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch endLatch = new CountDownLatch(2);

        AtomicReference<String[]> phantomReadResult = new AtomicReference<>();

        executorService.submit(() -> {
            try{
                startLatch.await();

                String[] result = repeatableReadService.readUserNameAndSize(userId);

                phantomReadResult.set(result);
            }catch (Exception e) {
                return ;
            } finally {
                endLatch.countDown();
            }
        });

        executorService.submit(() -> {
            try{
                startLatch.await();

                Thread.sleep(500);

                repeatableReadService.updateUserName(userId, "강민정");
                repeatableReadService.createUser("신입생");
            }catch (Exception e) {
                return;
            }finally {
                endLatch.countDown();
            }
        });

        startLatch.countDown();
        endLatch.await();

        String[] assertValue = phantomReadResult.get();
        String size1 = assertValue[0].substring(assertValue[0].length()-1);
        String size2 = assertValue[1].substring(assertValue[1].length()-1);

        assertThat(assertValue[0].substring(0,2)).isEqualTo(assertValue[1].substring(0,2)); // dirty read 발생 여부 확인
        assertThat(size1).isNotEqualTo(size2); // phantom read 발생 여부 확인
    }
}
