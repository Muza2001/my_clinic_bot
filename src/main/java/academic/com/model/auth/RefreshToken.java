package academic.com.model.auth;

import academic.com.model.template.AbsEntity;
import lombok.*;

import javax.persistence.Entity;
import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken extends AbsEntity {

    private String refreshToken;

    private Instant createdAt;

}
