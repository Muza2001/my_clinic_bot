package academic.com.model;

import academic.com.model.template.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employee")
public class Employee extends AbsEntity {

    private String firstname;

    private String lastname;

    @Column(unique = true, nullable = false)
    private String password;

    @Column(unique = true, length = 14, nullable = false)
    private String phoneNumber1;

    @Column(unique = true, length = 14)
    private String phoneNumber2;

    private String address;

    private String serviceType;

    private Instant expiration;

    private Double serviceCost;

    private Integer withdrawableMoney; // olib qolinadigan summa foyizda (10, 20, ...) bu admin tomonidan kiritiladi

    private String description;

}
