package com.jt.service.impl;

import com.jt.service.FileService;
import com.jt.vo.EasyUI_Image;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Administrator on 2019/7/5.
 */
@SuppressWarnings("ALL")
@Service
//加载配置文件,将数据交给Spring容器管理
@PropertySource(value = "classpath:/properties/image.properties")
public class FileServiceImpl implements FileService {

    /**
     * 由于成员变量将路径写死,扩展不易,最好方式
     * 应该写到配置文件中动态获取
     */
    @Value("${image.localDirPath}")
    private String localDirPath; //= "c:/jt/image/";

    @Value("${image.urlDirPath}")
    private String urlDirPath;

    /**
     * 问题1：校验文件类型是否为图片？ 如何校验
     * 利用后缀校验
     * 问题2：如何防止恶意的文件上传
     * 将文件交给工具api校验从中获取宽高
     * 问题3：众多图片如何保存
     * 分文件存储：按照yyyy/MM/dd
     * 问题4：文件如果重名如何处理
     * 自定义UUID为文件名称
     * <p>
     * 文件上传思路
     * 1.获取用户文件名用于校验
     * 2.校验文件名称是否为图片
     * 3.利用API读取用户提交数据
     * 4.采用以时间格式创建文件夹保存数据 yyyy/MM/dd
     * 5.判断文件夹是否存在 不存在 新建文件目录
     * 6.采用UUID为文件名称,防止文件重名 32位16进制数
     *
     * @param uploadFile
     * @return
     */
    @Override
    public EasyUI_Image fileUpload(MultipartFile uploadFile) {
        EasyUI_Image ui_image = new EasyUI_Image();
        //1.获取文件名称
        String fileName = uploadFile.getOriginalFilename();
        //2.校验文件名称  正则表达式
        fileName = fileName.toLowerCase();//将字符转化为小写
        if (!fileName.matches("^.+\\.(jpg|png|gif)$")) {
            ui_image.setError(1);  //表示文件上传有误
            return ui_image;
        }


        //3.利用API读取用户提交数据
        try {
            BufferedImage bufferedImage = ImageIO
                    .read(uploadFile.getInputStream());
            int height = bufferedImage.getHeight();
            int width = bufferedImage.getWidth();

            if (height == 0 || width == 0) {
                ui_image.setError(1);
                return ui_image;
            }
            //封装图片数据
            ui_image.setHeight(height);
            ui_image.setWidth(width);

            //4.以时间格式创建文件夹
            String dateDirPath = new SimpleDateFormat
                    ("yyyy/MM/dd").format(new Date());
            String realDirPath = localDirPath + dateDirPath;
            File dirFile = new File(realDirPath);
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }
            //6.采用UUID命名文件名称
            String uuid = UUID.randomUUID()
                    .toString().replaceAll("-", "");
            //截取字符串
            String fileType = fileName
                    .substring(fileName.lastIndexOf("."));
            String realFileName = uuid + fileType;

            //7.实现文件上传
            File file = new File(realDirPath + "/" + realFileName);
            uploadFile.transferTo(file);
            String realUrlPath = urlDirPath + dateDirPath + "/" + realFileName;
            //8.编辑虚拟路径 数据返回
            ui_image.setUrl(realUrlPath);
            System.out.println(ui_image.getUrl());

        } catch (Exception e) {
            e.printStackTrace();
            ui_image.setError(1);
            return ui_image;
        }
        return ui_image;
    }
}

