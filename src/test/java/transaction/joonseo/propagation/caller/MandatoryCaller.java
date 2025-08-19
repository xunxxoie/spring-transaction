package transaction.joonseo.propagation.caller;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import transaction.joonseo.propagation.callee.MandatoryCallee;

import java.util.Collections;

@Component
public class MandatoryCaller {

    private final MandatoryCallee mandatoryCallee;

    public MandatoryCaller(MandatoryCallee mandatoryCallee) {
        this.mandatoryCallee = mandatoryCallee;
    }

    /**
     * @return 호출 메소드와 피호출 메소드가 같은 트랜잭션에서 실행되고 있는지 여부
     */
    // POINT [propagation = Propagation.MANDATORY] 호출 메소드의 트랜잭션이 이미 있는 경우
    @Transactional
    public boolean outerMethodWithTransaction(){

        Object callerResourceMap = TransactionSynchronizationManager.getResourceMap();
        int callerHashCode = callerResourceMap.hashCode();

        int calleeHashCode = mandatoryCallee.innerMethod();

        System.out.println("callerHashCode = " + callerHashCode);
        System.out.println("calleeHashCode = " + calleeHashCode);

        return callerHashCode == calleeHashCode && calleeHashCode != 0;
    }

    /**
     * @return 호출 메소드와 피호출 메소드가 같은 트랜잭션에서 실행되고 있는지 여부
     */
    // POINT [propagation = Propagation.MANDATORY] 호출 메소드의 트랜잭션이 없는 경우
    public boolean outerMethodWithoutTransaction(){
        int callerHashCode;

        Object callerResourceMap = TransactionSynchronizationManager.getResourceMap();

        if(callerResourceMap.equals(Collections.emptyMap())){
            callerHashCode = 0;
        }else{
            callerHashCode = callerResourceMap.hashCode();
        }

        int calleeHashCode = mandatoryCallee.innerMethod(); // IllegalTransactionStateException 발생 지점

        System.out.println("callerHashCode = " + callerHashCode);
        System.out.println("calleeHashCode = " + calleeHashCode);

        return callerHashCode == calleeHashCode && callerHashCode == 0;
    }
}
