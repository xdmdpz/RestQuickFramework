package com.yf.modules.projectInfo.controller;

import com.yf.modules.projectInfo.service.ProjectInfoService;
import com.yf.modules.projectInfo.domain.ProjectInfo;
import com.yf.common.base.RestResponse;

import io.swagger.annotations.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;


@RestController
@Api(tags = "projectInfo")
@RequestMapping("api")
public class ProjectInfoController {

    private static Logger logger = Logger.getLogger(ProjectInfoController.class);

    @Autowired
    private ProjectInfoService projectInfoService;

    /**
     * 获取列表
     *
     * @param page     页码
     * @param size     分页大小
     * @param response
     * @return
     */
    @ApiOperation(value = "分页查询所有", notes = "")
    @ApiResponses({
            @ApiResponse(code = 200, message = "查询成功", response = ProjectInfo.class),
            @ApiResponse(code = 404, message = "查询失败", response = RestResponse.class)
    })
    @GetMapping("projectInfos")
    public RestResponse findPage(@ApiParam(value = "页码", defaultValue = "1")
                                 @RequestParam(value = "page", defaultValue = "1") Integer page,
                                 @ApiParam(value = "页大小", defaultValue = "15")
                                 @RequestParam(value = "size", defaultValue = "15") Integer size,
                                 RestResponse response) {
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        Pageable pageable = PageRequest.of(page, size, sort);
        return response.success(projectInfoService.findPage(pageable));
    }

    /**
     * 新增
     *
     * @param projectInfo 新增实体，前端数据需要 JSON.stringify(data)
     * @param response
     * @return
     */
    @ApiOperation(value = "新增", notes = "")
    @ApiResponses({
            @ApiResponse(code = 201, message = "添加成功"),
            @ApiResponse(code = 400, message = "参数验证失败", response = RestResponse.class)
    })
    @PostMapping("projectInfo")
    public RestResponse create(@ApiParam(value = "实体", required = true)
                               @RequestBody ProjectInfo projectInfo,
                               RestResponse response) {
        response.success(projectInfoService.create(projectInfo));
        return response.success();
    }

    /**
     * 更新
     *
     * @param projectInfo 更新实体，前端数据需要 JSON.stringify(data)
     * @param response
     * @return
     */
    @ApiOperation(value = "修改", notes = "")
    @ApiResponses({
            @ApiResponse(code = 201, message = "修改成功"),
            @ApiResponse(code = 400, message = "参数验证失败", response = RestResponse.class)
    })
    @PatchMapping("projectInfo")
    public RestResponse update(@ApiParam(value = "实体", required = true)
                               @RequestBody ProjectInfo projectInfo,
                               RestResponse response) {
        return response.success(projectInfoService.update(projectInfoService.findOne(projectInfo)));
    }

    /**
     * 删除
     *
     * @param id       主键
     * @param response
     * @return
     */
    @ApiOperation(value = "删除", notes = "")
    @ApiResponses({
            @ApiResponse(code = 201, message = "修改成功"),
            @ApiResponse(code = 400, message = "参数验证失败", response = RestResponse.class)
    })
    @DeleteMapping("projectInfo")
    public RestResponse delete(@ApiParam(value = "主键", required = true)
                               @RequestParam Long id,
                               RestResponse response) {
        projectInfoService.delete(projectInfoService.findOne(id));
        return response.success();
    }

    /**
     * 根据ID查找
     *
     * @param id       主键
     * @param response
     * @return
     */
    @ApiOperation(value = "通过主键查找", notes = "")
    @ApiResponses({
            @ApiResponse(code = 200, message = "查询成功", response = ProjectInfo.class),
            @ApiResponse(code = 404, message = "查询失败", response = RestResponse.class)
    })
    @GetMapping("projectInfo")
    public RestResponse findOne(@ApiParam(value = "主键", required = true)
                                @RequestParam Long id,
                                RestResponse response)  {
        return response.success(projectInfoService.findOne(id));
    }
}