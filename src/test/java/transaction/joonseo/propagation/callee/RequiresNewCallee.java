package transaction.joonseo.propagation.callee;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Component
public class RequiresNewCallee {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int innerMethod(){
        Object resourceMap = TransactionSynchronizationManager.getResourceMap();

        System.out.println("Inner method called");

        return resourceMap.hashCode();
    }
}
