package lk.ijse.repository.custom.impl;

import lk.ijse.entity.Admin;
import lk.ijse.repository.custom.AdminDAO;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class AdminDAOImpl implements AdminDAO {

    private Session session;

    @Override
    public Admin getAdminById(int id) {
        return null;
    }

    @Override
    public String generateNextId() {
        String hql = "SELECT A.id FROM Admin AS A ORDER BY A.id DESC";
        Query idquery = session.createQuery(hql);
        String adminId = (String) idquery.setMaxResults(1).uniqueResult();
        return adminId;
    }

    @Override
    public ResultSet verifyAdmin(String userName, String password) {
        ResultSet resultSet = (ResultSet) session.createNativeQuery("SELECT password FROM Admin WHERE name = ?", Admin.class).getResultList();
        return resultSet;
    }

    @Override
    public boolean save(Admin entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public void delete(Admin entity) throws SQLException, ClassNotFoundException {
    }

    @Override
    public void update(Admin entity) throws SQLException, ClassNotFoundException {
    }

    @Override
    public ArrayList<Admin> getAll() throws SQLException, ClassNotFoundException {
        try {
            List<Admin> admins = session.createNativeQuery("SELECT * FROM Admin", Admin.class).getResultList();
            return (ArrayList<Admin>) admins;
        } catch (Exception e) {
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public ArrayList<String> loadIds() throws SQLException {
        return null;
    }

    @Override
    public Admin search(String id) throws Exception {
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
