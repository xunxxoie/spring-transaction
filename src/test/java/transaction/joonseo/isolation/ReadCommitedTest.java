package transaction.joonseo.isolation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import transaction.joonseo.domain.user.entity.User;
import transaction.joonseo.domain.user.repository.UserRepository;
import transaction.joonseo.isolation.service.ReadCommitedService;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ReadCommitedTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReadCommitedService readCommitedService;

    @Test
    @DisplayName("트랜잭션 격리 수준이 READ_COMMITTED면 Non-repeatable Read가 발생할 수 있다.")
    void nonRepeatableRead() throws InterruptedException {
        //given
        User user1 = userRepository.save(User.create("한준서"));
        Long userId = user1.getId();

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch endLatch = new CountDownLatch(2);

        AtomicReference<String[]> readResults = new AtomicReference<>();

        //when
        executorService.submit(() -> {
            try {
                startLatch.await();

                String[] results = readCommitedService.readUserNameTwice(userId);

                readResults.set(results);
            } catch (Exception e) {
                return ;
            } finally {
                endLatch.countDown();
            }
        });

        executorService.submit(() -> {
            try {
                startLatch.await();

                Thread.sleep(500);

                readCommitedService.updateUserName(userId, "강민정");
            } catch (Exception e) {
                return ;
            } finally {
                endLatch.countDown();
            }
        });

        startLatch.countDown();
        endLatch.await();

        //then
        String[] results = readResults.get();
        assertThat(results[0]).isEqualTo("한준서");
        assertThat(results[1]).isEqualTo("강민정");
        assertThat(results[0]).isNotEqualTo(results[1]);
    }
}
