package com.dwms.advert.controller;

import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dwms.advert.domain.Advert;
import com.dwms.advert.service.AdvertService;
import com.dwms.common.annotation.Log;
import com.dwms.common.controller.BaseController;
import com.dwms.common.domain.QueryRequest;
import com.dwms.common.domain.ResponseBo;

@Controller
public class AdvertController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AdvertService advertService;

    @RequestMapping("ad")
    @RequiresPermissions("advert:list")
    public String index(Model model) {
    	model.addAttribute("isAdmin", isAdmin());
        return "advert/advert";
    }
    
    @RequestMapping("ad/getAdvert")
    @ResponseBody
    public ResponseBo getAdvert(Integer adId) {
        try {
            Advert advert = this.advertService.findById(adId);
            return ResponseBo.ok(advert);
        } catch (Exception e) {
            log.error("获取广告失败", e);
            return ResponseBo.error("获取广告失败，请联系网站管理员！");
        }
    }

    @Log("获取广告信息")
    @RequestMapping("ad/list")
    @RequiresPermissions("advert:list")
    @ResponseBody
    public Map<String, Object> advertList(QueryRequest request, Advert advert) {
    	if (!isAdmin()) {
    		advert.setPartyId(getPartyId());
    	}
        return super.selectByPageNumSize(request, () -> this.advertService.findAllAdverts(advert));
    }

    @Log("新增广告")
    @RequiresPermissions("advert:add")
    @RequestMapping("ad/add")
    @ResponseBody
    public ResponseBo addAdvert(Advert advert, MultipartFile file) {
    	if (null == advert.getPartyId()) {
    		advert.setPartyId(getPartyId());
    	}
        try {
            advert.setCoverImg(file);
            return this.advertService.addAdvert(advert);
        } catch (Exception e) {
            log.error("新增广告失败", e);
            return ResponseBo.error("新增广告失败，请联系网站管理员！");
        }
    }
    
    @Log("修改广告")
    @RequiresPermissions("advert:update")
    @RequestMapping("ad/update")
    @ResponseBody
    public ResponseBo updateAdvert(Advert advert, MultipartFile file) {
        try {
            advert.setCoverImg(file);
            return this.advertService.updateAdvert(advert);
        } catch (Exception e) {
            log.error("修改广告失败", e);
            return ResponseBo.error("修改广告失败，请联系网站管理员！");
        }
    }
    
    @Log("删除广告")
    @RequiresPermissions("advert:delete")
    @RequestMapping("ad/delete")
    @ResponseBody
    public ResponseBo deleteAdvert(String ids) {
        try {
        	return this.advertService.deleteAdverts(ids);
        } catch (Exception e) {
            log.error("删除广告失败", e);
            return ResponseBo.error("删除广告失败，请联系网站管理员！");
        }
    }
    
}
