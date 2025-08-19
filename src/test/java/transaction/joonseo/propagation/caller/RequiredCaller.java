package transaction.joonseo.propagation.caller;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import transaction.joonseo.propagation.callee.RequiredCallee;

import java.util.Collections;

@Component
public class RequiredCaller {

    private final RequiredCallee requiredCallee;

    public RequiredCaller(RequiredCallee requiredCallee) {
        this.requiredCallee = requiredCallee;
    }

    // POINT [propagation = Propagation.REQUIRED] 호출 메소드의 트랜잭션이 이미 있는 경우
    @Transactional
    public boolean outerMethodWithTransaction(){

        Object callerResourceMap = TransactionSynchronizationManager.getResourceMap();
        int callerHashCode = callerResourceMap.hashCode();

        int calleeHashCode = requiredCallee.innerMethod();

        System.out.println("callerHashCode = " + callerHashCode);
        System.out.println("calleeHashCode = " + calleeHashCode);

        return callerHashCode == calleeHashCode;
    }


    // POINT [propagation = Propagation.REQUIRED] 호출 메소드의 트랜잭션이 없는 경우
    public boolean outerMethodWithoutTransaction(){
        int callerHashCode;

        Object callerResourceMap = TransactionSynchronizationManager.getResourceMap();

        if(callerResourceMap.equals(Collections.emptyMap())){
            callerHashCode = 0;
        }else{
            callerHashCode = callerResourceMap.hashCode();
        }

        int calleeHashCode = requiredCallee.innerMethod();

        System.out.println("callerHashCode = " + callerHashCode);
        System.out.println("calleeHashCode = " + calleeHashCode);

        return callerHashCode == calleeHashCode;
    }
}
