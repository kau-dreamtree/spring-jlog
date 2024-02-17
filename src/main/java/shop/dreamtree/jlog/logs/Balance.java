package shop.dreamtree.jlog.logs;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class Balance {
    private long amount;
    private String username;

    public Balance(long amount, String username) {
        this.amount = amount;
        this.username = (amount == 0) ? "" : username;
    }
}
