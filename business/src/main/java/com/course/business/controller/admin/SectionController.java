package com.course.business.controller.admin;

import com.course.server.dto.SectionDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.SectionService;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/section")
public class SectionController {

    private static final Logger LOG = LoggerFactory.getLogger(SectionController.class);
    public static final String BUSINESS_NAME = "Section";

    @Resource
    private SectionService sectionService;

    @PostMapping("/list")
    public ResponseDto<PageDto<SectionDto>> list(@RequestBody PageDto<SectionDto> pageDto) {
        LOG.info("pageDto: {}", pageDto);
        ResponseDto<PageDto<SectionDto>> responseDto = new ResponseDto<>();
        sectionService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    @PostMapping("/save")
    public ResponseDto<SectionDto> save(@RequestBody SectionDto sectionDto) {
        LOG.info("sectionDto: {}", sectionDto);

        // check operation
        ValidatorUtil.require(sectionDto.getId(), "ID");
        ValidatorUtil.require(sectionDto.getTitle(), "title");
        ValidatorUtil.length(sectionDto.getTitle(), "title", 1, 50);
        ValidatorUtil.length(sectionDto.getVideo(), "video", 1, 200);

        // save operation
        ResponseDto<SectionDto> responseDto = new ResponseDto<>();
        sectionService.save(sectionDto);
        responseDto.setContent(sectionDto);
        return responseDto;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseDto<SectionDto> delete(@PathVariable String id) {
        LOG.info("id: {}", id);
        ResponseDto<SectionDto> responseDto = new ResponseDto<>();
        sectionService.delete(id);
        return responseDto;
    }
}
