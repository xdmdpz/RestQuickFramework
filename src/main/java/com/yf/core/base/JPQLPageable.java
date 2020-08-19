package com.yf.core.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

/**
 * 自定义jpql分页 接口 默认实现
 */
public interface JPQLPageable {
    final boolean COUNT_QUERY = true;
    final boolean QUERY = false;

    /**
     * 分页查找  兼容 PageAble对象
     * @param entityManager
     * @param query  查询query对象
     * @param countQuery countQuery对象
     * @param pageable 分页对象
     * @return
     *
     * 创建countQuery
     *  CriteriaQuery query = builder.createQuery(Long.class);// 创建查询
     *  Root<UserInfo> root = query.from(UserInfo.class);// 构建Root
     *  query.select(builder.count(root));
     *
     * 参数略...
     */
    default Page findpage(EntityManager entityManager,CriteriaQuery query, CriteriaQuery countQuery, Pageable pageable) {
        //SQL查询对象
        TypedQuery createQuery = entityManager.createQuery(query);

        //分页参数
        Integer pageSize = pageable.getPageSize();
        Integer pageNo = pageable.getPageNumber();

        //SQL查询对象
        Long count = (Long) entityManager.createQuery(countQuery).getSingleResult();
        // 实际查询返回分页对象
        int startIndex = pageSize * pageNo;
        createQuery.setFirstResult(startIndex);
        createQuery.setMaxResults(pageable.getPageSize());
        Page pageRst =
                new PageImpl(createQuery.getResultList(), pageable, count);
        return pageRst;
    }
}
