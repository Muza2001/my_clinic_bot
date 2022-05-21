package academic.com.service.Impl;

import academic.com.dto.request.FileUploadRequest;
import academic.com.dto.response.AttachmentResponse;
import academic.com.dto.response.Response;
import academic.com.mapper.AttachmentMapper;
import academic.com.model.Acceptance;
import academic.com.model.Attachment;
import academic.com.model.Employee;
import academic.com.repository.AcceptanceRepository;
import academic.com.repository.AttachmentRepository;
import academic.com.repository.EmployeeRepository;
import academic.com.service.AttachmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AttachmentServiceImpl implements AttachmentService {

    private final AttachmentRepository attachmentRepository;
    private final AcceptanceRepository acceptanceRepository;
    private final EmployeeRepository employeeRepository;
    private final AttachmentMapper attachmentMapper;

    @Override
    public ResponseEntity<?> uploadToDb(MultipartFile file) throws IOException {
        Attachment attachment = new Attachment();
        attachment.setData(file.getBytes());
        attachment.setFileName(file.getOriginalFilename());
        attachment.setFileType(file.getContentType());
        attachmentRepository.save(attachment);
        log.info("Save file !!! {}", attachment);
        return ResponseEntity.status(201).body(new Response(
                    "File saved. File id {" + attachment.getId() + "}" +
                            "Please add the information the url { " +
                            "http://localhost8080:/api/v1/add_reference/"
                            + attachment.getId() + " }",
                    true));
    }

    @Override
    public ResponseEntity<?> addReference(FileUploadRequest fileUploadRequest, Long id) {
        Employee employee = employeeRepository.findByPassword(fileUploadRequest.getPassword()).orElseThrow(()
                -> new IllegalArgumentException("Wrong password"));
        Attachment attachment = attachmentRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("Id not found"));
        attachment.setEmployee(employee);
        if (!acceptanceRepository.existsByClientId(fileUploadRequest.getClientId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Response(
                            "Client id not found",
                            false));
        }
        Acceptance acceptance = acceptanceRepository.findByClientId(
                fileUploadRequest.getClientId()).orElseThrow(()
                -> new IllegalArgumentException("Id not found"));
        boolean b = false;
        for (Employee e : acceptance.getEmployees()){
            if (e.equals(employee)){
                b = true;
                break;
            }
        }
        if (b) {
            if (acceptance.isOver()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new Response(
                                "This patient was diagnosed",
                                false));
            }else {
                attachment.setClientId(fileUploadRequest.getClientId());
                attachmentRepository.save(attachment);
                AttachmentResponse response = attachmentMapper.toResponse(attachment);
                return ResponseEntity.ok().body(new Response(
                        "The information was complete",
                        true, response));
            }
        }
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response(
                    "There is no such in id doctor on the patient list",
                    false));
    }

    @Override
    public ResponseEntity<Resource> findById(Long id) {
        Attachment attachment = attachmentRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("Id not found"));
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(attachment.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename = " +attachment.getFileName())
                .body(new ByteArrayResource(attachment.getData()));
    }
}
