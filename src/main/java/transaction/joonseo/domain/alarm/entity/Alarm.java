package transaction.joonseo.domain.alarm.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Alarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "bigint")
    private Long userId;

    @Column(columnDefinition = "varchar(255)")
    private String content;

    @Builder
    private Alarm(Long userId, String content) {
        this.userId = userId;
        this.content = content;
    }

    public static Alarm create(Long userId, String content) {
        return Alarm.builder()
                .userId(userId)
                .content(content)
                .build();
    }
}
