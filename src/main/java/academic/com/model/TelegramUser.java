package academic.com.model;

import academic.com.model.template.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TelegramUser extends AbsEntity {

    private String firstname;
    private String lastname;
    private String username;
    private String phoneNumber;
    private Long chatId;
    private boolean sendDiagnoseFile = false;
}
