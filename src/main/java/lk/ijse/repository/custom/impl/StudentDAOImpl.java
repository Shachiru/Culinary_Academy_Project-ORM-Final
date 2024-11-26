package lk.ijse.repository.custom.impl;

import lk.ijse.entity.Student;
import lk.ijse.repository.custom.StudentDAO;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAOImpl implements StudentDAO {
    private Session session;

    @Override
    public void save(Student student) throws SQLException, ClassNotFoundException {
        if (student == null) {
            throw new NullPointerException("Student cannot be null");
        }
        Student existingStudent = session.get(Student.class, student.getId());
        if (existingStudent != null) {
            session.update(student);
        } else {
            session.save(student);
        }
    }

    @Override
    public void delete(Student entity) throws SQLException, ClassNotFoundException {
        session.delete(entity);
    }

    @Override
    public void update(Student entity) throws SQLException, ClassNotFoundException {
        session.update(entity);
    }

    @Override
    public ArrayList<Student> getAll() throws SQLException, ClassNotFoundException {
        try {
            List<Student> studentList = session.createNativeQuery("SELECT * FROM Student", Student.class).getResultList();
            return (ArrayList<Student>) studentList;
        } catch (Exception e) {
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public ArrayList<String> loadIds() throws SQLException {
        // Implement logic to load IDs from database
        String sql = "SELECT id FROM Student";
        Query query = session.createQuery(sql, String.class);
        List<String> idList = query.getResultList();
        return (ArrayList<String>) idList;
    }

    @Override
    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public String generateNextId() {
        String sql = "SELECT S.id FROM Student AS S ORDER BY S.id DESC";
        Query idquery = session.createQuery(sql);
        String studentId = (String) idquery.setMaxResults(1).uniqueResult();
        return studentId;
    }

    @Override
    public int studentCount() { // show total number of students from dashboard
        String sql = "SELECT COUNT(S.id) FROM Student AS S";
        Query query = session.createQuery(sql, Long.class);
        Long count = (Long) query.getSingleResult();
        if (count != null) {
            return Math.toIntExact(count);
        } else {
            return 0;
        }
    }
}
