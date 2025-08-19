package transaction.joonseo.propagation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import transaction.joonseo.propagation.caller.NotSupportedCaller;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class NotSupportedTest {

    @Autowired
    private NotSupportedCaller notSupportedCaller;

    @Test
    @DisplayName("호출 메소드의 트랜잭션이 있어도, 피호출 메소드는 트랜잭션 없이 실행된다.")
    void callerMethodWithTransaction(){
        //when
        boolean result = notSupportedCaller.outerMethodWithTransaction();

        //then
        assertThat(result).isFalse(); // 피호출 메소드의 해시 아이디 == 0
    }

    @Test
    @DisplayName("호출 메소드의 트랜잭션이 없으면, 피호출 메소드는 트랜잭션 없이 실행된다.")
    void callerMethodWithoutTransaction(){
        //when
        boolean result = notSupportedCaller.outerMethodWithoutTransaction();

        //then
        assertThat(result).isTrue(); // 둘 다 트랜잭션 없이 실행되어서 0 == 0
    }
}
