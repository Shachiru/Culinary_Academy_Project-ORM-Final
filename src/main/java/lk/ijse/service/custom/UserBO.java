package lk.ijse.service.custom;

import lk.ijse.dto.UserDTO;
import lk.ijse.service.SuperBO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserBO extends SuperBO {

    boolean saveUser(UserDTO userDTO) throws Exception;

    boolean updateUser(UserDTO userDTO) throws Exception;

    boolean deleteUser(UserDTO userDTO) throws Exception;

    ArrayList<UserDTO> getAllUsers() throws SQLException;

    ArrayList<String> loadIds() throws SQLException;

    String generateNextUserId() throws Exception;
}
