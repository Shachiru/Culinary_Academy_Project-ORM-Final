package lk.ijse.service.custom.impl;

import lk.ijse.config.SessionFactoryConfig;
import lk.ijse.dto.UserDTO;
import lk.ijse.entity.User;
import lk.ijse.repository.DAOFactory;
import lk.ijse.repository.custom.UserDAO;
import lk.ijse.service.custom.UserBO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserBOImpl implements UserBO {

    UserDAO userDAO = DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.UserDAO);

    @Override
    public boolean saveUser(UserDTO userDTO) throws Exception {
        Session session = SessionFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            userDAO.setSession(session);
            userDAO.save(userDTO.toEntity());
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
    public boolean updateUser(UserDTO userDTO) throws Exception {
        Session session = SessionFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            userDAO.setSession(session);
            userDAO.update(userDTO.toEntity());
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
    public boolean deleteUser(UserDTO userDTO) throws Exception {
        Session session = SessionFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            userDAO.setSession(session);
            userDAO.delete(userDTO.toEntity());
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
    public ArrayList<UserDTO> getAllUsers() throws SQLException {
        Session session = SessionFactoryConfig.getInstance().getSession();
        userDAO.setSession(session);
        ArrayList<User> users = null;

        try {
            users = userDAO.getAll();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (users == null) {
            return new ArrayList<>();
        }
        ArrayList<UserDTO> userDTOS = new ArrayList<>();
        for (User user : users) {
            userDTOS.add(new UserDTO(
                    user.getId(),
                    user.getName(),
                    user.getPassword(),
                    user.getAddress(),
                    user.getMobile(),
                    user.getEmail()
            ));
        }
        session.close();
        return userDTOS;
    }

    @Override
    public ArrayList<String> loadIds() throws SQLException {
        Session session = SessionFactoryConfig.getInstance().getSession();
        userDAO.setSession(session);
        ArrayList<String> list = userDAO.loadIds();
        session.close();
        return list;
    }

    @Override
    public String generateNextUserId() throws Exception {
        String lastId = userDAO.getLastId();
        return incrementId(lastId);
    }

    private String incrementId(String lastId) {
        if (lastId == null) {
            return "US-0001";
        } else {
            int id = Integer.parseInt(lastId.split("-")[1]);
            id++;
            return String.format("US-%04d", id);
        }
    }
}
