package com.yf.modules.upload;


import com.yf.common.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * Created by if on 2017/11/22.
 *
 * @author sunyifu
 */
@Service
@Transactional(readOnly = true)
public class FileInfoService extends BaseService<FileInfo, FileInfoRepository, Long> {
    @Value("${custom.upload.file}")
    private String filePath;
    @Autowired
    private ZimgClient zimgClient;

    /**
     * 文件上传
     *
     * @param request 请求地址
     * @return
     * @throws IOException
     */
    @Transactional(readOnly = false)
    public FileInfo upload(HttpServletRequest request) {
        FileInfo uploadFile = UploadKit.getFile(request, filePath);
        super.create(uploadFile);
        return uploadFile;
    }


    /**
     * 图片上传
     *
     * @return
     * @throws IOException
     */
    @Transactional(readOnly = false)
    public ZimgInfo uploadImg(MultipartFile file) {
        return zimgClient.guideBookUpload(file);

    }

    /**
     * 文件下载
     *
     * @param fullFileName 全路径名
     * @return
     * @throws IOException
     */
    public Object download(String fullFileName) throws IOException {
        FileInfo fileInfo = repository.findFirstByFullFileName(fullFileName);
        return UploadKit.download(filePath, fileInfo);
    }

    public FileInfo findOneByFullFileName(String fullFileName) throws IOException {
        return repository.findFirstByFullFileName(fullFileName);
    }


}