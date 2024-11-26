package lk.ijse.repository;

import lk.ijse.repository.custom.impl.ProgramDAOImpl;
import lk.ijse.repository.custom.impl.StudentDAOImpl;

public class DAOFactory {
    private static DAOFactory daoFactory;

    public DAOFactory() {
    }

    public static DAOFactory getDaoFactory() {
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes {
        StudentDAO, ProgramDAO
    }

    public <T extends SuperDAO> T getDAO(DAOTypes daoTypes) {
        switch (daoTypes) {
            case StudentDAO:
                return (T) new StudentDAOImpl();
            case ProgramDAO:
                return (T) new ProgramDAOImpl();
            default:
                return null;
        }
    }
}
