package academic.com.model;

import academic.com.model.template.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Diagnose extends AbsEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    private Acceptance acceptance;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Attachment> attachments = new HashSet<>();

}
