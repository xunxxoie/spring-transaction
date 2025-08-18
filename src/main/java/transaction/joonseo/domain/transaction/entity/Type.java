package transaction.joonseo.domain.transaction.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Type {
    DEPOSIT("원이 입금되었습니다."),
    WITHDRAW("원이 출금되었습니다."),
    TRANSFER("원이 송금되었습니다.");

    private final String message;

    public static Type from(String type){
        return Arrays.stream(values())
                .filter(t -> t.message.equals(type))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("유효하지 않은 거래 타입"));
    }
}
