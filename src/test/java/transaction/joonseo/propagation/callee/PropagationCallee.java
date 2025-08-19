package transaction.joonseo.propagation.callee;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
public class PropagationCallee {

    @Transactional(propagation = Propagation.REQUIRED)
    public int innerMethodWithTransaction(){
        Object resourceMap = TransactionSynchronizationManager.getResourceMap();
        return resourceMap.hashCode();
    }
}
