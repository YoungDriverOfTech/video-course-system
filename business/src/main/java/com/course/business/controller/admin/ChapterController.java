package com.course.business.controller.admin;

import com.course.server.dto.ChapterDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.ChapterService;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/chapter")
public class ChapterController {

    private static final Logger LOG = LoggerFactory.getLogger(ChapterController.class);

    @Resource
    private ChapterService chapterService;

    @PostMapping("/list")
    public ResponseDto<PageDto<ChapterDto>> list(@RequestBody PageDto<ChapterDto> pageDto) {
        LOG.info("pageDto: {}", pageDto);
        ResponseDto<PageDto<ChapterDto>> responseDto = new ResponseDto<>();
        chapterService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    @PostMapping("/save")
    public ResponseDto<ChapterDto> save(@RequestBody ChapterDto chapterDto) {
        LOG.info("chapterDto: {}", chapterDto);

        // check operation
        ValidatorUtil.require(chapterDto.getName(), "name");
        ValidatorUtil.require(chapterDto.getCourseId(), "CourseId");
        ValidatorUtil.length(chapterDto.getCourseId(), "CourseId", 1, 8);

        // save operation
        ResponseDto<ChapterDto> responseDto = new ResponseDto<>();
        chapterService.save(chapterDto);
        responseDto.setContent(chapterDto);
        return responseDto;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseDto<ChapterDto> delete(@PathVariable String id) {
        LOG.info("id: {}", id);
        ResponseDto<ChapterDto> responseDto = new ResponseDto<>();
        chapterService.delete(id);
        return responseDto;
    }
}
