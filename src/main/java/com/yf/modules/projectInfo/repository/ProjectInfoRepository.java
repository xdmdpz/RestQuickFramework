package com.yf.modules.projectInfo.repository;

import com.yf.modules.projectInfo.domain.ProjectInfo;;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
* Created by if on 2017/11/22.
*/
@Repository
public interface ProjectInfoRepository extends JpaRepository<ProjectInfo, Integer>,JpaSpecificationExecutor<ProjectInfo>{


	
}

