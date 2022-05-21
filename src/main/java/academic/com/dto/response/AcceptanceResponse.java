package academic.com.dto.response;

import academic.com.model.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AcceptanceResponse {

    private String firstname;
    private String lastname;
    private String phoneNumber;
    private String serviceType;
    private Set<EmployeeResponse> employees = new HashSet<>();
    private Long clientId;


}
