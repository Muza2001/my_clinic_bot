package academic.com.dto.response;

import academic.com.model.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttachmentResponse {

    private Long id;
    private String originalName;
    private String fileName;
    private String FileType;
    private Long clientId;
    private Employee employee;

}
