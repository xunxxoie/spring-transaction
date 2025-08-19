package transaction.joonseo.propagation.caller;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import transaction.joonseo.propagation.callee.PropagationCallee;

import java.util.Collections;

@Service
public class PropagationCaller {

    private final PropagationCallee propagationCallee;

    public PropagationCaller(PropagationCallee propagationCallee) {
        this.propagationCallee = propagationCallee;
    }

    // POINT 호출 메소드에서 트랜잭션이 이미 있는 경우
    @Transactional
    public boolean outerMethodWithTransaction(){

        Object callerResourceMap = TransactionSynchronizationManager.getResourceMap();
        int callerHashCode = callerResourceMap.hashCode();

        int calleeHashCode = propagationCallee.innerMethodWithTransaction();

        System.out.println("callerHashCode = " + callerHashCode);
        System.out.println("calleeHashCode = " + calleeHashCode);

        return callerHashCode == calleeHashCode;
    }


    // POINT 호출 메소드에서 트랜잭션이 없는 경우
    public boolean outerMethodWithoutTransaction(){
        int callerHashCode;

        Object callerResourceMap = TransactionSynchronizationManager.getResourceMap();

        if(callerResourceMap.equals(Collections.emptyMap())){
            callerHashCode = 0;
        }else{
            callerHashCode = callerResourceMap.hashCode();
        }

        int calleeHashCode = propagationCallee.innerMethodWithTransaction();

        System.out.println("callerHashCode = " + callerHashCode);
        System.out.println("calleeHashCode = " + calleeHashCode);

        return callerHashCode == calleeHashCode;
    }
}
