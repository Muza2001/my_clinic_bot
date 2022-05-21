package academic.com.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminResponse {

    private Long id;
    private String firstname;
    private String lastname;
    private String username;
}
