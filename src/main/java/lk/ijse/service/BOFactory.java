package lk.ijse.service;

import lk.ijse.service.custom.impl.ProgramBOImpl;
import lk.ijse.service.custom.impl.StudentBOImpl;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory() {
    }

    public static BOFactory getBoFactory() {
        return (boFactory == null) ? boFactory = new BOFactory() : boFactory;
    }

    public enum BOTypes {
        StudentBO, ProgramBO
    }

    public <T extends SuperBO> T getBO(BOFactory.BOTypes boType) {
        switch (boType) {
            case StudentBO:
                return (T) new StudentBOImpl();
            case ProgramBO:
                return (T) new ProgramBOImpl();
            default:
                return null;
        }
    }
}
