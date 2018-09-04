package com.yf.modules.login;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by xdmdpz on 2018/7/2.
 */
@ApiModel
public class LoginContext {
    @ApiModelProperty(value = "用户名",notes = "")
    private String username;
    @ApiModelProperty(value = "密码",notes = "需要MD5")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
