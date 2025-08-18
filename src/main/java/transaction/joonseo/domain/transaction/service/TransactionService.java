package transaction.joonseo.domain.transaction.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import transaction.joonseo.domain.transaction.entity.Transaction;
import transaction.joonseo.domain.transaction.entity.Type;
import transaction.joonseo.domain.transaction.repository.TransactionRepository;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public void saveTransaction(Long userId, Long amount, Type type){
        transactionRepository.save(Transaction.create(userId, amount, type));
    }
}
