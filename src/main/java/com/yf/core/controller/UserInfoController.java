package com.yf.core.controller;

import com.yf.common.base.RestResponse;
import com.yf.common.domain.PageResult;
import com.yf.core.domain.UserInfo;
import com.yf.core.service.UserInfoService;
import io.swagger.annotations.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

/**
 * Created by xdmdpz on 2018/5/17.
 */
@RestController
@Api(tags = "userInfo", description = "用户管理")
@RequestMapping("api")
public class UserInfoController {

    private static Logger logger = Logger.getLogger(UserInfoController.class);

    @Autowired
    private UserInfoService _userInfoService;

    @GetMapping("mybatis")
    public PageResult<UserInfo> mybatis(@RequestParam(value = "size", defaultValue = "0") int size,
                                        @RequestParam(value = "page", defaultValue = "0") int page) {
        return _userInfoService.findAllMybatis(size, page);
    }

    @GetMapping("jpa")
    public Page<UserInfo> jpa(int pageSize, int pageNo) {
        return _userInfoService.findAllJpa(pageSize, pageNo);
    }


    /**
     * 获取列表
     *
     * @param page     页码
     * @param size     分页大小
     * @param response
     * @return
     */
    @ApiOperation(value = "ID查找接口", notes = "")
    @ApiResponses({
            @ApiResponse(code = 200, message = "查询成功", response = UserInfo.class),
            @ApiResponse(code = 404, message = "查询失败", response = RestResponse.class)
    })
    @GetMapping("userInfos")
    public RestResponse findPage(@ApiParam(value = "页码" ,defaultValue = "1")
                                    @RequestParam(value = "page", defaultValue = "1") Integer page,
                                 @ApiParam(value = "页大小",defaultValue = "15")
                                    @RequestParam(value = "size", defaultValue = "15") Integer size,
                                 RestResponse response) {
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        Pageable pageable = PageRequest.of(page, size, sort);


        return response.success(_userInfoService.findPage(pageable));
    }

    /**
     * 新增
     *
     * @param entity   新增实体，前端数据需要 JSON.stringify(data)
     * @param response
     * @return
     */
    @PostMapping("userInfo")
    @ApiOperation(value = "添加用户接口", notes = "")
    @ApiResponses({
            @ApiResponse(code = 201, message = "添加成功"),
            @ApiResponse(code = 400, message = "参数验证失败", response = RestResponse.class)
    })
    public RestResponse create(
            @ApiParam(value = "实体", required = true) @RequestBody UserInfo entity,
            RestResponse response) {
        response.success(_userInfoService.create(entity));
        return response.success();
    }

    /**
     * 更新
     *
     * @param entity   更新实体，前端数据需要 JSON.stringify(data)
     * @param response
     * @return
     */
    @ApiOperation(value = "修改接口", notes = "")
    @ApiResponses({
            @ApiResponse(code = 201, message = "修改成功"),
            @ApiResponse(code = 400, message = "参数验证失败", response = RestResponse.class)
    })
    @PatchMapping("userInfo")
    public RestResponse update(
            @ApiParam(value = "实体", required = true) @RequestBody UserInfo entity,
            RestResponse response) {
        return response.success(_userInfoService.update(_userInfoService.findOne(entity)));
    }

    /**
     * 删除
     *
     * @param id       主键
     * @param response
     * @return
     */
    @ApiOperation(value = "删除接口", notes = "")
    @ApiResponses({
            @ApiResponse(code = 201, message = "修改成功"),
            @ApiResponse(code = 400, message = "参数验证失败", response = RestResponse.class)
    })
    @DeleteMapping("userInfo")
    public RestResponse delete(@ApiParam(value = "主键", required = true)
                                   @RequestParam Long id,
            RestResponse response) {
        _userInfoService.delete(_userInfoService.findOne(id));
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
            @ApiResponse(code = 200, message = "查询成功", response = UserInfo.class),
            @ApiResponse(code = 404, message = "查询失败", response = RestResponse.class)
    })
    @GetMapping("userInfo")
    public RestResponse findOne(
            @ApiParam(value = "主键", required = true) @RequestParam Long id,
            RestResponse response) {
        return response.success(_userInfoService.findOne(id));

    }

}
