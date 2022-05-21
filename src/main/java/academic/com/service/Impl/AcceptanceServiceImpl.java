package academic.com.service.Impl;

import academic.com.dto.request.AcceptanceRequest;
import academic.com.dto.response.AcceptanceResponse;
import academic.com.dto.response.EmployeeResponse;
import academic.com.dto.response.Response;
import academic.com.mapper.AcceptanceMapper;
import academic.com.mapper.EmployeeMapper;
import academic.com.model.Acceptance;
import academic.com.model.Employee;
import academic.com.repository.AcceptanceRepository;
import academic.com.repository.EmployeeRepository;
import academic.com.service.AcceptanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AcceptanceServiceImpl implements AcceptanceService {

    private final AcceptanceRepository acceptanceRepository;
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final AcceptanceMapper acceptanceMapper;
    public String serviceType;
    @Override
    public ResponseEntity<?> register(AcceptanceRequest acceptanceRequest) {
        Set<Employee> employees = employeies(acceptanceRequest.getEmployeesId());
        Acceptance acceptance = acceptanceMapper.toEntity(acceptanceRequest, employees, serviceType);
        acceptanceRepository.save(acceptance);
        Set<EmployeeResponse> employeeResponses = employeeMapper.toResponseList(acceptance.getEmployees());
        AcceptanceResponse acceptanceResponse = acceptanceMapper.toResponse(acceptance, employeeResponses);
        return ResponseEntity.ok().body(new Response("Successfully save", true, acceptanceResponse ));
    }

    @Override
    public ResponseEntity<?> findById(Long id) {
        Acceptance acceptance = acceptanceRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("Id not found"));
        Set<EmployeeResponse> employeeResponses = employeeMapper.toResponseList(acceptance.getEmployees());
        AcceptanceResponse acceptanceResponse =
                acceptanceMapper.toResponse(acceptance, employeeResponses);
        return ResponseEntity.status(200).body(new Response(
                "find Acceptance ",
                true,
                acceptanceResponse));
    }

    public Set<Employee> employeies(Set<Long> employeeId) {
        Set<Employee> employees = new HashSet<>();
        for(Long i : employeeId){
            if (i != null){
                Employee employee = employeeRepository.findById(i).orElseThrow(()
                        -> new IllegalArgumentException("Id not found"));
                employees.add(employee);
                serviceType += employee.getServiceType();
            }
        }
        return employees;
    }

    @Override
    public ResponseEntity<?> editAcceptance(Long id, AcceptanceRequest acceptanceRequest) {
        Acceptance acceptance = acceptanceRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("Id not found"));
        Set<Employee> employees = employeies(acceptanceRequest.getEmployeesId());

        if (!acceptanceRequest.getLastname().equals(acceptance.getLastname()))
            acceptance.setLastname(acceptanceRequest.getLastname());

        if (!acceptanceRequest.getFirstname().equals(acceptance.getFirstname()))
            acceptance.setFirstname(acceptanceRequest.getFirstname());

        if (!acceptanceRequest.getPhoneNumber().equals(acceptance.getPhoneNumber()))
            acceptance.setPhoneNumber(acceptanceRequest.getPhoneNumber());

        if (!acceptanceRequest.getLastname().equals(acceptance.getLastname()))
            acceptance.setLastname(acceptanceRequest.getLastname());

        if (!employees.equals(acceptance.getEmployees()))
            acceptance.setEmployees(employees);

        acceptanceRepository.save(acceptance);
        Set<EmployeeResponse> employeeResponses = employeeMapper.toResponseList(acceptance.getEmployees());
        AcceptanceResponse response = acceptanceMapper.toResponse(acceptance, employeeResponses);

        return ResponseEntity.ok().body(new Response
                (
               "Acceptance successfully edited !!!",
                true ,
                       response
                )
        );
    }

    @Override
    public ResponseEntity<?> deleteById(Long id) {
        acceptanceRepository.deleteById(id);
        return ResponseEntity.status(204).body("Successfully deleted !!!");
    }
}
