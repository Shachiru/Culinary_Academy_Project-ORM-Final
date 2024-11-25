package lk.ijse.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentTM {
    private Long id;
    private String name;
    private String address;
    private String contact;
    private String email;
}
