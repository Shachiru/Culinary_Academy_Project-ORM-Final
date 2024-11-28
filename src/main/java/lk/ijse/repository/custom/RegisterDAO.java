package lk.ijse.repository.custom;

import lk.ijse.entity.Register;
import lk.ijse.repository.CrudDAO;

import java.sql.SQLException;

public interface RegisterDAO extends CrudDAO<Register, String> {
    
    String generateNextRegId() throws SQLException, ClassNotFoundException;
}
