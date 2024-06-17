package shop.dreamtree.jlog.domain.room;

import static shop.dreamtree.jlog.exception.JLogErrorCode.ROOM_FULL;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import shop.dreamtree.jlog.exception.JLogException;

@Embeddable
public class Usernames {

    @Column(length = 10, nullable = false)
    private String firstUsername;

    @Column(length = 10)
    private String secondUsername;

    protected Usernames() {}

    public Usernames(String firstUsername) {
        this.firstUsername = firstUsername;
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
        if (hasRoom() && doesNotContain(username)) {
            secondUsername = username;
        }
    }

    private boolean hasRoom() {
        return !isFull();
    }

    private boolean isFull() {
        return secondUsername != null;
    }

    public boolean doesNotContain(String username) {
        return !contains(username);
    }

    public boolean contains(String username) {
        return username.equals(firstUsername) || username.equals(secondUsername);
    }
}
