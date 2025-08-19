package transaction.joonseo.propagation.callee;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Collections;

@Component
public class NestedCallee {
    @Transactional(propagation = Propagation.NESTED)
    public int innerMethod() {
        Object resourceMap = TransactionSynchronizationManager.getResourceMap();

        if(resourceMap.equals(Collections.emptyMap()))
            return 0;

        return resourceMap.hashCode();
    }
}
