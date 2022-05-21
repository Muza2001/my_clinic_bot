package academic.com.service.Impl;

import academic.com.dto.request.AdminRequest;
import academic.com.dto.request.Withdrawable;
import academic.com.dto.response.AdminResponse;
import academic.com.dto.response.AuthenticationResponse;
import academic.com.dto.request.LoginRequest;
import academic.com.dto.request.EmployeeRequest;
import academic.com.dto.response.EmployeeResponse;
import academic.com.dto.response.Response;
import academic.com.mapper.AdminMapper;
import academic.com.mapper.EmployeeMapper;
import academic.com.model.Admin;
import academic.com.model.Employee;
import academic.com.repository.AdminRepository;
import academic.com.repository.EmployeeRepository;
import academic.com.security.JwtProvider;
import academic.com.service.UserService;
import academic.com.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class MyUserService implements UserDetailsService, UserService {

    private final EmployeeRepository employeeRepository;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;
    private final EmployeeMapper employeeMapper;
    private final AdminMapper adminMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin user = adminRepository.findByUsername(username).orElseThrow(()
                -> new IllegalArgumentException("Email not found"));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getEnabled(),
                true,
                true,
                true,
                grantedAuthority("USER")
        );
    }



    private Collection<? extends GrantedAuthority> grantedAuthority(String user) {
        return Collections.singletonList(new SimpleGrantedAuthority(user));
    }

    @Override
    public ResponseEntity<?> register(EmployeeRequest userDto) {
         Employee employee = (Employee) userRegister(userDto);
         if (employee != null) {
             EmployeeResponse response = employeeMapper.toDto(employee);
             return ResponseEntity.status(201).body(new Response(
                     "User successfully saved",
                     true,
                     response));
         }
         else {
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                     body(new Response(
                             "Please enter another password",
                             false));
         }
    }
    @Override
    public ResponseEntity<?> registerAdmin(AdminRequest admin) {
        Admin admin1 = (Admin) userRegister(admin);
        AdminResponse response = adminMapper.toDto(admin1);
        return ResponseEntity.status(201).body(new Response(
                "Admin successfully saved",
                true ,
                response));
    }

    @Override
    public ResponseEntity<?> setWithdrawable(Withdrawable withdrawable) {
        Employee employee = employeeRepository.findById(withdrawable.getEmployeeId()).orElseThrow(()
                -> new IllegalArgumentException("Employee id not found"));
        if (withdrawable.getPercentage() > 0){
            employee.setWithdrawableMoney(withdrawable.getPercentage());
        }
        employeeRepository.save(employee);
        return ResponseEntity.status(203).body(new
                Response(
                       "Employee edited successfully",
                        true,
                               employee));
    }

    @Value("${general.expiration.time}")
    Long EXPIRATION_TIME;
    @Override
    public ResponseEntity<?> login(LoginRequest request) {
        Authentication authenticate =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String generateToken = jwtProvider.generateToken((org.springframework.security.core.userdetails.User) authenticate.getPrincipal());
        // TODO: 2/25/22 check if user login details match, if not handle it.

        AuthenticationResponse response = AuthenticationResponse.builder()
                .authenticationToken(generateToken)
                .refreshToken(refreshTokenService.generateRefreshToken().getRefreshToken())
                .username(request.getUsername())
                .expirationData(Instant.now().plusMillis(EXPIRATION_TIME))
                .build();
        return ResponseEntity.ok(response);
    }

    public Object userRegister(Object object){
        if (object instanceof AdminRequest){
            AdminRequest admin = (AdminRequest) object;
            Admin user = adminMapper.toEntity(admin);
            user.setPassword(passwordEncoder.encode(admin.getPassword()));
            adminRepository.save(user);
            log.info("User save {}", user);
            return user;
        }
        if (object instanceof EmployeeRequest){
            EmployeeRequest employeeRegister = (EmployeeRequest) object;
            if (!employeeRepository.existsByPassword(employeeRegister.getPassword())){
                Employee employee = employeeMapper.toEntity(employeeRegister);
                employee.setPassword(employeeRegister.getPassword());
                employeeRepository.save(employee);
                log.info("User save {}", employee);
                return employee;
            }
            else {
                return "Password already exists";
            }
        }
        return null;
    }

    @Override
    public ResponseEntity<?> findAll() {
        List<Employee> employees = employeeRepository.findAll();
        Set<EmployeeResponse> employeeResponses = new HashSet<>();
        for (Employee e: employees) {
            if (e != null){
                employeeResponses.add(employeeMapper.toDto(e));
            }
        }
        return ResponseEntity.ok().body(new Response("Employees find", true, employeeResponses));
    }

    @Override
    public ResponseEntity<?> findById(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("Id not found"));
        EmployeeResponse employeeResponse = employeeMapper.toDto(employee);
        return ResponseEntity.ok().body(new Response(
                "Employee find",
                true,
                employeeResponse));
    }

    @Override
    public ResponseEntity<?> editEmployee(Long id, EmployeeRequest dto) {
        Employee employee = employeeRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("Id not found"));

        if (!employee.getAddress().equals(dto.getAddress()))
            employee.setAddress(dto.getAddress());

        if (!employee.getDescription().equals(dto.getDescription()))
            employee.setDescription(dto.getDescription());

        if (!employee.getServiceCost().equals(dto.getServiceCost()))
            employee.setServiceCost(dto.getServiceCost());

        if (!employee.getServiceType().equals(dto.getServiceType()))
            employee.setServiceType(dto.getServiceType());

        if (!employee.getFirstname().equals(dto.getFirstname()))
            employee.setFirstname(dto.getFirstname());

        if (!employee.getLastname().equals(dto.getLastname()))
            employee.setLastname(dto.getLastname());

        if (!employee.getPhoneNumber1().equals(dto.getPhoneNumber1()))
            employee.setPhoneNumber1(dto.getPhoneNumber1());

        if (!employee.getPhoneNumber2().equals(dto.getPhoneNumber2()))
            employee.setPhoneNumber2(dto.getPhoneNumber2());

        employeeRepository.save(employee);
        EmployeeResponse response = employeeMapper.toDto(employee);
        return ResponseEntity.status(202).body(response);
    }

    @Override
    public ResponseEntity<?> editAdmin(Long id, AdminRequest dto) {
        Admin admin = adminRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("Id not found"));
        if (!admin.getUsername().equals(dto.getUsername()))
            admin.setUsername(dto.getUsername());

        if (!admin.getFirstname().equals(dto.getFirstname()))
            admin.setFirstname(dto.getFirstname());

        if (!admin.getLastname().equals(dto.getLastname()))
            admin.setLastname(dto.getLastname());

        adminRepository.save(admin);
        AdminResponse response = adminMapper.toDto(admin);
        return ResponseEntity.status(202).body(response);
    }

    @Override
    public ResponseEntity<?> deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(()
                -> new IllegalCallerException("Id not found"));
        employeeRepository.delete(employee);
        log.info("Employee deleted. Employee id {}", employee);
        return ResponseEntity.status(205).body("Employee deleted successfully");
    }

    @Override
    public ResponseEntity<?> deleteAdmin(Long id) {
        Admin admin = adminRepository.findById(id).orElseThrow(()
                -> new IllegalCallerException("Id not found"));
        List<Admin> admins = adminRepository.findAll();
        if (admins.size() > 1) {
            adminRepository.delete(admin);
            log.info("Admin deleted. Employee id {}", admin);
            return ResponseEntity.status(205).body("Admin deleted successfully");
        }
        else {
            return ResponseEntity.status(400).body("this admin last admin cannot disable it");
        }
    }
}
