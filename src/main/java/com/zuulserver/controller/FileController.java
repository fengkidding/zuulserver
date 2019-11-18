package com.zuulserver.controller;

import com.zuulserver.model.enums.ResultEnum;
import com.zuulserver.model.vo.ResultVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * 处理文件
 *
 * @author f
 * @date 2018-05-14
 */
@RequestMapping(value = "/upload")
@RestController
public class FileController {

    @Value("${uploadPath}")
    private String path;

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    @PostMapping(value = "/file")
    public ResultVO<String> upload(@RequestParam(value = "file", required = true) MultipartFile file) {
        ResultVO resultVmo = new ResultVO();
        try {
            byte[] bytes = file.getBytes();
            File out = new File(path + file.getOriginalFilename());
            FileCopyUtils.copy(bytes, out);
            resultVmo.setCode(ResultEnum.SUCCESS.getCode());
            resultVmo.setMsg(ResultEnum.SUCCESS.getMsg());
            resultVmo.setData(out.getAbsolutePath());
        } catch (Exception e) {
            resultVmo.setCode(ResultEnum.ERROR.getCode());
            resultVmo.setMsg(ResultEnum.ERROR.getMsg());
            e.printStackTrace();
        }
        return resultVmo;
    }

}
