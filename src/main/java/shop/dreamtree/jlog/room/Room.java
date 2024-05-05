package shop.dreamtree.jlog.room;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
@Table
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 5, nullable = false)
    private String uid;

    @Column(length = 10, nullable = false)
    private String firstUsername;

    @Column(length = 10)
    private String secondUsername;

    @Builder
    public Room(String uid, String firstUsername) {
        this.uid = uid;
        this.firstUsername = firstUsername;
    }

    public void join(String secondUsername) {
        if (canJoin(secondUsername)) {
            this.secondUsername = secondUsername;
            return;
        }
        throw new IllegalArgumentException("The room has no room.");
    }

    private boolean canJoin(String username) {
        return secondUsername == null || contains(username);
    }

    public boolean contains(String username) {
        return firstUsername.equals(username) || secondUsername != null && secondUsername.equals(username);
    }
}
