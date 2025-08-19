package transaction.joonseo.propagation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import transaction.joonseo.propagation.caller.NestedCaller;

@SpringBootTest
class NestedTest {

    @Autowired
    private NestedCaller nestedCallee;


//    @Test
//    @DisplayName("호출 메소드의 트랜잭션이 있으면, 피호출 메소드는 새로운 트랜잭션에서 실행한다.")
//    void callerMethodWithTransaction(){
//        //when
//        boolean result = nestedCallee.outerMethodWithTransaction();
//
//        //then
//        assertThat(result).isFalse();
//    }
//
//    @Test
//    @DisplayName("호출 메소드의 트랜잭션이 없으면, 피호출 메소드는 새로운 트랜잭션 없이 실행된다.")
//    void callerMethodWithoutTransaction(){
//        //when
//        boolean result = nestedCallee.outerMethodWithoutTransaction();
//
//        //then
//        assertThat(result).isFalse();
//    }

}
