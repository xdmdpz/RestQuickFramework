package com.yf.core.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.yf.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by xdmdpz on 2018/5/17.
 */
@Entity
@Table(name = "user_info")
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "用户信息表")
public class UserInfo extends BaseEntity<Long> implements Serializable {

    private String name;
    private Integer age;
    private String address;


    @Column(name = "name")
    @ApiModelProperty(value = "姓名")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "age")
    @ApiModelProperty(value = "年龄")
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Column(name = "address")
    @ApiModelProperty(value = "地址")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
