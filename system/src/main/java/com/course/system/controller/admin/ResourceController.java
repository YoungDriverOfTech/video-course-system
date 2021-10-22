package com.course.system.controller.admin;

import com.course.server.dto.ResourceDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.ResourceService;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/resource")
public class ResourceController {

    private static final Logger LOG = LoggerFactory.getLogger(ResourceController.class);
    public static final String BUSINESS_NAME = "Resource";

    @Resource
    private ResourceService resourceService;

    @PostMapping("/list")
    public ResponseDto<PageDto<ResourceDto>> list(@RequestBody PageDto<ResourceDto> pageDto) {
        LOG.info("pageDto: {}", pageDto);
        ResponseDto<PageDto<ResourceDto>> responseDto = new ResponseDto<>();
        resourceService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    @PostMapping("/save")
    public ResponseDto<ResourceDto> save(@RequestBody ResourceDto resourceDto) {
        LOG.info("resourceDto: {}", resourceDto);

        // check operation
        ValidatorUtil.require(resourceDto.getName(), "name");
        ValidatorUtil.length(resourceDto.getName(), "name", 1, 100);
        ValidatorUtil.length(resourceDto.getPage(), "page", 1, 50);
        ValidatorUtil.length(resourceDto.getRequest(), "request", 1, 200);

        // save operation
        ResponseDto<ResourceDto> responseDto = new ResponseDto<>();
        resourceService.save(resourceDto);
        responseDto.setContent(resourceDto);
        return responseDto;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseDto<ResourceDto> delete(@PathVariable String id) {
        LOG.info("id: {}", id);
        ResponseDto<ResourceDto> responseDto = new ResponseDto<>();
        resourceService.delete(id);
        return responseDto;
    }
}
