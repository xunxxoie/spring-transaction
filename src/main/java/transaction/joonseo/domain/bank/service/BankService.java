package transaction.joonseo.domain.bank.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import transaction.joonseo.domain.account.service.AccountService;
import transaction.joonseo.domain.alarm.service.AlarmService;
import transaction.joonseo.domain.transaction.entity.Type;
import transaction.joonseo.domain.transaction.service.TransactionService;

@Service
@RequiredArgsConstructor
public class BankService {

    private final AccountService accountService;
    private final TransactionService transactionService;
    private final AlarmService alarmService;

    public Long withdraw(Long userId, Long amount) {
        // 계좌에서 출금
        Long balance = accountService.withdraw(userId, amount);

        // 거래내역 저장
        transactionService.saveTransaction(userId, amount, Type.WITHDRAW);

        // 알람 전송
        alarmService.sendAlarm(userId, amount, Type.WITHDRAW.getMessage());

        // 잔액 반환
        return balance;
    }

    public Long deposit(Long userId, Long amount) {
        // 계좌에 입금
        Long balance = accountService.deposit(userId, amount);

        // 거래내역 저장
        transactionService.saveTransaction(userId, amount, Type.DEPOSIT);

        // 알람 전송
        alarmService.sendAlarm(userId, amount, Type.DEPOSIT.getMessage());

        // 잔액 반환
        return balance;
    }

    public void transfer(Long senderId, Long receiverId, Long amount) {
        // 송신 계좌에서 출금, 잔액 부족이면 예외 반환
        try{
            accountService.withdraw(senderId, amount);
        }catch (RuntimeException e){
            return;
        }

        // 수신 계좌에 입금
        accountService.deposit(receiverId, amount);

        // 송신, 수신 거래내역 저장
        transactionService.saveTransaction(senderId, amount, Type.WITHDRAW);
        transactionService.saveTransaction(senderId, amount, Type.TRANSFER);
        transactionService.saveTransaction(receiverId, amount, Type.DEPOSIT);

        // 송신, 수신 알림 전송
        alarmService.sendAlarm(senderId, amount, Type.WITHDRAW.getMessage());
        alarmService.sendAlarm(senderId, amount, Type.TRANSFER.getMessage());
        alarmService.sendAlarm(receiverId, amount, Type.DEPOSIT.getMessage());
    }

    public String getBalance(Long userId){
        return "사용자 " + userId.toString() + "번의 잔액 : " + accountService.getBalance(userId).toString() + "원";
    }
}
