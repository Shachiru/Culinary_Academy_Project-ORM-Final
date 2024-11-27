package lk.ijse.repository.custom;

import lk.ijse.entity.Admin;
import lk.ijse.repository.CrudDAO;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface AdminDAO extends CrudDAO<Admin, String> {

    public Admin getAdminById(int id);
    String generateNextId();
    ResultSet verifyAdmin(String userName, String password) throws SQLException, ClassNotFoundException;
}
