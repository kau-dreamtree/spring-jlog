package com.jlog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jlog.domain.Member;

public interface MemberJpaRepository extends MemberRepository, JpaRepository<Member, Long> {
}
