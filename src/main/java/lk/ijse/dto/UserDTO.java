package lk.ijse.dto;

import lk.ijse.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String id;
    private String name;
    private String password;
    private String address;
    private String mobile;
    private String email;

    public UserDTO(String id, String userName, String password) {
        this.id = id;
        this.name = userName;
        this.password = password;
    }

    public User toEntity() {
        User user = new User();
        user.setId(this.id);
        user.setName(this.name);
        user.setPassword(this.password);
        user.setAddress(this.address);
        user.setMobile(this.mobile);
        user.setEmail(this.email);
        return user;
    }
}
