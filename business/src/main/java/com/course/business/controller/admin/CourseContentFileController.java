package com.course.business.controller.admin;

import com.course.server.dto.CourseContentFileDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.CourseContentFileService;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin/course-content-file")
public class CourseContentFileController {

    private static final Logger LOG = LoggerFactory.getLogger(CourseContentFileController.class);
    public static final String BUSINESS_NAME = "CourseContentFile";

    @Resource
    private CourseContentFileService courseContentFileService;

    @PostMapping("/list/{courseId}")
    public ResponseDto<List<CourseContentFileDto>> list(@PathVariable String courseId) {
        ResponseDto<List<CourseContentFileDto>> responseDto = new ResponseDto<>();
        List<CourseContentFileDto> fileDtoList = courseContentFileService.list(courseId);
        responseDto.setContent(fileDtoList);
        return responseDto;
    }

    @PostMapping("/save")
    public ResponseDto<CourseContentFileDto> save(@RequestBody CourseContentFileDto courseContentFileDto) {
        LOG.info("courseContentFileDto: {}", courseContentFileDto);

        // check operation
        ValidatorUtil.require(courseContentFileDto.getCourseId(), "course id");
        ValidatorUtil.length(courseContentFileDto.getUrl(), "url address", 1, 100);
        ValidatorUtil.length(courseContentFileDto.getName(), "file name", 1, 100);

        // save operation
        ResponseDto<CourseContentFileDto> responseDto = new ResponseDto<>();
        courseContentFileService.save(courseContentFileDto);
        responseDto.setContent(courseContentFileDto);
        return responseDto;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseDto<CourseContentFileDto> delete(@PathVariable String id) {
        LOG.info("id: {}", id);
        ResponseDto<CourseContentFileDto> responseDto = new ResponseDto<>();
        courseContentFileService.delete(id);
        return responseDto;
    }
}
