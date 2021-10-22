package com.course.server.dto;


import java.util.List;

public class ResourceDto {

    /**
     * id
     */
    private String id;

    /**
     * name|menu or button
     */
    private String name;

    /**
     * page|router
     */
    private String page;

    /**
     * request|interface
     */
    private String request;

    /**
     * parent id
     */
    private String parent;

    private List<ResourceDto> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public List<ResourceDto> getChildren() {
        return children;
    }

    public void setChildren(List<ResourceDto> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "ResourceDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", page='" + page + '\'' +
                ", request='" + request + '\'' +
                ", parent='" + parent + '\'' +
                ", children=" + children +
                '}';
    }

}
