package academic.com.service;

import academic.com.dto.request.FileUploadRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AttachmentService {

    ResponseEntity<?> uploadToDb(MultipartFile file) throws IOException;

    ResponseEntity<?> findById(Long id);

    ResponseEntity<?> addReference(FileUploadRequest fileUploadRequest, Long id);
}
