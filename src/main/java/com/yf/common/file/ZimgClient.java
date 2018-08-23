package com.yf.common.file;


import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
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
    public String guideBookUpload(MultipartFile file) {

        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        //将图片转换成字节流
        ByteArrayOutputStream baos = ImagePreHandle.handleImage(file, suffix);
        //headers信息
        Map<String, String> headers = new HashMap<String, String>();
        //获取zimg服务器地址
        //接口信息
        String url = zimgUrl + "/upload";

        headers.put("Content-type", suffix);
        //发送post请求，并获取返回结果
        String result = HttpUtil.binaryPost(url, headers, baos);
        //将返回结果转换成对象
        JSONObject jsonObject = new JSONObject(result);
        //获取info字段所返回的对象
        JSONObject info = jsonObject.getJSONObject("info");
        //获取对象中的md5字段的值
        String path = zimgUrl + "/" + info.getString("md5");
        return path;
    }


}
