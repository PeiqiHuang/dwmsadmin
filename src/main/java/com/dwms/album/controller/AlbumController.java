package com.dwms.album.controller;

import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dwms.album.domain.Album;
import com.dwms.album.service.AlbumService;
import com.dwms.common.annotation.Log;
import com.dwms.common.controller.BaseController;
import com.dwms.common.domain.QueryRequest;
import com.dwms.common.domain.ResponseBo;

@Controller
public class AlbumController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AlbumService albumService;

    @RequestMapping("album")
    @RequiresPermissions("album:list")
    public String index(Model model) {
    	model.addAttribute("isAdmin", isAdmin());
        return "album/album";
    }
    
    @RequestMapping("album/getAlbum")
    @ResponseBody
    public ResponseBo getAlbum(Integer albumId) {
        try {
            Album album = this.albumService.findById(albumId);
            return ResponseBo.ok(album);
        } catch (Exception e) {
            log.error("获取相册失败", e);
            return ResponseBo.error("获取相册失败，请联系网站管理员！");
        }
    }

    @Log("获取相册信息")
    @RequestMapping("album/list")
    @RequiresPermissions("album:list")
    @ResponseBody
    public Map<String, Object> albumList(QueryRequest request, Album album) {
    	if (!isAdmin()) {
    		album.setPartyId(getPartyId());
    	}
        return super.selectByPageNumSize(request, () -> this.albumService.findAllAlbums(album));
    }

    @Log("新增相册")
    @RequiresPermissions("album:add")
    @RequestMapping("album/add")
    @ResponseBody
    public ResponseBo addAlbum(Album album) {
    	if (null == album.getPartyId()) {
    		album.setPartyId(getPartyId());
    	}
        try {
            return this.albumService.addAlbum(album);
        } catch (Exception e) {
            log.error("新增相册失败", e);
            return ResponseBo.error("新增相册失败，请联系网站管理员！");
        }
    }
    
    @Log("修改相册")
    @RequiresPermissions("album:update")
    @RequestMapping("album/update")
    @ResponseBody
    public ResponseBo updateAlbum(Album album) {
        try {
            return this.albumService.updateAlbum(album);
        } catch (Exception e) {
            log.error("修改相册失败", e);
            return ResponseBo.error("修改相册失败，请联系网站管理员！");
        }
    }
    
    @Log("删除相册")
    @RequiresPermissions("album:delete")
    @RequestMapping("album/delete")
    @ResponseBody
    public ResponseBo deleteAlbum(String ids) {
        try {
        	return this.albumService.deleteAlbums(ids);
        } catch (Exception e) {
            log.error("删除相册失败", e);
            return ResponseBo.error("删除相册失败，请联系网站管理员！");
        }
    }
    
}
