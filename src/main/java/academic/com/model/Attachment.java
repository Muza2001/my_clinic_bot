package academic.com.model;

import academic.com.model.template.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Attachment extends AbsEntity {

    private String originalName;
    private String fileName;
    private String FileType;
    @Lob
    private byte[] data;
    private Long clientId;
    @ManyToOne(fetch = FetchType.EAGER)
    private Employee employee;

}
