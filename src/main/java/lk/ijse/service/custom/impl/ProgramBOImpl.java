package lk.ijse.service.custom.impl;

import lk.ijse.config.SessionFactoryConfig;
import lk.ijse.dto.ProgramDTO;
import lk.ijse.entity.Program;
import lk.ijse.repository.DAOFactory;
import lk.ijse.repository.custom.ProgramDAO;
import lk.ijse.service.custom.ProgramBO;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProgramBOImpl implements ProgramBO {

    ProgramDAO programDAO = DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ProgramDAO);

    @Override
    public boolean saveProgram(ProgramDTO programDTO) throws Exception {
        Session session = SessionFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            programDAO.setSession(session);
            programDAO.save(programDTO.toEntity());
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
    public boolean updateProgram(ProgramDTO programDTO) throws Exception {
        Session session = SessionFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            programDAO.setSession(session);
            programDAO.update(programDTO.toEntity());
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
    public boolean deleteProgram(ProgramDTO programDTO) throws Exception {
        Session session = SessionFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            programDAO.setSession(session);
            programDAO.delete(programDTO.toEntity());
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
    public ArrayList<ProgramDTO> getAllPrograms() throws SQLException {
        Session session = SessionFactoryConfig.getInstance().getSession();
        programDAO.setSession(session);
        ArrayList<Program> programs = null;
        try {
            programs = programDAO.getAll();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (programs == null) {
            return new ArrayList<>();
        }
        ArrayList<ProgramDTO> programDTOS = new ArrayList<>();
        for (Program program : programs) {
            programDTOS.add(new ProgramDTO(
                    program.getId(),
                    program.getName(),
                    program.getSeats(),
                    program.getDuration(),
                    program.getFee()
            ));
        }
        session.close();
        return programDTOS;
    }

    @Override
    public ArrayList<String> loadIds() throws SQLException {
        Session session = SessionFactoryConfig.getInstance().getSession();
        programDAO.setSession(session);
        ArrayList<String> list = programDAO.loadIds();
        session.close();
        return list;
    }

    @Override
    public String generateNextProgramId() throws Exception {
        String lastId = programDAO.getLastId();
        return incrementId(lastId);
    }

    @Override
    public ProgramDTO searchProgramByName(String name) throws SQLException {
        Session session = SessionFactoryConfig.getInstance().getSession();
        programDAO.setSession(session);
        Program program = programDAO.searchProgramByName(name);
        session.close();
        if (program != null) {
            return new ProgramDTO(
                    program.getId(),
                    program.getName(),
                    program.getSeats(),
                    program.getDuration(),
                    program.getFee());
        } else {
            return null;
        }
    }


    private String incrementId(String lastId) {
        if (lastId == null) {
            return "PRO-0001";
        } else {
            int id = Integer.parseInt(lastId.split("-")[1]);
            id++;
            return String.format("PRO-%04d", id);
        }
    }
}
