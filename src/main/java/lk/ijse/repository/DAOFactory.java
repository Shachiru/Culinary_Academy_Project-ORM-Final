package lk.ijse.repository;

import lk.ijse.repository.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;

    public DAOFactory() {
    }

    public static DAOFactory getDaoFactory() {
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes {
        StudentDAO, ProgramDAO, UserDAO, AdminDAO, RegisterDAO
    }

    public <T extends SuperDAO> T getDAO(DAOTypes daoTypes) {
        switch (daoTypes) {
            case StudentDAO:
                return (T) new StudentDAOImpl();
            case ProgramDAO:
                return (T) new ProgramDAOImpl();
            case UserDAO:
                return (T) new UserDAOImpl();
            case AdminDAO:
                return (T) new AdminDAOImpl();
            case RegisterDAO:
                return (T) new RegisterDAOImpl();
            default:
                return null;
        }
    }
}
