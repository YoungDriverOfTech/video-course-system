package com.course.server.dto;

public class ResponseDto<T> {

    /**
     * is business or transaction success or not
     */
    private boolean success = true;

    /**
     * return code
     */
    private String code;

    /**
     * return message
     */
    private String message;

    /**
     * return content
     */
    private T content;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}
