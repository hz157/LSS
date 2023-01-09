package com.itheima.lss.currency.controller;

import com.itheima.lss.currency.config.ResultTemplate;
import com.itheima.lss.currency.util.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.xml.transform.Result;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

@RestController
@RequestMapping("/lss/file")
public class FilesController {
    private final ResourceLoader resourceLoader;


    @Value("${web.upload-path}")
    private String path;

    public FilesController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @RequestMapping("fileUpload")
    public Object upload(@RequestParam("fileName") MultipartFile file, Map<String, Object> map) {
        try{
            // 要上传的目标文件存放路径
            String localPath = "/usr/java/library/files/photos/";
            // 上传成功或者失败的提示
            String msg = "";
            String path = FileUtils.upload(file, localPath, file.getOriginalFilename());
            if (path != null) {
                // 上传成功，给出页面提示
                return new ResultTemplate<>(path);
            } else {
                return new ResultTemplate<>("400","上传失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            return new ResultTemplate<>("400","异常提示");
        }
    }
    @RequestMapping(value = "avatar", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_PNG_VALUE})
    public Object showPhotos(@RequestParam("fileName") String fileName){
        try {
            // 由于是读取本机的文件，file是一定要加上的， path是在application配置文件中的路径
//            return ResponseEntity.ok(resourceLoader.getResource("file:" + path + filePath));
            File file = new File("/usr/java/library/files/photos/"+fileName);
            FileInputStream inputStream = new FileInputStream(file);
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes, 0, inputStream.available());
            return bytes;
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultTemplate<>("400","异常提示");
        }
    }
}
