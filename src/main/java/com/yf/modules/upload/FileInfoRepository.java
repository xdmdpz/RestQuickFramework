package com.yf.modules.upload;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* Created by if on 2017/11/22.
*/
@Repository
public interface FileInfoRepository extends JpaRepository<FileInfo, Long>,JpaSpecificationExecutor<FileInfo> {
	FileInfo findFirstByFullFileName(String fullFileName);

	List<FileInfo> findAllByFileMd5(String fileMd5);
}

