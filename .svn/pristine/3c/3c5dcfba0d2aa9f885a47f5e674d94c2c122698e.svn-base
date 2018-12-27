package com.dwms.album.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dwms.album.domain.Album;
import com.dwms.album.domain.AlbumImg;
import com.dwms.album.domain.AlbumImgForm;
import com.dwms.album.service.AlbumImgService;
import com.dwms.album.service.AlbumService;
import com.dwms.common.annotation.Log;
import com.dwms.common.controller.BaseController;
import com.dwms.common.domain.ResponseBo;

@Controller
public class AlbumImgController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AlbumImgService albumImgService;
    
    @Autowired
    private AlbumService albumService;
    
    @RequestMapping("albumImg")
    @RequiresPermissions("album:img")
    public String index(Model model, Integer albumId) {
    	//model.addAttribute("albumId", albumId);
    	Album a = this.albumService.findById(albumId);
    	model.addAttribute("album", a);
    	
        return "album/img/img";
    }
    
    @Log("获取相册图片信息")
    @RequestMapping("albumImg/list")
    @RequiresPermissions("album:img")
    @ResponseBody
    public ResponseBo list(AlbumImg ai) {
        return ResponseBo.ok(this.albumImgService.findImgs(ai));
    }
    
    @RequestMapping("albumImg/move")
    @RequiresPermissions("album:img")
    @ResponseBody
    public ResponseBo move(AlbumImgForm form) {
        return this.albumImgService.move(form);
    }
    
    @RequestMapping("albumImg/cover")
    @RequiresPermissions("album:img")
    @ResponseBody
    public ResponseBo setCover(Integer aiId) {
        return this.albumImgService.setCover(aiId);
    }
    
    @Log("新增相册图片")
    @RequestMapping("albumImg/add")
    @RequiresPermissions("album:img")
    @ResponseBody
    public ResponseBo addAlbumImg(AlbumImg img) {
        try {
            return this.albumImgService.addImg(img);
        } catch (Exception e) {
            log.error("新增相册图片失败", e);
            return ResponseBo.error("新增相册图片失败，请联系网站管理员！");
        }
    }
    
    @Log("删除相册图片")
    @RequestMapping("albumImg/delete")
    @RequiresPermissions("album:img")
    @ResponseBody
    public ResponseBo deleteAlbumImg(String ids) {
        try {
            return this.albumImgService.deleteImgs(ids);
        } catch (Exception e) {
            log.error("删除相册图片失败", e);
            return ResponseBo.error("删除相册图片失败，请联系网站管理员！");
        }
    }
}
