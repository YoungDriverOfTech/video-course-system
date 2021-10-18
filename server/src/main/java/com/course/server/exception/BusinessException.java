package com.course.server.exception;

public class BusinessException extends RuntimeException{

    private BusinessExceptionCode code;

    public BusinessException(BusinessExceptionCode code) {
        super(code.getDesc());
        this.code = code;
    }

    public BusinessExceptionCode getCode() {
        return code;
    }

    public void setCode(BusinessExceptionCode code) {
        this.code = code;
    }

    /**
     * do not write error message into stack for improving server performance, this exception is not system but
     * a business exception
     */
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
