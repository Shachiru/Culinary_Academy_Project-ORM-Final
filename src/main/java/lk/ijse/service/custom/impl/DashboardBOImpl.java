package lk.ijse.service.custom.impl;

import lk.ijse.config.SessionFactoryConfig;
import lk.ijse.repository.DAOFactory;
import lk.ijse.repository.custom.ProgramDAO;
import lk.ijse.repository.custom.StudentDAO;
import lk.ijse.service.custom.DashboardBO;
import org.hibernate.Session;

public class DashboardBOImpl implements DashboardBO {

    StudentDAO studentDAO = DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.StudentDAO);
    ProgramDAO programDAO = DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ProgramDAO);

    @Override
    public int studentCount() {
        Session session = SessionFactoryConfig.getInstance().getSession();
        try {
            studentDAO.setSession(session);
            int count = studentDAO.studentCount();
            session.close();
            return count;
        }catch (Exception e){
            session.close();
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int programCount() {
        Session session = SessionFactoryConfig.getInstance().getSession();
        try {
            programDAO.setSession(session);
            int count = programDAO.programCount();
            session.close();
            return count;
        }catch (Exception e){
            session.close();
            e.printStackTrace();
            return -1;
        }
    }
}
