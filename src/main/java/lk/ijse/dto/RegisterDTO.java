package lk.ijse.dto;

import lk.ijse.entity.Program;
import lk.ijse.entity.Register;
import lk.ijse.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {
    private String reg_id;
    private LocalDate reg_date;
    private Student student_id;
    private Program program_id;

}
