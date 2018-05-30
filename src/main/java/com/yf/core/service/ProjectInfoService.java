package com.yf.core.service;

import com.yf.core.repository.ProjectInfoRepository;
import com.yf.core.domain.ProjectInfo;
import com.yf.common.base.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* Created by if on 2017/11/22.
* @author sunyifu
*/
@Service
@Transactional(readOnly = true)
public class ProjectInfoService  extends BaseService<ProjectInfo,ProjectInfoRepository,Long>{

}