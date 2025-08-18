package transaction.joonseo.domain.account.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "bigint", name = "user_id", unique = true)
    private Long userId;

    @Column(columnDefinition = "bigint")
    private Long balance;

    @Builder
    private Account(Long userId, Long balance) {
        this.userId = userId;
        this.balance = balance;
    }

    public static Account create(Long userId, Long balance){
        return Account.builder()
                .userId(userId)
                .balance(balance)
                .build();
    }

    public void withdraw(Long amount){
        this.balance -= amount;
    }

    public void deposit(Long amount){
        this.balance += amount;
    }
}
