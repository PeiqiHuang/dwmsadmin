package com.dwms.common.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dwms.common.config.ImageConfig;
import com.dwms.common.domain.ImgResponse;
import com.dwms.common.exception.FileDownloadException;
import com.dwms.common.util.FileUtils;
import com.dwms.common.util.UUIDUtils;

@Controller
public class CommonController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private static long IMG_MAX_SIZE = 2 * 1024 * 1024;
    // 文件允许格式
    private String[] imageFiles = {".gif", ".png", ".jpg", ".jpeg", ".bmp"};
    
    @Autowired
    private ImageConfig imageConfig;
    
    @RequestMapping("common/download")
    public void fileDownload(String fileName, Boolean delete, HttpServletResponse response) throws IOException, FileDownloadException {
        if (StringUtils.isNotBlank(fileName) && !fileName.endsWith(".xlsx") && !fileName.endsWith(".csv"))
            throw new FileDownloadException("不支持该类型文件下载");
        String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf('_') + 1);
        String filePath = "file/" + fileName;
        File file = new File(filePath);
        if (!file.exists())
            throw new FileDownloadException("文件未找到");
        // 文件名中文乱码问题
        realFileName = new String(realFileName.getBytes("UTF-8"), "ISO-8859-1");
        response.setHeader("Content-Disposition", "attachment;fileName=" + realFileName);//java.net.URLEncoder.encode(realFileName, "utf-8")
        response.setContentType("multipart/form-data");
        response.setCharacterEncoding("utf-8");
        try (InputStream inputStream = new FileInputStream(file); OutputStream os = response.getOutputStream()) {
            byte[] b = new byte[2048];
            int length;
            while ((length = inputStream.read(b)) > 0) {
                os.write(b, 0, length);
            }
        } catch (Exception e) {
            log.error("文件下载失败", e);
        } finally {
            if (delete)
                Files.delete(Paths.get(filePath));
        }
    }
    
    /**
     * ckeditor上传图片
     * 
     * @author PeiqiHuang
     * @date 2018年12月3日 上午9:50:15
     * @param file（param名为upload）
     * @param savePath
     * @return
     * @throws IOException
     */
    @RequestMapping("common/upload")
    @ResponseBody
    public ImgResponse uploadImg(@RequestParam("upload") MultipartFile file, String subDir) {
    	if (file.isEmpty()) {
            return ImgResponse.error("文件不能为空");
       }
       if (!checkImageFileType(file.getOriginalFilename())) {
            return ImgResponse.error("不允许的图片类型");
       }
       if (file.getSize() > IMG_MAX_SIZE) {
           return ImgResponse.error("图片不能超过2M");
       }
       String home = imageConfig.getUploadPath();
    	String path = imageConfig.getRoot() + "/" + subDir + "/";
        String fileName = UUIDUtils.generateUUID() + "."
                + FilenameUtils.getExtension(file.getOriginalFilename());
		try {
			FileUtils.saveFile(home + path, file, fileName);
		} catch (IOException e) {
			e.printStackTrace();
			return ImgResponse.error(e.getMessage());
		}
        return ImgResponse.ok(imageConfig.getAccessPath() + path + fileName);
    }
    
    /**
     * 图片类型判断
     *
     * @param fileName
     * @return
     */
    private boolean checkImageFileType(String fileName) {
        Iterator<String> type = Arrays.asList(this.imageFiles).iterator();
        while (type.hasNext()) {
            String ext = type.next();
            if (fileName.toLowerCase().endsWith(ext)) {
                return true;
            }
        }
        return false;
    }
}
