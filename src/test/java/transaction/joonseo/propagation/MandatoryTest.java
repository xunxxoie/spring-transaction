package transaction.joonseo.propagation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.IllegalTransactionStateException;
import transaction.joonseo.propagation.caller.MandatoryCaller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class MandatoryTest {

    @Autowired
    private MandatoryCaller mandatoryCaller;

    @Test
    @DisplayName("호출 메소드의 트랜잭션이 있으면, 피호출 메소드는 해당 트랜잭션에서 실행된다.")
    void callerMethodWithTransaction(){
        //when
        boolean result = mandatoryCaller.outerMethodWithTransaction();

        //then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("호출 메소드의 트랜잭션이 없으면, 피호출 메소드는 예외를 반환한다.")
    void callerMethodWithoutTransaction(){
        //when&then
        assertThatThrownBy(() -> mandatoryCaller.outerMethodWithoutTransaction())
                .isInstanceOf(IllegalTransactionStateException.class);
    }

}