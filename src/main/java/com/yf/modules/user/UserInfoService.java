package com.yf.modules.user;

import com.yf.common.base.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by if on 2017/11/22.
 *
 * @author sunyifu
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class UserInfoService extends BaseService<UserInfo, UserInfoRepository, Long> {
    public List<UserInfo> findByUsername(String username) {
        return this.repository.findByUsername(username);
    }
}