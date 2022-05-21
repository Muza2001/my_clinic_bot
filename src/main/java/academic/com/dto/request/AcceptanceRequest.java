package academic.com.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AcceptanceRequest {

    private String firstname;
    private String lastname;
    private String phoneNumber;
    private Set<Long> employeesId = new HashSet<>();

}
