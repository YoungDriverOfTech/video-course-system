package com.course.server.dto;


import java.util.HashSet;
import java.util.List;

public class LoginUserDto {

    /**
     * id
     */
    private String id;

    /**
     * login name
     */
    private String loginName;

    /**
     * sign in token
     */
    private String token;

    /**
     * all resource
     */
    private List<ResourceDto> resources;

    /**
     * all request url, for backend role filter
     */
    private HashSet<String> requests;

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<ResourceDto> getResources() {
        return resources;
    }

    public void setResources(List<ResourceDto> resources) {
        this.resources = resources;
    }

    public HashSet<String> getRequests() {
        return requests;
    }

    public void setRequests(HashSet<String> requests) {
        this.requests = requests;
    }

    @Override
    public String toString() {
        return "LoginUserDto{" +
                "id='" + id + '\'' +
                ", loginName='" + loginName + '\'' +
                ", token='" + token + '\'' +
                ", resources=" + resources +
                ", requests=" + requests +
                '}';
    }

}
