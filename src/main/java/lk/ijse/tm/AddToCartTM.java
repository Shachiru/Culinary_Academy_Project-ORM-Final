package lk.ijse.tm;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddToCartTM {
    private String registrationId;
    private String studentId;
    private String courseId;
    private double courseFee;
    private double advance;
    private JFXButton remove;
}
