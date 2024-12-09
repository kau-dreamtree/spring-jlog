package com.jlog.domain;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(indexes = {
        @Index(name = "log_created_date_idx", columnList = "created_date"),
        @Index(name = "log_modified_date_idx", columnList = "modified_date"),
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = "id", callSuper = false)
@ToString
public class Log extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Room room;

    @ManyToOne(optional = false)
    private Member member;

    @Column(nullable = false)
    private long expense;

    private String memo;

    @Builder
    public Log(Room room, Member member, long expense, String memo) {
        this(null, room, member, expense, memo);
    }

    public boolean ownedBy(Member member) {
        return Objects.equals(this.member, member);
    }

    public void updateExpense(long expense) {
        if (expense < 0L) {
            throw new IllegalStateException("An expense must be a positive integer.");
        }
        this.expense = expense;
    }

    public void updateMemo(String memo) {
        this.memo = memo;
    }
}
