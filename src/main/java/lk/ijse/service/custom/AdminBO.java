package lk.ijse.service.custom;

import lk.ijse.dto.AdminDTO;
import lk.ijse.service.SuperBO;

import java.util.ArrayList;

public interface AdminBO extends SuperBO {

    boolean saveAdmin(AdminDTO adminDTO) throws Exception;

    boolean updateAdmin(AdminDTO adminDTO) throws Exception;

    boolean deleteAdmin(AdminDTO adminDTO) throws Exception;

    ArrayList<String> loadIds() throws Exception;

    String generateNextAdminId() throws Exception;

    boolean verifyAdmin(String userName, String password) throws Exception;

}
