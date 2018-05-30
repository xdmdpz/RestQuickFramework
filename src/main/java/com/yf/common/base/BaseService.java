package com.yf.common.base;


import com.yf.common.exception.Exceptions;
import com.yf.common.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
        entity.setCreateTime(null);
        entity.setUpdateTime(null);
        return repository.save(entity);

    }

    /**
     * 更新
     *
     * @param entity
     */
    @Transactional(readOnly = false)
    public T update(T entity) {
        T update = repository.getOne((ID) entity.id);
        BeanUtils.copyProperties(entity, update, new String[]{"id", "createTime", "delTag", "updateTime"});
        return repository.saveAndFlush(update);
    }

    /**
     * 更新
     *
     * @param entity
     */
    @Transactional(readOnly = false)
    public void delete(T entity) {
        repository.delete(entity);
    }

    /**
     * 批量更新
     *
     * @param entitys
     */
    @Transactional(readOnly = false)
    public void batchUpate(List<T> entitys) {
        for (int i = 0; i < entitys.size(); i++) {
            em.merge(entitys.get(i));
            if (i % 30 == 0) {
                em.flush();
                em.clear();
            }
        }
    }

    /**
     * 批量插入
     *
     * @param entitys
     */
    @Transactional(readOnly = false)
    public void batchCreate(List<T> entitys) {
        for (int i = 0; i < entitys.size(); i++) {
            em.persist(entitys.get(i));
            if (i % 30 == 0) {
                em.flush();
                em.clear();
            }
        }
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