package transaction.joonseo.domain.alarm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import transaction.joonseo.domain.alarm.entity.Alarm;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
}
