package controller;

public class AdminController {
    private final String ADMIN_USER = "admin";
    private final String ADMIN_PASS = "123"; // later replace with DB lookup

    public boolean login(String username, String password) {
        return ADMIN_USER.equals(username) && ADMIN_PASS.equals(password);
    }
}
