package wasp.singleton;

import wasp.DTO.Employee;

public final class LoggedInUser {

    private static volatile LoggedInUser instance;
    private Employee user;

    private LoggedInUser() {
    }

    public static LoggedInUser getInstance() {
        if (instance == null) {
            synchronized (LoggedInUser.class) {
                if (instance == null) {
                    instance = new LoggedInUser();
                }
            }
        }
        return instance;
    }

    public void setUser(Employee user) {
        this.user = user;
    }

    public Employee getUser() {
        return user;
    }

    public void clearUser() {
        user = null;
    }
}
