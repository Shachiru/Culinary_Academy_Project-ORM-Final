package lk.ijse.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTM {
    private String id;
    private String name;
    private String password;
    private String address;
    private String mobile;
    private String email;
}
