package com.yf.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import org.springframework.stereotype.Repository;
import com.yf.core.domain.UserInfo;

/**
 * Created by xdmdpz on 2018/5/17.
 */
@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long>, JpaSpecificationExecutor<UserInfo> {
    UserInfo findByName(String name);
}
