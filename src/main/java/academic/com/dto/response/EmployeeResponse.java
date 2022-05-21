package academic.com.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeResponse {

    private Long id;
    private String firstname;
    private String lastname;
    private String phoneNumber1;
    private String phoneNumber2;
    private String address;
    private String serviceType;
    private Double serviceCost;
    private String description;
    private Integer withdrawableMoney;

}
