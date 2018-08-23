package com.yf.common.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.yf.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "user_info")
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "")
public class UserInfo extends BaseEntity<Long> implements Serializable {

    private String realname;

    private String username;

    private String password;

    private Integer type;


    public UserInfo() {

    }


    public UserInfo(UserInfo userInfo) {
        super(userInfo.getId(), userInfo.getCreateTime(), userInfo.getUpdateTime(), userInfo.getUpdateBy(), userInfo.getCreateBy());
        this.realname = userInfo.getRealname();
        this.password = userInfo.getPassword();
        this.username = userInfo.getUsername();
        this.type = userInfo.getType();
    }

    public UserInfo(String realname, String username, String password, Integer type) {
        this.realname = realname;
        this.username = username;
        this.password = password;
        this.type = type;
    }



    public void setRealname(String realname) {
        this.realname = realname;
    }

    @Basic
    @ApiModelProperty(value = "真实姓名")
    @Column(name = "realname")
    public String getRealname() {
        return this.realname;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @ApiModelProperty(value = "用户名")
    @Column(name = "username")
    public String getUsername() {
        return this.username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @ApiModelProperty(value = "密码")
    @Column(name = "password")
    @JsonIgnore
    public String getPassword() {
        return this.password;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Basic
    @ApiModelProperty(value = "用户类型")
    @Column(name = "type")
    public Integer getType() {
        return this.type;
    }


}