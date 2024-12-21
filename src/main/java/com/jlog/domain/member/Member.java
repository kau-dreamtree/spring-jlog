package com.jlog.domain.member;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import com.jlog.domain.BaseEntity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@ToString
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "name", length = 16, nullable = false)
    private String name;

    @Column(nullable = false)
    private long expense;

    public Member(String name) {
        this(null, name, 0L);
    }

    public boolean matchName(String name) {
        return Objects.equals(this.name, name);
    }

    public void addExpense(long expense) {
        this.expense += expense;
    }

    public void subtractExpense(long expense) {
        if (expense > this.expense) {
            throw new IllegalStateException("Expense can't be less than 0");
        }
        this.expense -= expense;
    }
}
