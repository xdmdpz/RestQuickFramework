package com.yf.modules.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.yf.core.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;


@Entity
@Table(name = "user_info")
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class UserInfo extends BaseEntity<Integer> implements Serializable {

    @Basic
    @ApiModelProperty(value = "真实姓名")
    @Column(name = "realname")
    private String realname;

    @Basic
    @ApiModelProperty(value = "用户名")
    @Column(name = "username")
    private String username;

    @Basic
    @ApiModelProperty(value = "密码")
    @Column(name = "password")
    @JsonIgnore
    private String password;

    @Basic
    @ApiModelProperty(value = "用户类型")
    @Column(name = "type")
    private Integer type;


    public UserInfo(UserInfo userInfo) {
        super(userInfo.getId(), userInfo.getCreateTime(), userInfo.getUpdateTime(), userInfo.getUpdateBy(), userInfo.getCreateBy());
        this.realname = userInfo.getRealname();
        this.password = userInfo.getPassword();
        this.username = userInfo.getUsername();
        this.type = userInfo.getType();
    }

}