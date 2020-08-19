package com.yf.modules.login;

import com.yf.core.auth.AccessToken;
import com.yf.core.auth.AuthorizationAnnotation;
import com.yf.core.auth.AuthorizationUtils;
import com.yf.core.base.RestResponse;
import com.yf.core.exception.Exceptions;
import com.yf.modules.user.domain.UserInfo;
import com.yf.modules.user.service.UserInfoService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by xdmdpz on 2018/6/26.
 */
@RestController
@RequestMapping("api")
@AuthorizationAnnotation.RequireValidate
@Api(tags = "登录")
public class LoginController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private AuthorizationUtils authorizationUtils;

    @PostMapping("login")
    @AuthorizationAnnotation.NoRequireValidate
    @ApiOperation(value = "用户登录接口", notes = "通过用户名和密码登录")
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功"),
            @ApiResponse(code = 400, message = "参数验证失败"),
            @ApiResponse(code = 403, message = "密码错误"),
            @ApiResponse(code = 404, message = "用户名不存在"),
    })
    public  RestResponse<AccessToken> login(@RequestBody LoginContext loginContext) {
        UserInfo user = loginService.login(loginContext);
        return RestResponse.success(authorizationUtils.create(user.getId(),user.getUsername(),user.getType()));
    }
    @GetMapping("token")
    @ApiOperation(value = "换取token", notes = "token失效前换取token")
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功"),
            @ApiResponse(code = 403, message = "密码错误"),
    })
    public RestResponse<AccessToken> login(HttpServletRequest request) {
        if(authorizationUtils.verify(request)){
            Integer  userId = authorizationUtils.getUserId(request);
            UserInfo user = userInfoService.findOne(userId);
            return RestResponse.success(authorizationUtils.create(userId,user.getUsername(),user.getType()));
        } else {
            throw new Exceptions.ForbiddenException("token验证失败");
        }
    }



}
