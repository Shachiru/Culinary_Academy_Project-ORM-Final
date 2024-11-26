package lk.ijse.service.custom.impl;

import lk.ijse.config.SessionFactoryConfig;
import lk.ijse.dto.StudentDTO;
import lk.ijse.entity.Student;
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
    public boolean saveStudent(StudentDTO studentDTO) {
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
        Session session = SessionFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            studentDAO.setSession(session);
            studentDAO.update(studentDTO.toEntity());
            transaction.commit();
            session.close();
            return true;
        } catch (HibernateException e) {
            transaction.rollback();
            session.close();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteStudent(StudentDTO studentDTO) throws Exception {
        Session session = SessionFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            studentDAO.setSession(session);
            studentDAO.delete(studentDTO.toEntity());
            transaction.commit();
            session.close();
            return true;
        } catch (HibernateException e) {
            transaction.rollback();
            session.close();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ArrayList<StudentDTO> getAllStudents() throws SQLException {
        Session session = SessionFactoryConfig.getInstance().getSession();
        studentDAO.setSession(session);
        ArrayList<Student> students = null;
        try {
            students = studentDAO.getAll();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (students == null) {
            return new ArrayList<>();
        }
        ArrayList<StudentDTO> studentDTOS = new ArrayList<>();
        for (Student student : students) {
            studentDTOS.add(new StudentDTO(
                    student.getId(),
                    student.getName(),
                    student.getAddress(),
                    student.getContact(),
                    student.getEmail()
            ));
        }
        session.close();
        return studentDTOS;
    }

    @Override
    public ArrayList<String> loadIds() throws SQLException {
        Session session = SessionFactoryConfig.getInstance().getSession();
        studentDAO.setSession(session);
        ArrayList<String> list = studentDAO.loadIds();
        session.close();
        return list;
    }

    @Override
    public String generateNextStudentId() throws Exception {
        String lastId = studentDAO.getLastId();
        return incrementId(lastId);
    }

    private String incrementId(String lastId) {
        if (lastId == null) {
            return "STU-001";
        } else {
            int id = Integer.parseInt(lastId.split("-")[1]);
            id++;
            return String.format("STU-%04d", id);
        }
    }
}
