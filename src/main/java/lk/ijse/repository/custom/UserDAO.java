package lk.ijse.repository.custom;

import lk.ijse.entity.User;
import lk.ijse.repository.CrudDAO;

public interface UserDAO extends CrudDAO<User, String> {
    String generateNextId();
}
