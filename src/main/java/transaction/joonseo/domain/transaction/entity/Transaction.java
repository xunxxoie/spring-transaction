package transaction.joonseo.domain.transaction.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "bigint", name = "user_id")
    private Long userId;

    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(columnDefinition = "bigint")
    private Long amount;

    @Builder
    private Transaction(Long userId, Long amount, Type type) {
        this.userId = userId;
        this.amount = amount;
        this.type = type;
    }

    public static Transaction create(Long userId, Long amount, Type type) {
        return Transaction.builder()
                .userId(userId)
                .amount(amount)
                .type(type)
                .build();
    }
}
