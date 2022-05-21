package academic.com.dto.response;

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
public class DiagnoseResponse {

    private AcceptanceResponse acceptance;
    private Set<AttachmentResponse> attachments = new HashSet<>();

}
