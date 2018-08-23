package com.yf.common.file;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yf.common.base.BaseEntity;

import javax.persistence.*;
import java.io.File;
import java.io.Serializable;

/**
 * Created by xdmdpz on 2018/7/4.
 */
@Entity
@Table(name = "file_info")
public class FileInfo extends BaseEntity<Long> implements Serializable {
    //文件大小
    protected String fileSize;
    //文件MD5
    protected String fileMd5;
    //存在服务器端的名称
    protected String fullFileName;
    //文件类型
    protected int type;
    //源文件名带后缀
    protected String originFileName;
    //原名称
    protected String fileName;
    //原后缀
    protected String suffixName;
    //excel转csv ids
    protected String conversion;

    //与数据库无关
    private String name;
    private String url;
    protected File file;

    @Transient
    public String getName() {
        return name = this.originFileName;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Transient
    public String getUrl() {
        return url = this.fullFileName;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    @Transient
    @JsonIgnore
    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public FileInfo() {
    }

    @Basic
    @Column(name = "conversion")
    public String getConversion() {
        return conversion;
    }

    public void setConversion(String conversion) {
        this.conversion = conversion;
    }

    public FileInfo(String fileSize, String fileMd5, String fullFileName, String originFileName, String fileName, String suffixName, File file) {
        this.fileSize = fileSize;
        this.fileMd5 = fileMd5;
        this.fullFileName = fullFileName;
        this.originFileName = originFileName;
        this.fileName = fileName;
        this.suffixName = suffixName;
        this.file = file;
    }

    public FileInfo(String fullFileName, String suffixName, int type) {
        this.suffixName = suffixName;
        this.type = type;
        this.fullFileName = fullFileName;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    @Basic
    @Column(name = "file_size")
    public String getFileSize() {
        return this.fileSize;
    }

    public void setFileMd5(String fileMd5) {
        this.fileMd5 = fileMd5;
    }

    @Basic
    @Column(name = "file_md5")
    public String getFileMd5() {
        return this.fileMd5;
    }


    @Basic
    @Column(name = "type")
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setFullFileName(String fullFileName) {
        this.fullFileName = fullFileName;
    }

    @Basic
    @Column(name = "full_file_name")
    public String getFullFileName() {
        return this.fullFileName;
    }

    public void setOriginFileName(String originFileName) {
        this.originFileName = originFileName;
    }

    @Basic
    @Column(name = "origin_file_name")
    public String getOriginFileName() {
        return this.originFileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Basic
    @Column(name = "file_name")
    public String getFileName() {
        return this.fileName;
    }

    public void setSuffixName(String suffixName) {
        this.suffixName = suffixName;
    }

    @Basic
    @Column(name = "suffix_name")
    public String getSuffixName() {
        return this.suffixName;
    }

    @Override
    public String toString() {
        return "UploadFile{" +
                "(文件)file=" + file +
                ",(文件大小)fileSize='" + fileSize + '\'' +
                ",(文件md5)fileMD5='" + fileMd5 + '\'' +
                ",(文件存在服务器名称)fullFileName='" + fullFileName + '\'' +
                ",(文件原名称带后缀)originalFileName='" + originFileName + '\'' +
                ",(文件原名称)fileName='" + fileName + '\'' +
                ",(文件后缀)suffixName='" + suffixName + '\'' +
                '}';
    }

}