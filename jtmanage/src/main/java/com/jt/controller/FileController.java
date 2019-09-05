package com.jt.controller;

import com.jt.service.FileService;
import com.jt.vo.EasyUI_Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


@Controller  //跳转页面时使用
public class FileController {

    @Autowired
    private FileService fileService;

    /**
     * 1.确定url请求路径
     * http://localhost:8080/file
     * 2.获取用户提交的参数
     * 3.响应合适的页面
     * @return 将文件上传到C:\jt\image\
     */
    @RequestMapping("/file")
    public String file(MultipartFile fileImage) throws IOException {
        String name = fileImage.getOriginalFilename();

        //判断文件夹是否存在
        File fileDir = new File("C:\\jt\\image\\");
        if (!fileDir.exists()){
            //表示文件不存在 mkdirs创建多级目录
            fileDir.mkdirs();
        }
        File file = new File("C:\\jt\\image\\"+name);
        //1.实现文件上传API
        fileImage.transferTo(file);
        //要求重定向到file.jsp
        return "redirect:/file.jsp";
    }

    /**
     * 实现用户文件上传
     */
    @RequestMapping("/pic/upload")
    @ResponseBody
    public EasyUI_Image fileUpload(MultipartFile uploadFile){

        return fileService.fileUpload(uploadFile);
    }


}
