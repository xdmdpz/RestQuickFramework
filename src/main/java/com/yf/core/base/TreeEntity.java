package com.yf.core.base;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by if on 2017/12/1.
 */

@Data
public abstract class TreeEntity<T, ID extends Serializable> implements Serializable {
    protected ID id;
    //使用时需要在实体类pid字段做关联
    //重写get方法 指向pid字段
    protected ID pid;
    protected List<T> children;


}