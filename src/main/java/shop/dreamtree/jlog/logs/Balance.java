package shop.dreamtree.jlog.logs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Balance {
    private long amount;
    private String username;
}
