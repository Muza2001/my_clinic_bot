package academic.com.service;

import academic.com.dto.request.DiagnosisRequest;
import org.springframework.http.ResponseEntity;

public interface DiagnoseService {
    ResponseEntity<?> save(DiagnosisRequest request);

    ResponseEntity<?> edit(DiagnosisRequest request, Long id);

    ResponseEntity<?> findById(Long id);

    ResponseEntity<?> findList();
}



