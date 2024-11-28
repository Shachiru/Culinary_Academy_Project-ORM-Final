package lk.ijse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "Program")
public class Program {
    @Id
    @Column(name = "program_id")
    private String id;

    @Column(name = "program_name")
    private String name;

    @Column(name = "program_seats")
    private String seats;

    @Column(name = "program_duration")
    private String duration;

    @Column(name = "program_fee")
    private double fee;
}
