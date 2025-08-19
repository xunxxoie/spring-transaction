package transaction.joonseo.isolation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import transaction.joonseo.domain.user.entity.User;
import transaction.joonseo.domain.user.repository.UserRepository;
import transaction.joonseo.isolation.service.ReadUncommitService;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ReadUncommitTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReadUncommitService readUncommitService;

    @Test
    @DisplayName("트랜잭션 격리 수준이 READ_UNCOMMIT 이면 Dirty read가 발생할 수 있다.")
    void dirtyRead() throws InterruptedException {
        //given
        User user = userRepository.save(User.create("한준서"));
        Long userId = user.getId();

        ExecutorService executorService = Executors.newFixedThreadPool(3);
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch endLatch = new CountDownLatch(2);

        AtomicReference<String> dirtyReadResult = new AtomicReference<>();
        AtomicReference<String> realResult = new AtomicReference<>();

        executorService.submit(() -> {
            try{
                startLatch.await();

                readUncommitService.updateUserName(userId, "강민정");
            }catch(Exception e){
                System.out.println("Rollback success.");
            }finally {
                endLatch.countDown();
            }
        });

        executorService.submit(() -> {
            try{
                startLatch.await();

                Thread.sleep(1000);

                String name = readUncommitService.readUserName(userId);
                dirtyReadResult.set(name);

                System.out.println("Dirty read result = " + name);
            } catch (Exception e) {
                System.out.println("Unexpected error occurred.");
            } finally {
                endLatch.countDown();
            }
        });

        startLatch.countDown();
        endLatch.await();

        User finalUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("user not found"));
        realResult.set(finalUser.getName());

        assertThat(dirtyReadResult.get()).isEqualTo("강민정"); // 롤백되기 전 읽은 값(Dirty read)
        assertThat(realResult.get()).isEqualTo("한준서"); // 롤백되고난 후 값
    }
}
