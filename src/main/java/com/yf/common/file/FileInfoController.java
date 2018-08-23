package com.yf.common.file;

import com.yf.common.base.RestResponse;
import com.yf.common.exception.Exceptions;
import io.swagger.annotations.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by if on 2017/11/22.
 *
 * @author if
 */
@RestController
@ApiModel("文件图片上传")
@RequestMapping("api")
public class FileInfoController {

    private static Logger logger = Logger.getLogger(FileInfoController.class);

    @Autowired
    private FileInfoService fileInfoService;

    /**
     * 实现文件上传
     */
    @PostMapping("file")
    @ApiOperation(value = "文件上传接口", notes = "参数为文件类型")
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功"),
            @ApiResponse(code = 400, message = "参数验证失败", response = RestResponse.class),

    })
    public RestResponse<FileInfo> fileUpload(HttpServletRequest request, RestResponse response) {
        return response.success(fileInfoService.upload(request));
    }


    /**
     * 实现文件上传
     */
    @PostMapping("img")
    @ApiOperation(value = "图片上传接口", notes = "图片上传接口 \n" +
            "http://xxx/5f189d8ec57f5a5a0d3dcba47fa797e2?w=500&h=500&g=0&x=0&y=0&r=45&q=75&f=jpeg \n" +
            " 宽度w(width) 长度h(height) 灰度g(gray) x坐标x(x-postion) y坐标(y-postion) 角度r(rotate) 质量q(quality) 格式f(format)")
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功"),
            @ApiResponse(code = 400, message = "参数验证失败", response = RestResponse.class),

    })
    public RestResponse imgUpload(@ApiParam(value = "图片") @RequestParam MultipartFile file, RestResponse response) {
        return response.success(fileInfoService.uploadImg(file));
    }

    /**
     * 实现文件下载
     */
    @GetMapping("file")
    public RestResponse fileDownload(@RequestParam(defaultValue = "") String fullFileName,
                                     @RequestParam(defaultValue = "") String type,
                                     RestResponse response) throws IOException {


        if (fullFileName == null || fullFileName.equals("")) {
            throw new Exceptions.DataValidationFailedException("参数错误");
        }
        if (type.equals("download")) {
            return response.success(fileInfoService.download(fullFileName));
        } else {
            //查询文件是否存在
            FileInfo fileInfo = fileInfoService.findOneByFullFileName(fullFileName);
            if (fileInfo != null) {
                response.success(fileInfo);
            } else {
                throw new Exceptions.DataNotFoundException("文件未找到");
            }
        }
        return response;
    }


}