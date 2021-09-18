package com.course.business.controller.admin;

import com.course.server.dto.CategoryDto;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResponseDto;
import com.course.server.service.CategoryService;
import com.course.server.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin/category")
public class CategoryController {

    private static final Logger LOG = LoggerFactory.getLogger(CategoryController.class);
    public static final String BUSINESS_NAME = "Category";

    @Resource
    private CategoryService categoryService;

    @PostMapping("/all")
    public ResponseDto<List<CategoryDto>> all() {
        ResponseDto<List<CategoryDto> > responseDto = new ResponseDto<>();
        responseDto.setContent(categoryService.all());
        return responseDto;
    }

    @PostMapping("/list")
    public ResponseDto<PageDto<CategoryDto>> list(@RequestBody PageDto<CategoryDto> pageDto) {
        LOG.info("pageDto: {}", pageDto);
        ResponseDto<PageDto<CategoryDto>> responseDto = new ResponseDto<>();
        categoryService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    @PostMapping("/save")
    public ResponseDto<CategoryDto> save(@RequestBody CategoryDto categoryDto) {
        LOG.info("categoryDto: {}", categoryDto);

        // check operation
        ValidatorUtil.require(categoryDto.getParent(), "parent id");
        ValidatorUtil.require(categoryDto.getName(), "name");
        ValidatorUtil.length(categoryDto.getName(), "name", 1, 50);

        // save operation
        ResponseDto<CategoryDto> responseDto = new ResponseDto<>();
        categoryService.save(categoryDto);
        responseDto.setContent(categoryDto);
        return responseDto;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseDto<CategoryDto> delete(@PathVariable String id) {
        LOG.info("id: {}", id);
        ResponseDto<CategoryDto> responseDto = new ResponseDto<>();
        categoryService.delete(id);
        return responseDto;
    }
}
