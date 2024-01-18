package umc.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import umc.spring.domain.common.Uuid;
public interface UuidRepository extends JpaRepository<Uuid, Long> {
}
