package academic.com.model;

import academic.com.model.template.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Admin extends AbsEntity {

    private String firstname;

    private String lastname;

    private String password;

    @Column(unique = true, name = "username")
    private String username;

    private Instant expiration;

    private Boolean enabled;

}
