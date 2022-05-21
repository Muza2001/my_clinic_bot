package academic.com;

import academic.com.dto.request.AdminRequest;
import academic.com.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MyClinicApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyClinicApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(UserService userService){
        return args -> {
            userService.registerAdmin(
                    new AdminRequest(
                    "Muzaffar",
                    "Mahmudov",
                    "muza",
                    "asd123"));
        };
    }

}
