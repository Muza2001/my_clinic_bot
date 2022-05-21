package academic.com.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TelegramContact {

    private String firstname;
    private String lastname;
    private String username;
    private String phoneNumber;
    private Long chatId;
    private Long userId;

}
