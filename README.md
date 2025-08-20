# Spring Transaction 테스트

## 1. 트랜잭션 전파(Propagation) 테스트

### REQUIRED
- 호출 메소드에 트랜잭션이 있으면 해당 트랜잭션에서 실행
- 호출 메소드에 트랜잭션이 없으면 새로운 트랜잭션 생성

### REQUIRES_NEW
- 항상 새로운 트랜잭션을 생성하여 실행
- 기존 트랜잭션과 독립적으로 동작

### SUPPORTS
- 호출 메소드에 트랜잭션이 있으면 해당 트랜잭션에서 실행
- 호출 메소드에 트랜잭션이 없으면 트랜잭션 없이 실행

### MANDATORY
- 호출 메소드에 트랜잭션이 있으면 해당 트랜잭션에서 실행
- 호출 메소드에 트랜잭션이 없으면 `IllegalTransactionStateException` 발생

### NEVER
- 호출 메소드에 트랜잭션이 있으면 `IllegalTransactionStateException` 발생
- 호출 메소드에 트랜잭션이 없으면 트랜잭션 없이 실행

### NOT_SUPPORTED
- 항상 트랜잭션 없이 실행
- 기존 트랜잭션이 있어도 일시 중단

<br>

## 2. 트랜잭션 격리 수준(Isolation Level) 테스트

### READ_UNCOMMITTED
- **Dirty Read** 발생 가능성 검증
- 커밋되지 않은 데이터를 읽을 수 있음을 확인

### READ_COMMITTED
- **Non-repeatable Read** 발생 가능성 검증
- 같은 트랜잭션 내에서 동일한 데이터를 두 번 읽었을 때 다른 결과가 나올 수 있음을 확인

### REPEATABLE_READ
- **Phantom Read** 발생 가능성 검증
- 동일한 로우는 일관성 있게 읽히지만, 테이블 크기는 변경될 수 있음을 확인

<br>

## 3. 롤백(Rollback) 테스트

### rollbackFor/noRollbackFor 옵션
- `rollbackFor = IllegalAccessException.class`: 체크 예외 발생 시 롤백 수행
- `noRollbackFor = IllegalArgumentException.class`: 언체크 예외 발생 시 롤백 수행하지 않음

<br>

## 4. 테스트 구현 방식

- `CountDownLatch`와 `ExecutorService`를 사용한 동시성 테스트
- 각 전파 옵션별로 Caller/Callee 패턴으로 구조화된 테스트 코드