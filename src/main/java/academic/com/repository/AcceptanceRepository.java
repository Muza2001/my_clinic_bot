package academic.com.repository;

import academic.com.model.Acceptance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AcceptanceRepository extends JpaRepository<Acceptance, Long> {

    Optional<Acceptance> findByClientId(Long clientId);

    boolean existsByClientId(Long clientId);

}
