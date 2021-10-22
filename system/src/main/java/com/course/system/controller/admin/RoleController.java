package com.course.business.controller.admin;

import com.course.server.dto.RoleDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.RoleService;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/role")
public class RoleController {

    private static final Logger LOG = LoggerFactory.getLogger(RoleController.class);
    public static final String BUSINESS_NAME = "Role";

    @Resource
    private RoleService roleService;

    @PostMapping("/list")
    public ResponseDto<PageDto<RoleDto>> list(@RequestBody PageDto<RoleDto> pageDto) {
        LOG.info("pageDto: {}", pageDto);
        ResponseDto<PageDto<RoleDto>> responseDto = new ResponseDto<>();
        roleService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    @PostMapping("/save")
    public ResponseDto<RoleDto> save(@RequestBody RoleDto roleDto) {
        LOG.info("roleDto: {}", roleDto);

        // check operation
        ValidatorUtil.require(roleDto.getName(), "character");
        ValidatorUtil.length(roleDto.getName(), "character", 1, 50);
        ValidatorUtil.require(roleDto.getDesc(), "description");
        ValidatorUtil.length(roleDto.getDesc(), "description", 1, 100);

        // save operation
        ResponseDto<RoleDto> responseDto = new ResponseDto<>();
        roleService.save(roleDto);
        responseDto.setContent(roleDto);
        return responseDto;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseDto<RoleDto> delete(@PathVariable String id) {
        LOG.info("id: {}", id);
        ResponseDto<RoleDto> responseDto = new ResponseDto<>();
        roleService.delete(id);
        return responseDto;
    }
}
