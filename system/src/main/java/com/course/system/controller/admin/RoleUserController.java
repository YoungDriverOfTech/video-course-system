package com.course.system.controller.admin;

import com.course.server.dto.RoleUserDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.RoleUserService;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/roleUser")
public class RoleUserController {

    private static final Logger LOG = LoggerFactory.getLogger(RoleUserController.class);
    public static final String BUSINESS_NAME = "RoleUser";

    @Resource
    private RoleUserService roleUserService;

    @PostMapping("/list")
    public ResponseDto<PageDto<RoleUserDto>> list(@RequestBody PageDto<RoleUserDto> pageDto) {
        LOG.info("pageDto: {}", pageDto);
        ResponseDto<PageDto<RoleUserDto>> responseDto = new ResponseDto<>();
        roleUserService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    @PostMapping("/save")
    public ResponseDto<RoleUserDto> save(@RequestBody RoleUserDto roleUserDto) {
        LOG.info("roleUserDto: {}", roleUserDto);

        // check operation
        ValidatorUtil.require(roleUserDto.getRoleId(), "role");
        ValidatorUtil.require(roleUserDto.getUserId(), "user");

        // save operation
        ResponseDto<RoleUserDto> responseDto = new ResponseDto<>();
        roleUserService.save(roleUserDto);
        responseDto.setContent(roleUserDto);
        return responseDto;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseDto<RoleUserDto> delete(@PathVariable String id) {
        LOG.info("id: {}", id);
        ResponseDto<RoleUserDto> responseDto = new ResponseDto<>();
        roleUserService.delete(id);
        return responseDto;
    }
}
