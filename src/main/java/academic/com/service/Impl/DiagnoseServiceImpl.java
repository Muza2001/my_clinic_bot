package academic.com.service.Impl;

import academic.com.dto.request.DiagnosisRequest;
import academic.com.dto.response.*;
import academic.com.mapper.AcceptanceMapper;
import academic.com.mapper.AttachmentMapper;
import academic.com.mapper.EmployeeMapper;
import academic.com.model.Acceptance;
import academic.com.model.Attachment;
import academic.com.model.Diagnose;
import academic.com.model.Employee;
import academic.com.repository.AcceptanceRepository;
import academic.com.repository.AttachmentRepository;
import academic.com.repository.DiagnosisRepository;
import academic.com.repository.EmployeeRepository;
import academic.com.service.DiagnoseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class DiagnoseServiceImpl implements DiagnoseService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final AcceptanceRepository acceptanceRepository;
    private final AttachmentRepository attachmentRepository;
    private final DiagnosisRepository diagnosisRepository;
    private final AcceptanceMapper acceptanceMapper;
    private final AttachmentMapper attachmentMapper;

    @Override
    public ResponseEntity<?> save(DiagnosisRequest request) {
        Employee employee = employeeRepository.findByPassword(request.getDoctorPassword()).orElseThrow(()
                -> new IllegalArgumentException("Wrong password"));
        Set<Attachment> attachments = findByClientIdInAttachment(request.getClientId());
        Acceptance acceptance = findByClientIdInAcceptance(request.getClientId());
        boolean b = false;
        for (Employee e : acceptance.getEmployees()) {
            if (e.getId().equals(employee.getId())){
                b = true;
                break;
            }
        }
        if (!b){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response(
                    "There is no such in id doctor on the patient list",
                    false));
        }
        else {
            acceptance.setOver(true);
            acceptanceRepository.save(acceptance);
            diagnosisRepository.save(new Diagnose(acceptance, attachments));
            Set<EmployeeResponse> employeeResponses = employeeMapper.toResponseList(acceptance.getEmployees());
            AcceptanceResponse acceptanceResponse = acceptanceMapper.toResponse(acceptance, employeeResponses);
            Set<AttachmentResponse> attachmentResponses = attachmentMapper.toListResponse(attachments);
            DiagnoseResponse response = new DiagnoseResponse(acceptanceResponse, attachmentResponses);
            return ResponseEntity.status(201).body(new Response(
                    "Diagnose successfully saved",
                     true,
                    response));
        }
    }

    public Set<Attachment> findByClientIdInAttachment(Long id){
        return attachmentRepository.findByClientId(id).orElseThrow(()
                -> new IllegalArgumentException("Wrong id"));
    }

    public Acceptance findByClientIdInAcceptance(Long id){
        return acceptanceRepository.findByClientId(id).orElseThrow(()
                -> new IllegalArgumentException("Client id not found"));
    }

    @Override
    public ResponseEntity<?> findById(Long id) {
        Diagnose diagnose = diagnosisRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("Id not found"));
        Set<EmployeeResponse> employeeResponses = employeeMapper.toResponseList(diagnose.getAcceptance().getEmployees());
        return ResponseEntity.ok().body(
                new Response(
                        "Diagnose find !!!",
                         true,
                new DiagnoseResponse(
                        acceptanceMapper.toResponse(diagnose.getAcceptance(), employeeResponses),
                        attachmentMapper.toListResponse(diagnose.getAttachments()))));
    }

    @Override
    public ResponseEntity<?> findList() {
        List<Employee> employees = employeeRepository.findAll();
        Set<EmployeeResponse> employeeResponses = new HashSet<>();
        for (Employee e: employees) {
            if (e != null){
                employeeResponses.add(employeeMapper.toDto(e));
            }
        }
        return ResponseEntity.ok().body(
                diagnosisRepository.findAll()
                        .stream()
                        .map(diagnose
                                -> new DiagnoseResponse(
                                acceptanceMapper.toResponse(diagnose.getAcceptance(), employeeResponses),
                                attachmentMapper.toListResponse(diagnose.getAttachments())))
                        .collect(Collectors.toList())
        );
    }

    @Override
    public ResponseEntity<?> edit(DiagnosisRequest request, Long id) {

        Diagnose diagnose = diagnosisRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("Diagnose id not found"));
        Set<Attachment> attachments = findByClientIdInAttachment(request.getClientId());
        Acceptance acceptance = findByClientIdInAcceptance(request.getClientId());
        if (employeeRepository.existsByPassword(request.getDoctorPassword())) {
            if (!diagnose.getAcceptance().equals(acceptance))
                diagnose.setAcceptance(acceptance);

            if (!diagnose.getAttachments().equals(attachments))
                diagnose.setAttachments(attachments);
            diagnosisRepository.save(diagnose);
            Set<EmployeeResponse> employeeResponses = employeeMapper.toResponseList(diagnose.getAcceptance().getEmployees());
            AcceptanceResponse acceptanceResponse = acceptanceMapper.toResponse(acceptance, employeeResponses);
            Set<AttachmentResponse> attachmentResponses = attachmentMapper.toListResponse(attachments);
            DiagnoseResponse response = new DiagnoseResponse(acceptanceResponse, attachmentResponses);
            return ResponseEntity.status(201).body(new Response(
                    "Diagnose successfully saved",
                    true,
                    response));
        } else
              return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                      new Response(
                              "Wrong password ",
                              false));
    }
}
