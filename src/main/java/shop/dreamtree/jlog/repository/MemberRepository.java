package shop.dreamtree.jlog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import shop.dreamtree.jlog.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
