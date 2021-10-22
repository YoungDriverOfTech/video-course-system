package com.course.business.controller.admin;

import com.course.server.dto.RoleResourceDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.RoleResourceService;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/roleResource")
public class RoleResourceController {

    private static final Logger LOG = LoggerFactory.getLogger(RoleResourceController.class);
    public static final String BUSINESS_NAME = "RoleResource";

    @Resource
    private RoleResourceService roleResourceService;

    @PostMapping("/list")
    public ResponseDto<PageDto<RoleResourceDto>> list(@RequestBody PageDto<RoleResourceDto> pageDto) {
        LOG.info("pageDto: {}", pageDto);
        ResponseDto<PageDto<RoleResourceDto>> responseDto = new ResponseDto<>();
        roleResourceService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    @PostMapping("/save")
    public ResponseDto<RoleResourceDto> save(@RequestBody RoleResourceDto roleResourceDto) {
        LOG.info("roleResourceDto: {}", roleResourceDto);

        // check operation
        ValidatorUtil.require(roleResourceDto.getRoleId(), "role");
        ValidatorUtil.require(roleResourceDto.getResourceId(), "resource");

        // save operation
        ResponseDto<RoleResourceDto> responseDto = new ResponseDto<>();
        roleResourceService.save(roleResourceDto);
        responseDto.setContent(roleResourceDto);
        return responseDto;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseDto<RoleResourceDto> delete(@PathVariable String id) {
        LOG.info("id: {}", id);
        ResponseDto<RoleResourceDto> responseDto = new ResponseDto<>();
        roleResourceService.delete(id);
        return responseDto;
    }
}
