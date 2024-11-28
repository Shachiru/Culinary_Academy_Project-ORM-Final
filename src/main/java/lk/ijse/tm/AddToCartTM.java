package lk.ijse.tm;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddToCartTM {
    private String purchase_id;
    private String student_id;
    private String program_id;
    private String program_fee;
    private double advanceAmount;
    private double balanceAmount;
    private String register_date;
    private JFXButton Remove;
}
