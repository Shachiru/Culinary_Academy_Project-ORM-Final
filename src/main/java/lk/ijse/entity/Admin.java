package lk.ijse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "Admin")
public class Admin {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)       // get error in hibernate (not load db table)because of this
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

//    @OneToMany(mappedBy = "admin")
//    private List<Student> students = new ArrayList<>();
}
