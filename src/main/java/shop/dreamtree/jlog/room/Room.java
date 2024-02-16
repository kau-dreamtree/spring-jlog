package shop.dreamtree.jlog.room;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import shop.dreamtree.jlog.posts.Posts;

@NoArgsConstructor
@Getter
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 8, nullable = false)
    private String uid;

    @Column(length = 10, nullable = false)
    private String firstUsername;

    @Column(length = 10)
    private String secondUsername;

    @Builder
    public Room(String uid, String firstUsername, String secondUsername) {
        this.uid = uid;
        this.firstUsername = firstUsername;
        this.secondUsername = secondUsername;
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
