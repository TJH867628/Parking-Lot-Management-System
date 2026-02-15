package controller;

import dao.FineDAO;
import model.Iterator.FineSchemeIterator;

public class AdminController {
    private final String ADMIN_USER = "admin";
    private final String ADMIN_PASS = "123"; // later replace with DB lookup

    public boolean login(String username, String password) {
        return ADMIN_USER.equals(username) && ADMIN_PASS.equals(password);
    }

    public FineSchemeIterator getAllFineSchemes() {
        FineDAO fineDAO = new FineDAO();
        try {
            return fineDAO.getAllFineSchemes();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void changeFineScheme(int newSchemeId) {
        FineDAO fineDAO = new FineDAO();
        try {
            fineDAO.changeFineScheme(newSchemeId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
