package academic.com.service;

import academic.com.dto.request.AcceptanceRequest;
import org.springframework.http.ResponseEntity;

public interface AcceptanceService {

    ResponseEntity<?> register(AcceptanceRequest acceptanceRequest);

    ResponseEntity<?> findById(Long id);

    ResponseEntity<?> editAcceptance(Long id, AcceptanceRequest acceptanceRequest);

    ResponseEntity<?> deleteById(Long id);

}
