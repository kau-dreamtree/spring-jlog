package shop.dreamtree.jlog.domain.room;

import static shop.dreamtree.jlog.exception.JLogErrorCode.UNAUTHORIZED_USERNAME;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import shop.dreamtree.jlog.domain.log.Log;
import shop.dreamtree.jlog.domain.outpay.Outpay;
import shop.dreamtree.jlog.exception.JLogException;

@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 5, nullable = false)
    private String code;

    @Embedded
    private Usernames usernames;

    @Embedded
    private Outpay outpay;

//     todo: Set @OneToMany relationship
//     @OneToMany(fetch = FetchType.EAGER, mappedBy = "room")
//     private List<Log> logs;

    protected Room() {}

    public Room(String code, String username) {
        // todo: Validate username not empty
        this.code = code;
        this.usernames = new Usernames(username);
        this.outpay = Outpay.create();
    }

    public void join(String username) {
        usernames.join(username);
    }

    public void authenticate(String username) {
        if (usernames.doesNotContain(username)) {
            throw new JLogException(UNAUTHORIZED_USERNAME);
        }
    }

    public void addLog(Log log) {
        outpay.add(log);
    }

    public void updateOutpay(List<Log> logs) {
        outpay.update(logs);
    }

    public String code() {
        return code;
    }

    public Outpay outpay() {
        return outpay;
    }
}
