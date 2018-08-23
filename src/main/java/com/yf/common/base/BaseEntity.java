package com.yf.common.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * Created by if on 2017/11/22.
 */
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@MappedSuperclass
public abstract class BaseEntity<ID extends Serializable> extends AbstractEntity implements Serializable {
    protected ID id;
    protected Date createTime;
    protected Date updateTime;
    protected String delTag = "0";
    protected ID updateBy;
    protected ID createBy;

    public BaseEntity() {
    }

    public BaseEntity(ID id, Date createTime, Date updateTime, ID updateBy, ID createBy) {
        this.id = id;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.updateBy = updateBy;
        this.createBy = createBy;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_time", nullable = false)
    public Date getCreateTime() {
        return createTime;
    }


    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "update_time", nullable = true)
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }


    @Column(name = "del_tag", nullable = false, length = 1)
    public String getDelTag() {
        return delTag;
    }

    public void setDelTag(String delTag) {
        this.delTag = delTag;
    }

    @Column(name = "update_by", nullable = true)
    public ID getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(ID updateBy) {
        this.updateBy = updateBy;
    }

    @Column(name = "create_by", nullable = true)
    public ID getCreateBy() {
        return createBy;
    }

    public void setCreateBy(ID createBy) {
        this.createBy = createBy;
    }

    @PrePersist
    public void PrePersist() {
        this.updateTime = new Date();
        this.createTime = new Date();
    }

    @PreUpdate
    public void PreUpdate() {
        this.updateTime = new Date();
    }

}
