package com.course.system.controller.admin;

import com.course.server.dto.*;
import com.course.server.service.UserService;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/admin/user")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
    public static final String BUSINESS_NAME = "User";

    @Resource
    private UserService userService;

    @PostMapping("/list")
    public ResponseDto<PageDto<UserDto>> list(@RequestBody PageDto<UserDto> pageDto) {
        LOG.info("pageDto: {}", pageDto);
        ResponseDto<PageDto<UserDto>> responseDto = new ResponseDto<>();
        userService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    @PostMapping("/save")
    public ResponseDto<UserDto> save(@RequestBody UserDto userDto) {
        LOG.info("userDto: {}", userDto);

        userDto.setPassword(DigestUtils.md5DigestAsHex(userDto.getPassword().getBytes()));
        // check operation
        ValidatorUtil.require(userDto.getLoginName(), "login name");
        ValidatorUtil.length(userDto.getLoginName(), "login name", 1, 50);
        ValidatorUtil.length(userDto.getName(), "nick name", 1, 50);
        ValidatorUtil.require(userDto.getPassword(), "password");

        // save operation
        ResponseDto<UserDto> responseDto = new ResponseDto<>();
        userService.save(userDto);
        responseDto.setContent(userDto);
        return responseDto;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseDto<UserDto> delete(@PathVariable String id) {
        LOG.info("id: {}", id);
        ResponseDto<UserDto> responseDto = new ResponseDto<>();
        userService.delete(id);
        return responseDto;
    }

    @PostMapping("/save-password")
    public ResponseDto<Object> savePassword(@RequestBody UserDto userDto) {
        userDto.setPassword(DigestUtils.md5DigestAsHex(userDto.getPassword().getBytes()));
        ResponseDto<Object> responseDto = new ResponseDto<>();
        userService.savePassword(userDto);
        responseDto.setContent(userDto);
        return responseDto;
    }

    @PostMapping("/login")
    public ResponseDto<LoginUserDto> login(@RequestBody UserDto userDto, HttpServletRequest request) {
        userDto.setPassword(DigestUtils.md5DigestAsHex(userDto.getPassword().getBytes()));
        ResponseDto<LoginUserDto> responseDto = new ResponseDto<>();
        LoginUserDto loginUserDto = userService.login(userDto);
        request.getSession().setAttribute(Constants.LOGIN_USER, loginUserDto);
        responseDto.setContent(loginUserDto);
        return responseDto;
    }

    @GetMapping("/logout")
    public ResponseDto<Object> logout(HttpServletRequest request) {
        ResponseDto<Object> responseDto = new ResponseDto<>();
        request.getSession().removeAttribute(Constants.LOGIN_USER);
        return responseDto;
    }
}
