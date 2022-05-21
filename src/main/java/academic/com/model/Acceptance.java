package academic.com.model;

import academic.com.model.template.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Acceptance extends AbsEntity {

    private String firstname;
    private String lastname;
    private String phoneNumber;
    private String serviceType;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Employee> employees = new HashSet<>();
    private Long clientId;
    private boolean isOver = false;

}
