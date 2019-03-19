package com.yf.common.base;

import com.github.pagehelper.Page;

import java.io.Serializable;
import java.util.List;

/**
 * mybatis分页返回数据包专用实体类
 *
 * 因为项目架构中使用了 springdataJpa与 mybatis
 * mybatis使用 pageHelper进行分页
 * jpa是用内置的分页
 * 返回值对象不一致
 *
 * 以jpa分页对象字段为基准，新建PageResult对象，实现分页后对象格式的统一
 *
 * @author if
 *
 * 2018-05-18
 */
public class PageResult<T> implements Serializable {

    public PageResult(int pageSize, int pageNo) {
        this.size = pageSize;
        this.number = pageNo;
    }

    private int totalPages = 1;    //总页数
    private long totalElements = 7;   //总条数
    private int size = 15;      //页大小
    private int number = 1;
    private List content;

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List getContent() {
        return content;
    }

    public void setContent(List content) {
        this.content = content;
    }


    public PageResult<T> toPagedResult(List<T> datas) {
        if (datas instanceof Page) {
            Page page = (Page) datas;
            this.setNumber(page.getPageNum());
            this.setSize(page.getPageSize());
            this.setContent(page.getResult());
            this.setTotalElements(page.getTotal());
            this.setTotalPages(page.getPages());
        } else {
            this.setContent(datas);
            this.setTotalElements(datas.size());
        }
        return this;
    }

}
