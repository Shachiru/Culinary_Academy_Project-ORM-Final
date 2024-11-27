package lk.ijse.service;

import lk.ijse.service.custom.impl.DashboardBOImpl;
import lk.ijse.service.custom.impl.ProgramBOImpl;
import lk.ijse.service.custom.impl.StudentBOImpl;
import lk.ijse.service.custom.impl.UserBOImpl;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory() {
    }

    public static BOFactory getBoFactory() {
        return (boFactory == null) ? boFactory = new BOFactory() : boFactory;
    }

    public enum BOTypes {
        StudentBO, ProgramBO, UserBO, DashboardBO
    }

    public <T extends SuperBO> T getBO(BOFactory.BOTypes boType) {
        switch (boType) {
            case StudentBO:
                return (T) new StudentBOImpl();
            case ProgramBO:
                return (T) new ProgramBOImpl();
            case UserBO:
                return (T) new UserBOImpl();
            case DashboardBO:
                return (T) new DashboardBOImpl();
            default:
                return null;
        }
    }
}
