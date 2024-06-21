package dreamtree.jlog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dreamtree.jlog.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
