package academic.com.service;

import academic.com.dto.request.LoginRequest;
import academic.com.dto.request.AdminRequest;
import academic.com.dto.request.EmployeeRequest;
import academic.com.dto.request.Withdrawable;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<?> register(EmployeeRequest userDto);

    ResponseEntity<?> login(LoginRequest request);

    ResponseEntity<?> registerAdmin(AdminRequest admin);

    ResponseEntity<?> editEmployee(Long id, EmployeeRequest dto);

    ResponseEntity<?> editAdmin(Long id, AdminRequest dto);

    ResponseEntity<?> deleteEmployee(Long id);

    ResponseEntity<?> deleteAdmin(Long id);

    ResponseEntity<?> setWithdrawable(Withdrawable withdrawable);

    ResponseEntity<?> findAll();

    ResponseEntity<?> findById(Long id);
}
