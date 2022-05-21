package academic.com.controller;

import academic.com.dto.request.FileUploadRequest;
import academic.com.service.AttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/file_upload")
@RequiredArgsConstructor
public class AttachmentController {

    private final AttachmentService attachmentService;

    @PostMapping("/save")
    public ResponseEntity<?> uploadDb(@RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.status(201).body(attachmentService.uploadToDb(file));
    }

    @PostMapping("/add_reference/{id}")
    public ResponseEntity<?> addReference(@RequestBody FileUploadRequest fileUploadRequest,
                                          @PathVariable Long id) {
        return ResponseEntity.status(201).body(attachmentService.addReference(fileUploadRequest, id));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(attachmentService.findById(id));
    }

}
