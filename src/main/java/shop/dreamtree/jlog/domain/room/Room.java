package shop.dreamtree.jlog.domain.room;

import static shop.dreamtree.jlog.exception.JLogErrorCode.ROOM_FULL;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import shop.dreamtree.jlog.exception.JLogException;

@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 8, nullable = false)
    private String code;

    @Column(length = 10, nullable = false)
    private String firstUsername;

    @Column(length = 10)
    private String secondUsername;

    protected Room() {}

    public Room(String code, String username) {
        this.code = code;
        this.firstUsername = username;
    }

    public void join(String username) {
        if (cannotJoin(username)) {
            throw new JLogException(ROOM_FULL);
        }
        joinIfEmpty(username);
    }

    public boolean cannotJoin(String username) {
        return isFull() && doesNotContain(username);
    }

    private void joinIfEmpty(String username) {
        if (isEmpty() && !Objects.equals(firstUsername, username)) {
            secondUsername = username;
        }
    }

    private boolean doesNotContain(String username) {
        return !(Objects.equals(firstUsername, username) || Objects.equals(secondUsername, username));
    }

    private boolean isFull() {
        return !isEmpty();
    }

    private boolean isEmpty() {
        return secondUsername == null;
    }

    public Long id() {
        return id;
    }

    public String code() {
        return code;
    }

    public String firstUsername() {
        return firstUsername;
    }

    public String secondUsername() {
        return secondUsername;
    }
}
