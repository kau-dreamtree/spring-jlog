package shop.dreamtree.jlog.logs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import shop.dreamtree.jlog.room.Room;
import shop.dreamtree.jlog.room.RoomRepository;

@RequiredArgsConstructor
@Service
public class LogsService {
    private final LogsRepository logsRepository;
    private final RoomRepository roomRepository;

    @Transactional
    void save(LogsDto logsDto) {
        authorize(logsDto.getRoomUid(), logsDto.getUsername());
        logsRepository.save(logsDto.toEntity());
    }

    LogsResponse find(String roomUid, String username) {
        authorize(roomUid, username);
        List<LogsDto> logs = logsRepository.getAllByRoomUidOrderByCreatedDateAsc(roomUid).stream()
                .map(LogsDto::from)
                .toList();
        Map<String, Long> sum = getSum(logs);
        long balance = computeBalance(sum);
        String winner = findWinnerName(sum);
        return LogsResponse.builder()
                .balance(new Balance(balance, winner))
                .logsDtos(logs)
                .build();
    }

    private Map<String, Long> getSum(List<LogsDto> logs) {
        Map<String, Long> sum = new HashMap<>();
        logs.forEach(log -> {
            String username = log.getUsername();
            long expense = log.getExpense();
            if (!sum.containsKey(username)) {
                sum.put(username, 0L);
            }
            sum.computeIfPresent(username, (k, v) -> v + expense);
        });
        return sum;
    }

    private String findWinnerName(Map<String, Long> sum) {
        List<String> names = sum.keySet().stream().toList();
        if (names.isEmpty()) {
            return "";
        }
        String name1 = names.get(0);
        if (names.size() == 1) {
            return name1;
        }
        String name2 = names.get(1);
        long amount1 = sum.get(name1);
        long amount2 = sum.get(name2);
        if (amount1 < amount2) {
            return name2;
        }
        return name1;
    }

    private long computeBalance(Map<String, Long> sum) {
        List<Long> amounts = sum.values().stream().toList();
        if (amounts.isEmpty()) {
            return 0L;
        }
        long amount1 = amounts.get(0);
        if (amounts.size() == 1) {
            return amount1;
        }
        long amount2 = amounts.get(1);
        return Math.abs(amount1 - amount2);
    }

    private void authorize(String roomUid, String username) {
        Room room = roomRepository.findByUid(roomUid)
                .orElseThrow(() -> new EntityNotFoundException("No such room exists."));
        if (!room.contains(username)) {
            throw new IllegalArgumentException("Unauthorized username");
        }
    }

}
