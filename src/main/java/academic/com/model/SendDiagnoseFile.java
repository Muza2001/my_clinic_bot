package academic.com.model;

import academic.com.model.template.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SendDiagnoseFile extends AbsEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    private TelegramUser telegramUser;

    @ManyToOne(fetch = FetchType.EAGER)
    private Diagnose diagnoses;

}
