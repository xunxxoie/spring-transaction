package transaction.joonseo.domain.alarm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import transaction.joonseo.domain.alarm.entity.Alarm;
import transaction.joonseo.domain.alarm.repository.AlarmRepository;
import transaction.joonseo.domain.transaction.entity.Type;

@Service
@RequiredArgsConstructor
public class AlarmService {

    private final AlarmRepository alarmRepository;

    public void sendAlarm(Long userId, Long amount, String message){
        alarmRepository.save(Alarm.create(userId, amount.toString() + message));
    }
}
