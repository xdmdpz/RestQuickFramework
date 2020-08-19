package com.yf.core.upload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.Serializable;

/**
 * Created by xdmdpz on 2018/7/4.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadFileInfo implements Serializable {
    //文件大小
    private String fileSize;
    //文件MD5
    private String fileMd5;
    //上传名称
    private String originFileName;
    //源文件名带后缀
    private String fullFileName;
    //原名称
    private String fileName;
    //原后缀
    private String suffix;

    @JsonIgnore
    private File file;

    @Override
    public String toString() {
        return "UploadFile{" +
                "(文件)upload=" + file +
                ",(文件大小)fileSize='" + fileSize + '\'' +
                ",(文件md5)fileMD5='" + fileMd5 + '\'' +
                ",(文件原名称带后缀)fullFileName='" + fullFileName + '\'' +
                ",(上传服务器时的名称)originalFileName='" + originFileName + '\'' +
                ",(文件原名称)fileName='" + fileName + '\'' +
                ",(文件后缀)suffixName='" + suffix + '\'' +
                '}';
    }

}