package com.jlog.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJpaRepository extends MemberRepository, JpaRepository<Member, Long> {
}
