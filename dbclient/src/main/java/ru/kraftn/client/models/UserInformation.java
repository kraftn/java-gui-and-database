package ru.kraftn.client.models;

public class UserInformation {
    private String userName;
    private String roleName;
    private String loginName;

    public UserInformation(String userName, String roleName, String loginName) {
        this.userName = userName;
        this.roleName = roleName;
        this.loginName = loginName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
}
