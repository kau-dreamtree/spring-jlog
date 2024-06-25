package dreamtree.jlog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dreamtree.jlog.domain.Member;

public interface MemberJpaRepository extends MemberRepository, JpaRepository<Member, Long> {
}
