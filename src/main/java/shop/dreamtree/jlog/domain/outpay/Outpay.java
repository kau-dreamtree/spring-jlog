package shop.dreamtree.jlog.domain.outpay;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import shop.dreamtree.jlog.domain.log.Log;

@Embeddable
public class Outpay {

    @Column(nullable = false)
    private long amount;

    @Column(nullable = false)
    private String username;

    protected Outpay() {}

    public Outpay(long amount, String username) {
        this.amount = amount;
        this.username = username;
    }

    public static Outpay create() {
        return new Outpay(0L, "");
    }

    public boolean add(Log log) {
        return outpay(log) || underpay(log) || payoff(log);
    }

    // todo: refactor to update from the difference between the previous log and the new one.
    public void update(List<Log> logs) {
        clear();
        logs.forEach(this::add);
    }

    private boolean outpay(Log log) {
        if (isWinner(log.username())) {
            amount += log.expense();
            return true;
        }
        if (isTie()) {
            username = log.username();
            amount = log.expense();
            return true;
        }
        return false;
    }

    private boolean underpay(Log log) {
        if (isNotWinner(log.username())) {
            if (log.expense() < amount) {
                amount -= log.expense();
                return true;
            }
            if (amount < log.expense()) {
                amount = log.expense() - amount;
                username = log.username();
                return true;
            }
        }
        return false;
    }

    private boolean payoff(Log log) {
        if (isNotWinner(log.username()) && amount == log.expense()) {
            clear();
            return true;
        }
        return false;
    }

    private void clear() {
        amount = 0L;
        username = "";
    }

    private boolean isNotWinner(String username) {
        return !isWinner(username);
    }

    private boolean isWinner(String username) {
        return Objects.equals(this.username, username);
    }

    private boolean isTie() {
        return amount == 0L && username.isBlank();
    }

    public long amount() {
        return amount;
    }

    public String username() {
        return username;
    }
}
