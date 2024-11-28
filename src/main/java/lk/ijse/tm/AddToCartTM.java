package lk.ijse.tm;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddToCartTM {
    private String register_id;
    private String student_id;
    private String program_id;
    private String program_fee;
    private double advance_amount;
    private double remaining_amount;
    private String register_date;
    private JFXButton remove;
}
