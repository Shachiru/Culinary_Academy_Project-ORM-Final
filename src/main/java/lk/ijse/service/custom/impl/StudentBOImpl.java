package lk.ijse.service.custom.impl;

import lk.ijse.config.SessionFactoryConfig;
import lk.ijse.dto.StudentDTO;
import lk.ijse.repository.DAOFactory;
import lk.ijse.repository.custom.StudentDAO;
import lk.ijse.service.custom.StudentBO;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentBOImpl implements StudentBO {

    StudentDAO studentDAO = DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.StudentDAO);

    @Override
    public boolean saveStudent(StudentDTO studentDTO) throws Exception {
        Session session = SessionFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            studentDAO.setSession(session);
            studentDAO.save(studentDTO.toEntity());
            transaction.commit();
            session.close();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            session.close();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateStudent(StudentDTO studentDTO) throws Exception {
        return false;
    }

    @Override
    public boolean deleteStudent(StudentDTO studentDTO) throws Exception {
        return false;
    }

    @Override
    public ArrayList<StudentDTO> getAllStudents() throws SQLException {
        return null;
    }

    @Override
    public boolean loadIds(String id) throws Exception {
        return false;
    }

    @Override
    public List<StudentDTO> findAllStudents() {
        return List.of();
    }
}
