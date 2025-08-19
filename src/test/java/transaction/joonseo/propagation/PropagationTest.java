package transaction.joonseo.propagation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import transaction.joonseo.propagation.caller.PropagationCaller;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PropagationTest {

    @Autowired
    private PropagationCaller propagationCaller;

    @Test
    @DisplayName("호출 메소드의 트랜잭션이 있다면, 피호출 메소드는 해당 트랜잭션에서 실행된다.")
    void callerMethodWithTransaction(){
        //when
        boolean result = propagationCaller.outerMethodWithTransaction();

        //then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("호출 메소드의 트랜잭션이 없다면, 피호출 메소드는 새로운 트랜잭션에서 실행된다.")
    void callerMethodWithoutTransaction(){
        //when
        boolean result = propagationCaller.outerMethodWithoutTransaction();

        //then
        assertThat(result).isFalse();
    }
}
