package com.course.server.dto;


public class UserDto {

    /**
     * id
     */
    private String id;

    /**
     * login name
     */
    private String loginName;

    /**
     * nick name
     */
    private String name;

    /**
     * password
     */
    private String password;

    /**
     * certification
     */
    private String imageCode;

    /**
     * certification token
     */
    private String imageCodeToken;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImageCode() {
        return imageCode;
    }

    public void setImageCode(String imageCode) {
        this.imageCode = imageCode;
    }

    public String getImageCodeToken() {
        return imageCodeToken;
    }

    public void setImageCodeToken(String imageCodeToken) {
        this.imageCodeToken = imageCodeToken;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id='" + id + '\'' +
                ", loginName='" + loginName + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", imageCode='" + imageCode + '\'' +
                ", imageCodeToken='" + imageCodeToken + '\'' +
                '}';
    }
}
