package com.yf.common.login;

import com.yf.common.exception.Exceptions;
import com.yf.common.user.UserInfo;
import com.yf.common.user.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

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

        if (StringUtils.isEmpty(userInfo.getUsername())) {
            throw new Exceptions.DataValidationFailedException("用户名不能为空");
        }
        if (StringUtils.isEmpty(userInfo.getPassword())) {
            throw new Exceptions.DataValidationFailedException("密码不能为空");
        }

        //通过用户名查找用户
        List<UserInfo> users = userInfoService.findByUsername(userInfo.getUsername());
        if (users == null)
            throw new Exceptions.DataNotFoundException("此用户不存在");
        if (users.size()==0)
            throw new Exceptions.DataNotFoundException("此用户不存在");
        UserInfo user = users.get(0);
        if (user.getPassword() == null || user.getPassword() == "" || !user.getPassword().equals(userInfo.getPassword())) {
            throw new Exceptions.ForbiddenException("用户名或密码错误");
        }
        return user;
    }
}
