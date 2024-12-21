package com.jlog.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public abstract class BaseEntity {

    public BaseEntity() {
        this(LocalDateTime.now());
    }

    private BaseEntity(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        this.modifiedAt = createdAt;
    }

    @CreatedDate
    @Column(columnDefinition = "datetime(3)", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(columnDefinition = "datetime(3)", nullable = false)
    private LocalDateTime modifiedAt;
}
