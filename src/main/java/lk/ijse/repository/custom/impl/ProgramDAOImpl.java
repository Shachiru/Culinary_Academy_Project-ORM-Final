package lk.ijse.repository.custom.impl;

import lk.ijse.config.SessionFactoryConfig;
import lk.ijse.entity.Program;
import lk.ijse.repository.custom.ProgramDAO;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProgramDAOImpl implements ProgramDAO {

    private Session session;

    @Override
    public boolean save(Program entity) throws SQLException, ClassNotFoundException {
        session.save(entity);
        return false;
    }

    @Override
    public void delete(Program entity) throws SQLException, ClassNotFoundException {
        session.delete(entity);
    }

    @Override
    public void update(Program entity) throws SQLException, ClassNotFoundException {
        session.update(entity);
    }

    @Override
    public ArrayList<Program> getAll() throws SQLException, ClassNotFoundException {
        try {
            List<Program> programs = session.createNativeQuery("SELECT * FROM Program", Program.class).getResultList();
            return (ArrayList<Program>) programs;
        } catch (Exception e) {
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public ArrayList<String> loadIds() throws SQLException {
        String sql = "SELECT P.id FROM Program AS P";
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
            String sql = "SELECT P.id FROM Program AS P ORDER BY P.id DESC";
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
        String sql = "SELECT P.id FROM Program AS P ORDER BY P.id DESC";
        Query idquery = session.createQuery(sql);
        String programId = (String) idquery.setMaxResults(1).uniqueResult();
        return programId;
    }

    @Override
    public int programCount() {
        String sql = "SELECT COUNT(P.id) FROM Program AS P";
        Query query = session.createQuery(sql, Long.class);
        Long count = (Long) query.getSingleResult();
        if (count != null) {
            return Math.toIntExact(count);
        } else {
            return 0;
        }
    }

    @Override
    public Program search(String id) throws Exception {
        try (Session session = SessionFactoryConfig.getInstance().getSession()) {
            return session.get(Program.class, id);
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
