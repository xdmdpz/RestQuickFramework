package com.yf.modules.upload;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.yf.common.exception.Exceptions;
import com.yf.common.utils.ImagePreHandle;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

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
    @Autowired
    OkHttpClient okHttpClient;

    /**
     * 图片上传功能
     *
     * @param file 图片请求
     * @return 图表路径
     */
    public ZimgInfo guideBookUpload(MultipartFile file) {
        String url = zimgUrl + "/upload";
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);

        RequestBody requestBody = RequestBody.create(MediaType.parse(suffix), ImagePreHandle.handleImage(file, suffix).toByteArray());
        Request request = new Request.Builder()
                .url(url)
                .header("Content-Type", suffix)
                .post(requestBody)
                .build();
        try {
            ZimgResponse zimgResponse = new ObjectMapper().readValue(okHttpClient.newCall(request).execute().body().string(), ZimgResponse.class);
            return Optional.ofNullable(zimgResponse.getInfo())
                    .orElseThrow(() -> new Exceptions.DataValidationFailedException("文件上传失败"));

        } catch (IOException e) {
            throw new Exceptions.DataValidationFailedException("文件写入失败");
        }


    }


}
