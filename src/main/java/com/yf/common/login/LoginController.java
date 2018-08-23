package com.yf.common.login;

import com.yf.common.auth.AccessToken;
import com.yf.common.auth.AuthorizationAnnotation;
import com.yf.common.auth.AuthorizationUtils;
import com.yf.common.base.RestResponse;
import com.yf.common.exception.Exceptions;
import com.yf.common.user.UserInfo;
import com.yf.common.user.UserInfoService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by xdmdpz on 2018/6/26.
 */
@RestController
@ApiModel("系统登录")
@RequestMapping("api")
@AuthorizationAnnotation.RequireValidate
public class LoginController {

    public static final Logger log = Logger.getLogger(LoginController.class);

    @Autowired
    private LoginService loginService;
    @Autowired
    private UserInfoService userInfoService;

    @ResponseBody
    @PostMapping("login")
    @AuthorizationAnnotation.NoRequireValidate
    @ApiOperation(value = "用户登录接口", notes = "通过用户名和密码登录")
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功"),
            @ApiResponse(code = 400, message = "参数验证失败", response = RestResponse.class),
            @ApiResponse(code = 403, message = "密码错误", response = RestResponse.class),
            @ApiResponse(code = 404, message = "用户名不存在", response = RestResponse.class),
    })
    public RestResponse<AccessToken> login(@RequestBody LoginContext loginContext,RestResponse response) {
        UserInfo user = loginService.login(loginContext);
        return response.success(AuthorizationUtils.getInstance().create(user));
    }
    @ResponseBody
    @GetMapping("token")
    @ApiOperation(value = "换取token", notes = "token失效前换取token")
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功"),
            @ApiResponse(code = 403, message = "密码错误", response = RestResponse.class),
    })
    public RestResponse<AccessToken> login(HttpServletRequest request,RestResponse response) {

        if(AuthorizationUtils.getInstance().verify(request)){
            Long  userId = AuthorizationUtils.getInstance().getUserId(request.getHeader(AuthorizationUtils.HeaderTokenKey));
            UserInfo user = userInfoService.findOne(userId);
            return response.success(AuthorizationUtils.getInstance().create(user));
        } else {
            throw new Exceptions.ForbiddenException("token验证失败");
        }

    }



}
