package com.yf.modules.projectInfo.domain;

import com.yf.core.base.BaseEntity;

import lombok.*;
import io.swagger.annotations.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@ApiModel(value = "")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class ProjectInfo extends BaseEntity<Integer> implements Serializable {
    @Basic
    @ApiModelProperty(value = "项目名称")
    @Column(name = "project_name")
    private String projectName;
    @Basic
    @ApiModelProperty(value = "项目描述")
    @Column(name = "project_desc")
    private String projectDesc;


}