package com.course.business.controller.admin;

import com.course.server.dto.CourseDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.CourseService;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/course")
public class CourseController {

    private static final Logger LOG = LoggerFactory.getLogger(CourseController.class);
    public static final String BUSINESS_NAME = "Course";

    @Resource
    private CourseService courseService;

    @PostMapping("/list")
    public ResponseDto<PageDto<CourseDto>> list(@RequestBody PageDto<CourseDto> pageDto) {
        LOG.info("pageDto: {}", pageDto);
        ResponseDto<PageDto<CourseDto>> responseDto = new ResponseDto<>();
        courseService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    @PostMapping("/save")
    public ResponseDto<CourseDto> save(@RequestBody CourseDto courseDto) {
        LOG.info("courseDto: {}", courseDto);

        // check operation
        ValidatorUtil.require(courseDto.getName(), "name");
        ValidatorUtil.length(courseDto.getName(), "name", 1, 50);
        ValidatorUtil.length(courseDto.getSummary(), "summary", 1, 2000);
        ValidatorUtil.length(courseDto.getImage(), "profile", 1, 100);

        // save operation
        ResponseDto<CourseDto> responseDto = new ResponseDto<>();
        courseService.save(courseDto);
        responseDto.setContent(courseDto);
        return responseDto;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseDto<CourseDto> delete(@PathVariable String id) {
        LOG.info("id: {}", id);
        ResponseDto<CourseDto> responseDto = new ResponseDto<>();
        courseService.delete(id);
        return responseDto;
    }
}
