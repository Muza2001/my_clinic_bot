package academic.com.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class EmployeeRequest {

    private String firstname;
    private String lastname;
    private String password;
    private String phoneNumber1;
    private String phoneNumber2;
    private String address;
    private String serviceType;
    private Double serviceCost;
    private String description;

}
