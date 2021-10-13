package com.course.file.controller.admin;

import com.course.server.dto.FileDto;
import com.course.server.dto.ResponseDto;
import com.course.server.enums.FileUseEnum;
import com.course.server.service.FileService;
import com.course.server.util.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
    public ResponseDto<Object> update(@RequestParam MultipartFile file, String use) throws IOException {
        LOG.info("Upload file start");

        //  save file into local drive
        FileUseEnum useEnum = FileUseEnum.getByCode(use);
        String key = UuidUtil.getShortUuid();
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();

        // create dirs if not exist
        String dir = useEnum.name().toLowerCase();
        File fullDir = new File(FILE_PATH + dir);
        if (!fullDir.exists()) {
            fullDir.mkdir();
        }

        String path = dir + File.separator + key + "." + suffix;
        String fullPath = FILE_PATH + path;

        File dest = new File(fullPath);
        file.transferTo(dest);
        LOG.info(dest.getAbsolutePath());

        FileDto fileDto = new FileDto();
        fileDto.setPath(path);
        fileDto.setName(fileName);
        fileDto.setSize(Math.toIntExact(file.getSize()));
        fileDto.setSuffix(suffix);
        fileDto.setUse(use);
        fileService.save(fileDto);

        LOG.info("Upload file end");
        ResponseDto<Object> responseDto = new ResponseDto<>();
        fileDto.setPath(FILE_DOMAIN + path);
        responseDto.setContent(fileDto);
        return responseDto;

    }

    @GetMapping("/merge")
    public ResponseDto merge() throws Exception {
        File newFile = new File(FILE_PATH + "course/111.mp4");
        FileOutputStream outputStream = new FileOutputStream(newFile, true);
        FileInputStream fileInputStream = null; // partial file
        byte[] byt = new byte[10 * 1024 * 1024];
        int len;

        try {
            // read first partial file
            fileInputStream = new FileInputStream(new File(FILE_PATH +  "/course/Bc0SXtFn.blob"));
            while ((len = fileInputStream.read(byt)) != -1) {
                outputStream.write(byt, 0, len);
            }

            // read second partial file
            fileInputStream = new FileInputStream(new File(FILE_PATH +  "/course/roQbPm2x.blob"));
            while ((len = fileInputStream.read(byt)) != -1) {
                outputStream.write(byt, 0, len);
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

        ResponseDto responseDto = new ResponseDto();
        return responseDto;
    }
}
