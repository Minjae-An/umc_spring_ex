package umc.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import umc.spring.domain.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
}
