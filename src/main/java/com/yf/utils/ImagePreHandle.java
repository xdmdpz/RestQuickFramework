package com.yf.utils;

import com.yf.core.exception.Exceptions;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by lidianquan on 2016/12/21.
 */
public class ImagePreHandle {

    /**
     * 图片的预处理，将图片文件转换为二进制流
     *
     * @param file   文件
     * @param suffix 后缀名
     * @return
     */
    public static ByteArrayOutputStream handleImage(MultipartFile file, String suffix) {
        //允许上传的图片格式
        String allowSuffixs = "jpg,jpeg,gif,png,webp";
        if (allowSuffixs.indexOf(suffix) == -1) {
            //不支持此类型
            throw new Exceptions.DataValidationFailedException("不支持" + suffix + "格式类型图片");
        }
        //将图片转换成字节流
        ByteArrayOutputStream baos = null;
        try {
            InputStream is = file.getInputStream();
            byte[] b = new byte[(int) file.getSize()];
            int len = -1;
            baos = new ByteArrayOutputStream();
            while ((len = is.read(b, 0, b.length)) != -1) {
                baos.write(b, 0, len);
            }
            baos.flush();
            is.close();
            return baos;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
