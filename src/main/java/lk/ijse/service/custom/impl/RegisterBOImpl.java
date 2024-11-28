package lk.ijse.service.custom.impl;

import lk.ijse.repository.DAOFactory;
import lk.ijse.repository.custom.RegisterDAO;
import lk.ijse.service.custom.RegisterBO;

import java.time.Year;

public class RegisterBOImpl implements RegisterBO {
    RegisterDAO registerDAO = DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.RegisterDAO);

    @Override
    public String generateNextRegId() throws Exception {
        String lastId = registerDAO.getLastId();
        return incrementId(lastId);
    }

    private String incrementId(String lastId) {
        String currentYear = String.valueOf(Year.now().getValue());
        if(lastId == null || lastId.isEmpty()) {
            return String.format("REG-%s-0001", currentYear);
        }

        String[] parts = lastId.split("-");
        String lastYear = parts[2];
        int lastSequence = Integer.parseInt(parts[3]);

        if (!lastYear.equals(currentYear)) {
            return String.format("REG-%s-0001", currentYear);
        }
        lastSequence++;
        return String.format("REG-%s-%04d", currentYear, lastSequence);
    }
}
