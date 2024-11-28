package lk.ijse.repository.custom.impl;

import lk.ijse.entity.Register;
import lk.ijse.repository.custom.RegisterDAO;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.ArrayList;

public class RegisterDAOImpl implements RegisterDAO {

    private Session session;

    @Override
    public String generateNextRegId() throws SQLException, ClassNotFoundException {
        String sql = "SELECT R.id FROM Register AS R ORDER BY R.id DESC";
        Query idquery = session.createQuery(sql);
        String regId = (String) idquery.setMaxResults(1).uniqueResult();
        return regId;
    }

    @Override
    public boolean save(Register entity) throws SQLException, ClassNotFoundException {
        int save = (int) session.save(entity);
        return save > 0;
    }

    @Override
    public void delete(Register entity) throws SQLException, ClassNotFoundException {
        session.delete(entity);
    }

    @Override
    public void update(Register entity) throws SQLException, ClassNotFoundException {
        session.update(entity);
    }

    @Override
    public ArrayList<Register> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<String> loadIds() throws SQLException {
        return null;
    }

    @Override
    public Register search(String id) throws Exception {
        return null;
    }

    @Override
    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public String getLastId() throws Exception {
        return "";
    }
}
