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

    @Column(columnDefinition = "bigint", name = "sender_id")
    private Long senderId;

    @Column(columnDefinition = "bigint", name = "sender_account_id")
    private Long senderAccountId;

    @Column(columnDefinition = "bigint", name = "receiver_id")
    private Long receiverId;

    @Column(columnDefinition = "bigint", name = "receiver_account_id")
    private Long receiverAccountId;

    @Column(columnDefinition = "bigint")
    private Long amount;

    @Builder
    private Transaction(Long senderId, Long senderAccountId, Long receiverId, Long receiverAccountId, Long amount) {
        this.senderId = senderId;
        this.senderAccountId = senderAccountId;
        this.receiverId = receiverId;
        this.receiverAccountId = receiverAccountId;
        this.amount = amount;
    }

    public static Transaction create(Long senderId, Long senderAccountId, Long receiverId, Long receiverAccountId, Long amount) {
        return Transaction.builder()
                .senderId(senderId)
                .senderAccountId(senderAccountId)
                .receiverId(receiverId)
                .receiverAccountId(receiverAccountId)
                .amount(amount)
                .build();
    }
}
