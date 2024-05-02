package org.example.quanlykhohang.entity;

public class UserSession {
    private static UserSession instance;
    private int userId;
    private String userName;
    private UserSession() {
        // Private constructor to prevent instantiation
    }

    public static synchronized UserSession getInstance() {
        if(instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public void setCurrentUser(int userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }
    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }


    public void clearSession() {
        userId = 0;
        userName = null;
    }
}
