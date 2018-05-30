package com.yf.core.service;

import com.github.pagehelper.PageHelper;
import com.yf.common.base.BaseService;
import com.yf.core.domain.UserInfo;
import com.yf.core.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.yf.common.domain.PageResult;
import com.yf.core.mapper.UserInfoMapper;

import java.util.List;

/**
 * Created by xdmdpz on 2018/5/17.
 */
@Service
@Transactional(readOnly = true)
public class UserInfoService extends BaseService<UserInfo,UserInfoRepository,Long> {
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private UserInfoRepository userInfoRepository;

    public Page<UserInfo> findAllJpa(int pageSize, int pageNo) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        return userInfoRepository.findAll(pageable);
    }

    public PageResult<UserInfo> findAllMybatis(int pageSize, int pageNo) {
        PageHelper.startPage(pageNo, pageSize);
        List<UserInfo> list = userInfoMapper.findAll();

        PageResult<UserInfo> pageResult = new PageResult<>(pageSize, pageNo);

        return pageResult.toPagedResult(list);
    }


}
