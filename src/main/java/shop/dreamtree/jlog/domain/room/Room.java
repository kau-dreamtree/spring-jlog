package shop.dreamtree.jlog.domain.room;

import static shop.dreamtree.jlog.exception.JLogErrorCode.ROOM_FULL;
import static shop.dreamtree.jlog.exception.JLogErrorCode.UNAUTHORIZED_USERNAME;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import shop.dreamtree.jlog.domain.log.Log;
import shop.dreamtree.jlog.domain.outpay.Outpay;
import shop.dreamtree.jlog.exception.JLogException;

@Entity
public class Room {

    private static final Logger logger = LoggerFactory.getLogger(Room.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 5, nullable = false)
    private String code;

    @Column(length = 10, nullable = false)
    private String firstUsername;

    @Column(length = 10)
    private String secondUsername;

    @Embedded
    private Outpay outpay;

//     todo: Set @OneToMany relationship
//     @OneToMany(fetch = FetchType.EAGER, mappedBy = "room")
//     private List<Log> logs;

    protected Room() {}

    public Room(String code, String username) {
        // todo: Validate username not empty
        this.code = code;
        this.firstUsername = username;
        this.outpay = Outpay.create();
    }

    public void join(String username) {
        if (cannotJoin(username)) {
            throw new JLogException(ROOM_FULL);
        }
        joinIfEmpty(username);
    }

    public void authenticate(String username) {
        if (doesNotContain(username)) {
            throw new JLogException(UNAUTHORIZED_USERNAME);
        }
    }

    public void addLog(Log log) {
        outpay.add(log);
    }

    public void updateOutpay(List<Log> logs) {
        outpay.update(logs);
    }

    public boolean cannotJoin(String username) {
        return isFull() && doesNotContain(username);
    }

    private void joinIfEmpty(String username) {
        if (hasRoom() && doesNotContain(username)) {
            secondUsername = username;
        }
    }

    public boolean doesNotContain(String username) {
        return !contains(username);
    }

    public boolean contains(String username) {
        return username.equals(firstUsername) || username.equals(secondUsername);
    }

    private boolean hasRoom() {
        return !isFull();
    }

    private boolean isFull() {
        return secondUsername != null;
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

    public Outpay outpay() {
        return outpay;
    }
}
