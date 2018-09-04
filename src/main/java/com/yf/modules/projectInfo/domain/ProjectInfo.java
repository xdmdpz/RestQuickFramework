package com.yf.modules.projectInfo.domain;

import com.yf.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Column;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "project_info")
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "")
public class ProjectInfo extends BaseEntity<Long> implements Serializable {

    private String projectInfoId;

    private String projectName;

    private String projectDesc;

    public void setProjectInfoId(String projectInfoId) {
        this.projectInfoId = projectInfoId;
    }

    @Basic
    @ApiModelProperty(value = "主键")
    @Column(name = "project_info_id")
    public String getProjectInfoId() {
        return this.projectInfoId;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Basic
    @ApiModelProperty(value = "项目名称")
    @Column(name = "project_name")
    public String getProjectName() {
        return this.projectName;
    }

    public void setProjectDesc(String projectDesc) {
        this.projectDesc = projectDesc;
    }

    @Basic
    @ApiModelProperty(value = "项目描述")
    @Column(name = "project_desc")
    public String getProjectDesc() {
        return this.projectDesc;
    }


}