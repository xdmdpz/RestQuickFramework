package com.yf.modules.projectInfo.controller;

import com.yf.modules.projectInfo.service.ProjectInfoService;
import com.yf.modules.projectInfo.domain.ProjectInfo;
import com.yf.core.base.RestResponse;


import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "projectInfo")
@RequestMapping("api")
public class ProjectInfoController {

    @Autowired
    private ProjectInfoService projectInfoService;

    @ApiOperation(value = "分页查询所有", notes = "")
    @ApiResponses({
            @ApiResponse(code = 200, message = "查询成功"),
            @ApiResponse(code = 400, message = "参数验证失败")
    })
    @GetMapping("projectInfo")
    public RestResponse<Page<ProjectInfo>> findPage(@RequestParam(defaultValue = "0") Integer page,
                                                    @RequestParam(defaultValue = "15") Integer size,
                                                    @RequestParam(defaultValue = "") String order) {
        return RestResponse.success(
                projectInfoService.findPage(
                        PageRequest.of(page, size,
                                Sort.by(Sort.Direction.DESC, order.equals("") ? "createTime" : order)
                        )
                )
        );
    }

    @ApiOperation(value = "新增", notes = "")
    @ApiResponses({
            @ApiResponse(code = 200, message = "添加成功"),
            @ApiResponse(code = 400, message = "参数验证失败")
    })
    @PostMapping("projectInfo")
    public RestResponse<?> create(@RequestBody ProjectInfo projectInfo) {
        projectInfoService.create(projectInfo);
        return RestResponse.success();
    }

    @ApiOperation(value = "修改", notes = "")
    @ApiResponses({
            @ApiResponse(code = 200, message = "修改成功"),
            @ApiResponse(code = 400, message = "参数验证失败")
    })
    @PatchMapping("projectInfo")
    public RestResponse<?> update(@RequestBody ProjectInfo projectInfo) {
        projectInfoService.update(projectInfo);
        return RestResponse.success();
    }

    @ApiOperation(value = "删除", notes = "")
    @ApiResponses({
            @ApiResponse(code = 200, message = "删除成功"),
            @ApiResponse(code = 400, message = "参数验证失败")
    })
    @DeleteMapping("projectInfo/{id}")
    public RestResponse<?> delete(@RequestParam Integer id) {
        projectInfoService.delete(projectInfoService.findOne(id));
        return RestResponse.success();
    }

    @ApiOperation(value = "通过主键查找", notes = "")
    @ApiResponses({
            @ApiResponse(code = 200, message = "查询成功"),
            @ApiResponse(code = 400, message = "参数错误")
    })
    @GetMapping("projectInfo/{id}")
    public RestResponse<ProjectInfo> findOne(@PathVariable Integer id) {
        return RestResponse.success(projectInfoService.findOne(id));
    }
}
