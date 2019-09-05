package com.jt.service;

import com.jt.vo.EasyUI_Image;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Administrator on 2019/7/5.
 */
public interface FileService {
    EasyUI_Image fileUpload(MultipartFile uploadFile);
}
