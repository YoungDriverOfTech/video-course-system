package com.course.system.controller;

import com.course.server.dto.ResponseDto;
import com.course.server.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ControllerExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public ResponseDto<Object> businessExceptionHandler(BusinessException e) {
        ResponseDto<Object> responseDto = new ResponseDto<>();
        responseDto.setSuccess(false);
        LOG.error("Business error：{}", e.getCode().getDesc());
        responseDto.setMessage(e.getCode().getDesc());
        return responseDto;
    }
}
