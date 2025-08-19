package transaction.joonseo.propagation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import transaction.joonseo.propagation.caller.SupportsCaller;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SupportsTest {

    @Autowired
    private SupportsCaller supportsCaller;

    @Test
    @DisplayName("호출 메소드의 트랜잭션이 있으면, 피호출 메소드는 해당 트랜잭션에서 실행된다.")
    void callerMethodWithTransaction(){
        //when
        boolean result = supportsCaller.outerMethodWithTransaction();

        //then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("호출 메소드의 트랜잭션이 없으면, 피호출 메소드는 트랜잭션 없이 실행된다.")
    void callerMethodWithoutTransaction(){
        //when
        boolean result = supportsCaller.outerMethodWithoutTransaction();

        //then
        assertThat(result).isTrue(); // 0(트랜잭션 없을 시 반환) == 0으로 true
    }
}
