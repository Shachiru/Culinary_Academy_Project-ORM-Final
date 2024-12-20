package lk.ijse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "Register")
public class Register {
    @Id
    @Column(name = "reg_id")
    private String reg_id;

    @CreationTimestamp
    private LocalDate reg_date;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student_id;

    @ManyToOne
    @JoinColumn(name = "program_id")
    private Program pId;

}
