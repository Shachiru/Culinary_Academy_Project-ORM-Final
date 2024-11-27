package lk.ijse.service.custom.impl;

import lk.ijse.dto.AdminDTO;
import lk.ijse.repository.DAOFactory;
import lk.ijse.repository.custom.AdminDAO;
import lk.ijse.service.custom.AdminBO;

import java.sql.ResultSet;
import java.util.ArrayList;

public class AdminBOImpl implements AdminBO {

    AdminDAO adminDAO = DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.AdminDAO);
    @Override
    public boolean saveAdmin(AdminDTO adminDTO) throws Exception {
        return false;
    }

    @Override
    public boolean updateAdmin(AdminDTO adminDTO) throws Exception {
        return false;
    }

    @Override
    public boolean deleteAdmin(AdminDTO adminDTO) throws Exception {
        return false;
    }

    @Override
    public ArrayList<String> loadIds() throws Exception {
        return null;
    }

    @Override
    public String generateNextAdminId() throws Exception {
        return "";
    }

    @Override
    public boolean verifyAdmin(String userName, String password) throws Exception {
        ResultSet resultSet = adminDAO.verifyAdmin(userName, password);
        String pwd = "";
        if (resultSet.next()) {
            pwd = resultSet.getString(1);
            if (pwd.equals(password)) {
                return true;
        }
    }
        return false;
    }
}
