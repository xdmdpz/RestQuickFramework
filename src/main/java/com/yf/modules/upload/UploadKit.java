package com.yf.modules.upload;

import com.yf.common.utils.MD5Util;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.UUID;

/**
 * Created by if on 2017/12/18.
 */
public class UploadKit {
    private static final String ENCODING = "utf-8";

    /**
     * 上传文件，并获取上传文件（单文件上传）
     *
     * @param request    [request请求]
     * @param folderPath [文件保存路]
     * @return [文件自定义实体类]
     */
    public static FileInfo getFile(HttpServletRequest request, String folderPath) {
        // 创建一个通用的多部分解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        // 新建目录
        File directory = new File(folderPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        try {
            // 判断 request 是否有文件上传,即多部分请求
            if (multipartResolver.isMultipart(request)) {
                // 转换成多部分request
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
                // 取得request中的所有文件名
                Iterator<String> iter = multiRequest.getFileNames();
                while (iter.hasNext()) {
                    FileInfo f = transferFile(iter.next(), folderPath, multiRequest);
                    if (f != null) {
                        return f;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 文件写入磁盘
     *
     * @param path         [description]
     * @param folderPath   [description]
     * @param multiRequest [description]
     * @return [description]
     * @throws IOException [description]
     */
    private static FileInfo transferFile(String path, String folderPath, MultipartHttpServletRequest multiRequest) throws IOException {
        // 取得上传文件
        MultipartFile file = multiRequest.getFile(path);

        if (file != null) {
            // 取得当前上传文件的文件名称
            String fileMD5 = MD5Util.getMD5String(file.getBytes());
            String originalFileName = file.getOriginalFilename();//原名称 带后缀
            String fileName = originalFileName.substring(0, originalFileName.lastIndexOf("."));//原名称
            String suffixName = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);//后缀名

            double size = (file.getSize() * 1.0) / (1024 * 1.0) / (1024 * 1.0);
            BigDecimal bg = new BigDecimal(size);
            double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            String fileSize = f1 + "";

            // 如果名称不为“”,说明该文件存在，否则说明该文件不存在
            if (originalFileName.trim() != "") {
                // 重命名上传后的文件名
                String newName = UUID.randomUUID().toString();
                // 定义上传路径
                String tarpath = folderPath + "/" + newName;
                File localFile = new File(tarpath);
                file.transferTo(localFile);
                FileInfo uploadFile = new FileInfo(fileSize, fileMD5,newName, originalFileName, fileName, suffixName, localFile);
                return uploadFile;
            }
        }
        return null;
    }

    /**
     * 文件下载
     *
     * @param filePath 文件路径
     * @return
     * @throws UnsupportedEncodingException
     * @throws IOException
     */
    public static ResponseEntity<byte[]> download(String filePath) throws UnsupportedEncodingException, IOException {
        String fileName = FilenameUtils.getName(filePath);
        return downloadAssist(filePath, fileName);
    }
    public static ResponseEntity<byte[]> download(String filePath, FileInfo fileInfo) throws UnsupportedEncodingException, IOException {
        return downloadAssist(filePath+"/"+ fileInfo.getFullFileName(),fileInfo.getOriginFileName());
    }

    /**
     * 文件下载
     *
     * @param filePath 文件路径
     * @param fileName 文件名
     * @return
     * @throws UnsupportedEncodingException
     * @throws IOException
     */
    public static ResponseEntity<byte[]> download(String filePath, String fileName) throws UnsupportedEncodingException, IOException {
        return downloadAssist(filePath, fileName);
    }


    /**
     * 文件下载辅助
     *
     * @param filePath 文件路径
     * @param fileName 文件名
     * @return
     * @throws UnsupportedEncodingException
     * @throws IOException
     */
    private static ResponseEntity<byte[]> downloadAssist(String filePath, String fileName) throws UnsupportedEncodingException, IOException {
        File file = new File(filePath);
        if (!file.isFile() || !file.exists()) {
            throw new IllegalArgumentException("filePath 参数必须是真实存在的文件路径:" + filePath);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", URLEncoder.encode(fileName, ENCODING));
        return new ResponseEntity<byte[]>(org.apache.commons.io.FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
    }

}