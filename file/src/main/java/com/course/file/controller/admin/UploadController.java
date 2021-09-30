package com.course.file.controller.admin;

import com.course.server.dto.ResponseDto;
import com.course.server.util.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RequestMapping("/admin")
@RestController
public class UploadController {

    private static final Logger LOG = LoggerFactory.getLogger(UploadController.class);

    public static final String BUSINESS_NAME = "UploadFile";

    @RequestMapping("/upload")
    public ResponseDto<Object> update(@RequestParam MultipartFile file) throws IOException {
        LOG.info("Upload file start {}", file);

        //  save file into local drive
        String fileName = file.getOriginalFilename();
        String key = UuidUtil.getShortUuid();
        String fullPath = "D:/file/teacher/" + key + "-" + fileName;

        File dest = new File(fullPath);
        file.transferTo(dest);
        LOG.info(dest.getAbsolutePath());
        LOG.info("Upload file end");
        ResponseDto<Object> responseDto = new ResponseDto<>();
        return responseDto;

    }
}
