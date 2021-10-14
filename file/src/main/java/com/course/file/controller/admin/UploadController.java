package com.course.file.controller.admin;

import com.course.server.dto.FileDto;
import com.course.server.dto.ResponseDto;
import com.course.server.enums.FileUseEnum;
import com.course.server.service.FileService;
import com.course.server.util.Base64ToMultipartFile;
import com.course.server.util.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@RequestMapping("/admin")
@RestController
public class UploadController {

    private static final Logger LOG = LoggerFactory.getLogger(UploadController.class);

    public static final String BUSINESS_NAME = "UploadFile";

    @Value("${file.domain}")
    private String FILE_DOMAIN;

    @Value("${file.path}")
    private String FILE_PATH;

    @Resource
    private FileService fileService;

    @RequestMapping("/upload")
    public ResponseDto upload(@RequestBody FileDto fileDto) throws Exception {
        LOG.info("Upload file start");
        String use = fileDto.getUse();
        String key = fileDto.getKey();
        String suffix = fileDto.getSuffix();
        String shardBase64 = fileDto.getShard();
        MultipartFile shard = Base64ToMultipartFile.base64ToMultipart(shardBase64);

        //  save file into local drive
        FileUseEnum useEnum = FileUseEnum.getByCode(use);

        // create dirs if not exist
        String dir = useEnum.name().toLowerCase();
        File fullDir = new File(FILE_PATH + dir);
        if (!fullDir.exists()) {
            fullDir.mkdir();
        }

        String path = new StringBuilder(dir)
                .append(File.separator)
                .append(key)
                .append(".")
                .append(suffix)
                .toString();
        String localPath = new StringBuilder(path)
                .append(".")
                .append(fileDto.getShardIndex())
                .toString();
        String fullPath = FILE_PATH + localPath;

        File dest = new File(fullPath);
        shard.transferTo(dest);
        LOG.info(dest.getAbsolutePath());

        fileDto.setPath(path);
        fileService.save(fileDto);

        LOG.info("Upload file end");
        ResponseDto<Object> responseDto = new ResponseDto<>();
        fileDto.setPath(FILE_DOMAIN + path);
        responseDto.setContent(fileDto);

        if (fileDto.getShardIndex().equals(fileDto.getShardTotal())) {
            this.merge(fileDto);
        }
        return responseDto;
    }

    public void merge(FileDto fileDto) throws Exception {
        LOG.info("merge start");
        String path = fileDto.getPath();
        path = path.replace(FILE_DOMAIN, "");
        Integer shardTotal = fileDto.getShardTotal();
        File newFile = new File(FILE_PATH + path);
        FileOutputStream outputStream = new FileOutputStream(newFile, true);
        FileInputStream fileInputStream = null; // partial file
        byte[] byt = new byte[10 * 1024 * 1024];
        int len;

        try {
            for (int i = 0; i < shardTotal; i++) {
                // read first section
                fileInputStream = new FileInputStream(new File(FILE_PATH + path + "." + (i + 1)));
                while ((len = fileInputStream.read(byt)) != -1) {
                    outputStream.write(byt, 0, len);
                }
            }
        } catch (IOException e) {
            LOG.error(e.toString());
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                outputStream.close();
                LOG.info("close IO stream");
            } catch (Exception e) {
                LOG.error(e.toString());
            }
        }
        LOG.info("merge ended");

        // let gc release the useless refs
        System.gc();
        Thread.sleep(100);

        // delete merged sections
        LOG.info("delete section start");
        for (int i = 0; i < shardTotal; i++) {
            String filePath = FILE_PATH + path + "." + (i + 1);
            File file = new File(filePath);
            boolean result = file.delete();
            LOG.info("delete{}，{}", filePath, result ? "success" : "failure");
        }
        LOG.info("delete end");
    }

    @GetMapping("/check/{key}")
    public ResponseDto check(@PathVariable String key) {
        LOG.info("check section start {}", key);
        ResponseDto<Object> responseDto = new ResponseDto<>();
        FileDto fileDto = fileService.findByKey(key);
        if (fileDto != null) {
            fileDto.setPath(FILE_DOMAIN +fileDto.getPath());
        }
        responseDto.setContent(fileDto);
        return responseDto;
    }
}
