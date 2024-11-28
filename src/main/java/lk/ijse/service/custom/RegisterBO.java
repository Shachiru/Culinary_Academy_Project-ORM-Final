package lk.ijse.service.custom;

import lk.ijse.dto.RegisterDTO;
import lk.ijse.entity.Register;
import lk.ijse.service.SuperBO;

import java.sql.SQLException;
import java.util.List;

public interface RegisterBO extends SuperBO {
    String generateNextRegId() throws Exception;
}
