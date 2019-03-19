package com.yf.modules.upload;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 处理图片上传的类
 * <p>
 * Created by li on 2016/10/18.
 */
@Component
public class ZimgClient {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${custom.upload.zimg}")
    private String zimgUrl;

    /**
     * 图片上传功能
     *
     * @param file 图片文件
     * @return 图表路径
     */
    public String guideBookUpload(MultipartHttpServletRequest request) {
        //获取zimg服务器地址
        //接口信息
        String url = zimgUrl + "/upload";
//        String suffix = file.getName().substring(file.getName().lastIndexOf(".") + 1);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();

        params.add("file", new FileSystemResource(new File(file.getOriginalFilename())));
        params.add("fileName",file.getName());

        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(params, httpHeaders);
        String result = restTemplate.postForObject(url, httpEntity, String.class);
        return result;

    }


}
