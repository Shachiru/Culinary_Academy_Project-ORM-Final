package lk.ijse.repository.custom;

import lk.ijse.entity.Student;
import lk.ijse.repository.CrudDAO;

public interface StudentDAO extends CrudDAO<Student,String> {
    String generateNextId();
    int studentCount();
}
