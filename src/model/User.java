package model;

/**
 * @author 软工1801温蟾圆
 */
public class User {
    private String username;
    private String password;
    private boolean isAdministrator;

    public User() {}

    public User(String username, String password, boolean isAdministrator) {
        this.username = username;
        this.password = password;
        this.isAdministrator = isAdministrator;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdministrator() {
        return isAdministrator;
    }

    public void setAdministrator(boolean administrator) {
        isAdministrator = administrator;
    }
}
