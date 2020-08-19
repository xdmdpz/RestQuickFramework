package com.yf.modules.login;

import com.yf.core.exception.Exceptions;
import com.yf.modules.user.domain.UserInfo;
import com.yf.modules.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by xdmdpz on 2018/6/26.
 */
@Service
public class LoginService {

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 用户登录
     *
     * @return 用户的相关信息 基本信息和权限信息
     */
    public UserInfo login(LoginContext userInfo) {
        Optional.ofNullable(userInfo.getUsername()).orElseThrow(() -> new Exceptions.DataValidationFailedException("用户名不能为空"));
        Optional.ofNullable(userInfo.getPassword()).orElseThrow(() -> new Exceptions.DataValidationFailedException("密码不能为空"));
        return Optional.ofNullable(userInfoService.findByUsername(userInfo.getUsername()))
                .orElseThrow(() -> new Exceptions.DataNotFoundException("此用户不存在"))
                .stream()
                .filter(user -> user.getPassword().equals(userInfo.getPassword()) && userInfo.getUsername().equals(user.getUsername()))
                .findFirst()
                .orElseThrow(() -> new Exceptions.ForbiddenException("用户名或密码错误"));
    }
}
