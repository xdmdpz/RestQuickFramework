package com.yf.core.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * Created by if on 2017/11/22.
 */
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class BaseEntity<ID extends Serializable> extends AbstractEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected ID id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_time", nullable = false)
    protected Date createTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "update_time", nullable = true)
    protected Date updateTime;

    @Column(name = "del_tag", nullable = false, length = 1)
    protected String delTag = "0";

    @Column(name = "update_by", nullable = true)
    protected ID updateBy;

    @Column(name = "create_by", nullable = true)
    protected ID createBy;

    public BaseEntity(ID id, Date createTime, Date updateTime, ID updateBy, ID createBy) {
        this.id = id;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.updateBy = updateBy;
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
