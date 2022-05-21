package academic.com.controller;

import academic.com.dto.request.AdminRequest;
import academic.com.dto.request.LoginRequest;
import academic.com.dto.request.EmployeeRequest;
import academic.com.dto.request.Withdrawable;
import academic.com.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/auth")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register/employee")
    public ResponseEntity<?> registerEmployee(@RequestBody EmployeeRequest dto){
        return ResponseEntity.status(201).body(userService.register(dto));
    }

    @PostMapping("/register/admin")
    public ResponseEntity<?> registerAdmin(@RequestBody AdminRequest dto){
        return ResponseEntity.status(201).body(userService.registerAdmin(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(userService.login(request));
    }

    @PostMapping("/withdrawable money")
    public ResponseEntity<?> setWithdrawable(@RequestBody Withdrawable withdrawable){
        return ResponseEntity.status(203).body(userService.setWithdrawable(withdrawable));
    }

    @PutMapping("/edit/employee/{id}")
    public ResponseEntity<?> editEmployee(@PathVariable Long id, EmployeeRequest dto){
        return ResponseEntity.ok().body(userService.editEmployee(id, dto));
    }

    @PutMapping("/edit/admin/{id}")
    public ResponseEntity<?> editAdmin(@PathVariable Long id, AdminRequest dto){
        return ResponseEntity.ok().body(userService.editAdmin(id, dto));
    }

    @GetMapping("/get_employee_list")
    public ResponseEntity<?> getList() {
        return ResponseEntity.ok().body(userService.findAll());
    }

    @GetMapping("/find_id/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        return ResponseEntity.ok().body(userService.findById(id));
    }

    @DeleteMapping("/delete/employee/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id){
        return ResponseEntity.status(204).body(userService.deleteEmployee(id));
    }

    @DeleteMapping("/delete/admin/{id}")
    public ResponseEntity<?> deleteAdmin(@PathVariable Long id){
        return ResponseEntity.status(204).body(userService.deleteAdmin(id));
    }
}
