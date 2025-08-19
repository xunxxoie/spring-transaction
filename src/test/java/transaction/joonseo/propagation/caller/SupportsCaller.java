package transaction.joonseo.propagation.caller;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import transaction.joonseo.propagation.callee.SupportsCallee;

import java.util.Collections;

@Component
public class SupportsCaller {

    private final SupportsCallee supportsCallee;

    public SupportsCaller(SupportsCallee supportsCallee) {
        this.supportsCallee = supportsCallee;
    }

    // POINT [propagation = Propagation.SUPPORTS] 호출 메소드의 트랜잭션이 이미 있는 경우
    @Transactional
    public boolean outerMethodWithTransaction(){

        Object callerResourceMap = TransactionSynchronizationManager.getResourceMap();
        int callerHashCode = callerResourceMap.hashCode();

        int calleeHashCode = supportsCallee.innerMethod();

        System.out.println("callerHashCode = " + callerHashCode);
        System.out.println("calleeHashCode = " + calleeHashCode);

        return callerHashCode == calleeHashCode;
    }


    // POINT [propagation = Propagation.SUPPORTS] 호출 메소드의 트랜잭션이 없는 경우
    public boolean outerMethodWithoutTransaction(){
        int callerHashCode;

        Object callerResourceMap = TransactionSynchronizationManager.getResourceMap();

        if(callerResourceMap.equals(Collections.emptyMap())){
            callerHashCode = 0;
        }else{
            callerHashCode = callerResourceMap.hashCode();
        }

        int calleeHashCode = supportsCallee.innerMethod();

        System.out.println("callerHashCode = " + callerHashCode);
        System.out.println("calleeHashCode = " + calleeHashCode);

        return callerHashCode == calleeHashCode;
    }
}
