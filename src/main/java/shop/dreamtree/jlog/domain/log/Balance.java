package shop.dreamtree.jlog.domain.log;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingLong;

import java.util.List;
import java.util.Map;

public record Balance(
        long amount,
        String winner
) {

    public static Balance from(List<Log> logs) {
        Map<String, Long> eachSum = getEachSum(logs);
        long balanceAmount = computeBalance(eachSum);
        String winner = findWinnerName(eachSum);
        return new Balance(balanceAmount, winner);
    }

    private static Map<String, Long> getEachSum(List<Log> logs) {
        return logs.stream().collect(groupingBy(Log::username, summingLong(Log::expense)));
    }

    private static long computeBalance(Map<String, Long> sum) {
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

    private static String findWinnerName(Map<String, Long> sum) {
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
}
