package academic.com.repository;

import academic.com.model.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

    Optional<Set<Attachment>> findByClientId(Long clientId);
}
