package com.yf.core.base;


import cn.hutool.core.bean.BeanUtil;
import com.yf.core.auth.AuthorizationUtils;
import com.yf.core.exception.Exceptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * Created by if on 2017/11/22.
 *
 * @author sunyifu
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public abstract class BaseService<T extends BaseEntity, R extends JpaRepository<T, ID>, ID extends Serializable> {

    @Autowired
    protected R repository;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private AuthorizationUtils authorizationUtils;


    /**
     * 保存
     *
     * @param entity
     */
    @Transactional(readOnly = false)
    public T save(T entity) {
        if (IsNewRecord(entity))
            return this.create(entity);
        else
            return this.update(entity);

    }

    public boolean IsNewRecord(T entity) {
        if (entity.id.equals("0") || entity.isNewRecord()) {
            return true;
        } else
            return false;
    }

    /**
     * 新增
     *
     * @param entity
     */
    @Transactional(readOnly = false)
    public T create(T entity) {
        return repository.save(entity);

    }
    @Transactional(readOnly = false)
    public void saveAll(List<T> list, HttpServletRequest request) {
        list.forEach(entity -> {
            entity.setCreateBy(authorizationUtils.getUserId(request));
            entity.setUpdateBy(authorizationUtils.getUserId(request));
        });
        this.repository.saveAll(list);
    }

    /**
     * 新增
     *
     * @param entity
     */
    @Transactional(readOnly = false)
    public T create(T entity, HttpServletRequest request) {
        entity.setCreateBy(authorizationUtils.getUserId(request));
        entity.setUpdateBy(authorizationUtils.getUserId(request));
        return this.create(entity);

    }
    /**
     * 更新
     *
     * @param entity
     */
    @Transactional(readOnly = false)
    public T update(T entity) {
        T update = repository.getOne((ID) entity.id);
        BeanUtil.copyProperties(entity, update, "id", "createTime", "delTag", "updateTime");
        return repository.saveAndFlush(update);
    }

    /**
     * 更新
     *
     * @param entity
     */
    @Transactional(readOnly = false)
    public T update(T entity, HttpServletRequest request) {
        T update = repository.getOne((ID) entity.id);
        BeanUtil.copyProperties(entity, update, "id", "createTime", "delTag", "updateTime");
        entity.setUpdateBy(authorizationUtils.getUserId(request));
        return this.update(update);
    }


    /**
     * 删除
     *
     * @param entity
     */
    @Transactional(readOnly = false)
    public void delete(T entity) {
        repository.delete(entity);
    }




    /**
     * 通过主键查找
     *
     * @param id
     * @return
     */
    public T findOne(ID id) throws Exceptions.DataValidationFailedException {
        Optional<T> update = repository.findById(id);
        return update.orElseThrow(() -> new Exceptions.DataValidationFailedException("参数验证失败"));
    }

    public T findOne(T t) throws Exceptions.DataValidationFailedException {
        return this.findOne((ID) t.id);
    }

    /**
     * 查询所有（排序）
     *
     * @param sort 排序
     * @return
     */
    public List<T> findList(Sort sort) {
        return repository.findAll(sort);
    }

    /**
     * 查询所有(分页)
     *
     * @param pageable 分页
     * @return
     */
    public Page<T> findPage(Pageable pageable) {
        return repository.findAll(pageable);
    }


}