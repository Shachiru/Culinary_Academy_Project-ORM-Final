package lk.ijse.repository.custom.impl;

import lk.ijse.config.SessionFactoryConfig;
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
    public boolean save(Student entity) throws SQLException, ClassNotFoundException {
        session.save(entity);
        return false;
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
            List<Student> students = session.createNativeQuery("SELECT * FROM Student", Student.class).getResultList();
            return (ArrayList<Student>) students;
        } catch (Exception e) {
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public ArrayList<String> loadIds() throws SQLException {
       String sql = "SELECT S.id FROM Student AS S";
       Query query = session.createQuery(sql);
       List list = query.list();
       return (ArrayList<String>) list;
    }

    @Override
    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public String getLastId() throws Exception {
        try (Session session = SessionFactoryConfig.getInstance().getSession()) {
            String sql = "SELECT S.id FROM Student AS S ORDER BY S.id DESC";
            Query<String> query = session.createQuery(sql, String.class);
            query.setMaxResults(1);
            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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
