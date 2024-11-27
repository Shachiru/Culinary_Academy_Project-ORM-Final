package lk.ijse.repository;

import lk.ijse.repository.custom.impl.AdminDAOImpl;
import lk.ijse.repository.custom.impl.ProgramDAOImpl;
import lk.ijse.repository.custom.impl.StudentDAOImpl;
import lk.ijse.repository.custom.impl.UserDAOImpl;

public class DAOFactory {
    private static DAOFactory daoFactory;

    public DAOFactory() {
    }

    public static DAOFactory getDaoFactory() {
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes {
        StudentDAO, ProgramDAO, UserDAO, AdminDAO
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
            default:
                return null;
        }
    }
}
