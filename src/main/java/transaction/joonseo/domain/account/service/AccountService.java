package transaction.joonseo.domain.account.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import transaction.joonseo.domain.account.entity.Account;
import transaction.joonseo.domain.account.repository.AccountRepository;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public Long withdraw(Long userId, Long amount) {
        Account account = accountRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("계좌 조회 실패"));

        if(account.getBalance() < amount)
            throw new RuntimeException("송신 계좌 잔액 부족");

        account.withdraw(amount);

        return account.getBalance();
    }

    public Long deposit(Long userId, Long amount) {
        Account account = accountRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("계좌 조회 실패"));

        account.deposit(amount);

        return account.getBalance();
    }

    public Long getBalance(Long userId) {
        Account account = accountRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("계좌 조회 실패"));

        return account.getBalance();
    }
}
