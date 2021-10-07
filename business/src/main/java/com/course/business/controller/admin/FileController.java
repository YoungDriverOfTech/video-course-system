package com.course.business.controller.admin;

import com.course.server.dto.FileDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.FileService;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/file")
public class FileController {

    private static final Logger LOG = LoggerFactory.getLogger(FileController.class);
    public static final String BUSINESS_NAME = "File";

    @Resource
    private FileService fileService;

    @PostMapping("/list")
    public ResponseDto<PageDto<FileDto>> list(@RequestBody PageDto<FileDto> pageDto) {
        LOG.info("pageDto: {}", pageDto);
        ResponseDto<PageDto<FileDto>> responseDto = new ResponseDto<>();
        fileService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    @PostMapping("/save")
    public ResponseDto<FileDto> save(@RequestBody FileDto fileDto) {
        LOG.info("fileDto: {}", fileDto);

        // check operation
        ValidatorUtil.require(fileDto.getPath(), "relative path");
        ValidatorUtil.length(fileDto.getPath(), "relative path", 1, 100);
        ValidatorUtil.length(fileDto.getName(), "file name", 1, 100);
        ValidatorUtil.length(fileDto.getSuffix(), "suffix", 1, 10);

        // save operation
        ResponseDto<FileDto> responseDto = new ResponseDto<>();
        fileService.save(fileDto);
        responseDto.setContent(fileDto);
        return responseDto;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseDto<FileDto> delete(@PathVariable String id) {
        LOG.info("id: {}", id);
        ResponseDto<FileDto> responseDto = new ResponseDto<>();
        fileService.delete(id);
        return responseDto;
    }
}
