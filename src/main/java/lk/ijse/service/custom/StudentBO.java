package lk.ijse.service.custom;

import lk.ijse.dto.StudentDTO;
import lk.ijse.service.SuperBO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface StudentBO extends SuperBO {

    boolean saveStudent(StudentDTO studentDTO) throws Exception;

    boolean updateStudent(StudentDTO studentDTO)throws Exception;

    boolean deleteStudent(StudentDTO studentDTO) throws Exception;

    ArrayList<StudentDTO> getAllStudents() throws SQLException;

    boolean loadIds(String id) throws Exception;

    List<StudentDTO> findAllStudents();
}
