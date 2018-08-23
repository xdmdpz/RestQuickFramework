package com.yf.core.mapper;

import com.yf.common.user.UserInfo;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xdmdpz on 2018/5/17.
 */
@Repository
public interface UserInfoMapper {
    @Select("select * from user_info")
    List<UserInfo> findAll();
}
