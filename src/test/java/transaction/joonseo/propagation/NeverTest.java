package transaction.joonseo.propagation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.IllegalTransactionStateException;
import transaction.joonseo.propagation.caller.NeverCaller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class NeverTest {

    @Autowired
    private NeverCaller neverCaller;

    @Test
    @DisplayName("호출 메소드의 트랜잭션이 있을때, 피호출 메소드는 예외를 반환한다.")
    void callerMethodWithTransaction(){
        //when&then
        assertThatThrownBy(() -> neverCaller.outerMethodWithTransaction())
                .isInstanceOf(IllegalTransactionStateException.class);
    }

    @Test
    @DisplayName("호출 메소드의 트랜잭션이 없으면, 트랜잭션 없이 실행된다.")
    void callerMethodWithoutTransaction(){
        //when
        boolean result = neverCaller.outerMethodWithoutTransaction();

        //then
        assertThat(result).isTrue();
    }
}
