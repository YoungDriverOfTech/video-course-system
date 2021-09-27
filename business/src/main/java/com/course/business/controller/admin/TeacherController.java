package com.course.business.controller.admin;

import com.course.server.dto.TeacherDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.TeacherService;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin/teacher")
public class TeacherController {

    private static final Logger LOG = LoggerFactory.getLogger(TeacherController.class);
    public static final String BUSINESS_NAME = "Teacher";

    @Resource
    private TeacherService teacherService;

    @PostMapping("/all")
    public ResponseDto<List<TeacherDto>> all() {
        ResponseDto<List<TeacherDto>> responseDto = new ResponseDto<>();
        List<TeacherDto> teacherDtoList = teacherService.all();
        responseDto.setContent(teacherDtoList);
        return responseDto;
    }

    @PostMapping("/list")
    public ResponseDto<PageDto<TeacherDto>> list(@RequestBody PageDto<TeacherDto> pageDto) {
        LOG.info("pageDto: {}", pageDto);
        ResponseDto<PageDto<TeacherDto>> responseDto = new ResponseDto<>();
        teacherService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    @PostMapping("/save")
    public ResponseDto<TeacherDto> save(@RequestBody TeacherDto teacherDto) {
        LOG.info("teacherDto: {}", teacherDto);

        // check operation
        ValidatorUtil.require(teacherDto.getName(), "name");
        ValidatorUtil.length(teacherDto.getName(), "name", 1, 50);
        ValidatorUtil.length(teacherDto.getNickname(), "nickname", 1, 50);
        ValidatorUtil.length(teacherDto.getImage(), "image", 1, 100);
        ValidatorUtil.length(teacherDto.getPosition(), "position", 1, 50);
        ValidatorUtil.length(teacherDto.getMotto(), "motto", 1, 50);
        ValidatorUtil.length(teacherDto.getIntro(), "intro", 1, 500);

        // save operation
        ResponseDto<TeacherDto> responseDto = new ResponseDto<>();
        teacherService.save(teacherDto);
        responseDto.setContent(teacherDto);
        return responseDto;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseDto<TeacherDto> delete(@PathVariable String id) {
        LOG.info("id: {}", id);
        ResponseDto<TeacherDto> responseDto = new ResponseDto<>();
        teacherService.delete(id);
        return responseDto;
    }
}
