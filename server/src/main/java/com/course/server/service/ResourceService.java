package com.course.server.service;

import com.alibaba.fastjson.JSON;
import com.course.server.domain.Resource;
import com.course.server.domain.ResourceExample;
import com.course.server.dto.PageDto;
import com.course.server.dto.ResourceDto;
import com.course.server.mapper.ResourceMapper;
import com.course.server.util.CopyUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResourceService {

    private static final Logger LOG = LoggerFactory.getLogger(ResourceService.class);

    @javax.annotation.Resource
    private ResourceMapper resourceMapper;

    public void list(PageDto<ResourceDto> pageDto) {
        // paging and select records
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        ResourceExample resourceExample = new ResourceExample();
        List<Resource> resourceList = resourceMapper.selectByExample(resourceExample);

        // set properties
        PageInfo<Resource> pageInfo = new PageInfo<>(resourceList);
        pageDto.setTotal(pageInfo.getTotal());

        // transfer resourceList into resourceDtoList
        List<ResourceDto> resourceDtoList = CopyUtil.copyList(resourceList, ResourceDto.class);
        pageDto.setList(resourceDtoList);
    }

    public void save(ResourceDto resourceDto) {
        Resource resource = CopyUtil.copy(resourceDto, Resource.class);
        if (StringUtils.isEmpty(resource.getId())) {
            this.insert(resource);
        } else {
            this.update(resource);
        }
    }

    private void insert(Resource resource) {
        resourceMapper.insert(resource);
    }

    private void update(Resource resource) {
        resourceMapper.updateByPrimaryKey(resource);
    }

    public void delete(String id) {
        resourceMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void saveJson(String json) {
        List<ResourceDto> jsonList = JSON.parseArray(json, ResourceDto.class);
        List<ResourceDto> list = new ArrayList<>();
        if (!CollectionUtils.isEmpty(jsonList)) {
            for (ResourceDto d: jsonList) {
                d.setParent("");
                add(list, d);
            }
        }
        LOG.info("total: {}", list.size());

        resourceMapper.deleteByExample(null);
        for (ResourceDto resourceDto : list) {
            this.insert(CopyUtil.copy(resourceDto, Resource.class));
        }
    }

    public void add(List<ResourceDto> list, ResourceDto dto) {
        list.add(dto);
        if (!CollectionUtils.isEmpty(dto.getChildren())) {
            for (ResourceDto d: dto.getChildren()) {
                d.setParent(dto.getId());
                add(list, d);
            }
        }
    }

    public List<ResourceDto> loadTree() {
        ResourceExample example = new ResourceExample();
        example.setOrderByClause("id asc");
        List<Resource> resourceList = resourceMapper.selectByExample(example);
        List<ResourceDto> resourceDtoList = CopyUtil.copyList(resourceList, ResourceDto.class);
        for (int i = resourceDtoList.size() - 1; i >= 0; i--) {
            // get current node
            ResourceDto child = resourceDtoList.get(i);

            // no need to handle next one if this one have no parent node
            if (StringUtils.isEmpty(child.getParent())) {
                continue;
            }
            // search parent node
            for (int j = i - 1; j >= 0; j--) {
                ResourceDto parent = resourceDtoList.get(j);
                if (child.getParent().equals(parent.getId())) {
                    if (CollectionUtils.isEmpty(parent.getChildren())) {
                        parent.setChildren(new ArrayList<>());
                    }
                    // add node to first index
                    parent.getChildren().add(0, child);

                    // find parent node then remove child node
                    resourceDtoList.remove(child);
                }
            }
        }
        return resourceDtoList;
    }
}
