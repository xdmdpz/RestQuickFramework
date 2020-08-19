package com.yf.modules.upload;


import com.yf.core.upload.UploadFileInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* Created by if on 2017/11/22.
*/
@Repository
public interface FileInfoRepository extends JpaRepository<FileInfo, Long>,JpaSpecificationExecutor<FileInfo> {
	UploadFileInfo findFirstByFullFileName(String fullFileName);

	List<UploadFileInfo> findAllByFileMd5(String fileMd5);
}

