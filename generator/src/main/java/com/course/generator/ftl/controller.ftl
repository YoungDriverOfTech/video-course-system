package com.course.business.controller.admin;

import com.course.server.dto.${Domain}Dto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.${Domain}Service;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/${domain}")
public class ${Domain}Controller {

    private static final Logger LOG = LoggerFactory.getLogger(${Domain}Controller.class);
    public static final String BUSINESS_NAME = "${Domain}";

    @Resource
    private ${Domain}Service ${domain}Service;

    @PostMapping("/list")
    public ResponseDto<PageDto<${Domain}Dto>> list(@RequestBody PageDto<${Domain}Dto> pageDto) {
        LOG.info("pageDto: {}", pageDto);
        ResponseDto<PageDto<${Domain}Dto>> responseDto = new ResponseDto<>();
        ${domain}Service.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    @PostMapping("/save")
    public ResponseDto<${Domain}Dto> save(@RequestBody ${Domain}Dto ${domain}Dto) {
        LOG.info("${domain}Dto: {}", ${domain}Dto);

        // check operation
        ValidatorUtil.require(${domain}Dto.getName(), "name");
        ValidatorUtil.require(${domain}Dto.getCourseId(), "CourseId");
        ValidatorUtil.length(${domain}Dto.getCourseId(), "CourseId", 1, 8);

        // save operation
        ResponseDto<${Domain}Dto> responseDto = new ResponseDto<>();
        ${domain}Service.save(${domain}Dto);
        responseDto.setContent(${domain}Dto);
        return responseDto;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseDto<${Domain}Dto> delete(@PathVariable String id) {
        LOG.info("id: {}", id);
        ResponseDto<${Domain}Dto> responseDto = new ResponseDto<>();
        ${domain}Service.delete(id);
        return responseDto;
    }
}
