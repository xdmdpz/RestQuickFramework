package com.yf.modules.projectInfo.service;

import com.yf.modules.projectInfo.repository.ProjectInfoRepository;
import com.yf.modules.projectInfo.domain.ProjectInfo;
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