package com.yf.modules.upload;

import com.yf.core.base.RestResponse;
import com.yf.core.exception.Exceptions;
import com.yf.core.upload.ZimgInfo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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
@RequestMapping("api")
public class FileInfoController {

    @Autowired
    private FileInfoService fileInfoService;

    @PostMapping("file")
    @ApiOperation(value = "文件上传接口", notes = "参数为文件类型")
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功"),
            @ApiResponse(code = 400, message = "参数验证失败"),

    })
    public RestResponse<FileInfo> fileUpload(HttpServletRequest request) {
        return RestResponse.success(fileInfoService.upload(request));
    }

    @PostMapping("img")
    @ApiOperation(value = "图片上传接口", notes = "图片上传接口 \n" +
            "http://xxx/5f189d8ec57f5a5a0d3dcba47fa797e2?w=500&h=500&g=0&x=0&y=0&r=45&q=75&f=jpeg \n" +
            " 宽度w(width) 长度h(height) 灰度g(gray) x坐标x(x-postion) y坐标(y-postion) 角度r(rotate) 质量q(quality) 格式f(format)")
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功"),
            @ApiResponse(code = 400, message = "参数验证失败"),
    })
    public RestResponse<ZimgInfo> imgUpload(@ApiParam(value = "图片") @RequestParam MultipartFile file) {
        return RestResponse.success(fileInfoService.uploadImg(file));
    }
    @ApiOperation(value = "文件下载", notes = "文件下载")
    @GetMapping("file")
    public RestResponse<?> fileDownload(@RequestParam(defaultValue = "") String fullFileName) throws IOException {
        if (StringUtils.isEmpty(fullFileName)) {
            throw new Exceptions.DataValidationFailedException("参数错误");
        }
        return RestResponse.success(fileInfoService.download(fullFileName));
    }


}