package lk.ijse.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProgramTM {
    private String id;
    private String name;
    private String seats;
    private String duration;
    private double fee;
}
