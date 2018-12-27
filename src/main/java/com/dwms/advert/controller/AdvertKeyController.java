package com.dwms.advert.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dwms.advert.domain.AdvertKey;
import com.dwms.advert.service.AdvertKeyService;
import com.dwms.advert.service.AdvertService;
import com.dwms.common.annotation.Log;
import com.dwms.common.controller.BaseController;
import com.dwms.common.domain.QueryRequest;
import com.dwms.common.domain.ResponseBo;

@Controller
public class AdvertKeyController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AdvertKeyService adertKeyService;
    
    @Autowired
    private AdvertService advertService;
    
    
    @RequestMapping("adKey")
    @RequiresPermissions("advert:key")
    public String index(Model model) {
    	model.addAttribute("isAdmin", isAdmin());
    	
        return "advert/key/adKey";
    }
    
    @Log("获取广告位置")
    @RequestMapping("adKey/list")
    @RequiresPermissions("advert:key")
    @ResponseBody
    public Map<String, Object> adKeyList(QueryRequest request, AdvertKey adKey) {
        return super.selectByPageNumSize(request, () -> this.adertKeyService.findAll(adKey));
    }
    
    @RequestMapping("adKey/getAdKey")
    @ResponseBody
    public ResponseBo getAdKey(String adKey) {
        try {
            AdvertKey key = this.adertKeyService.findByKey(adKey);
            return ResponseBo.ok(key);
        } catch (Exception e) {
            log.error("获取广告位置失败", e);
            return ResponseBo.error("获取广告位置失败，请联系网站管理员！");
        }
    }
    
    @RequestMapping("adKey/getAdKeys")
    @ResponseBody
    public ResponseBo getAdKeys() {
        try {
            AdvertKey key = new AdvertKey();
            key.setStatus(AdvertKey.STATUS_VALID);
            List<AdvertKey> keys = this.adertKeyService.findAll(key);
            return ResponseBo.ok(keys);
        } catch (Exception e) {
            log.error("获取广告位置列表失败", e);
            return ResponseBo.error("获取广告位置列表失败，请联系网站管理员！");
        }
    }
    
    @Log("新增广告位置")
    @RequiresPermissions("advert:key")
    @RequestMapping("adKey/add")
    @ResponseBody
    public ResponseBo addAdKey(AdvertKey key) {
        try {
            return this.adertKeyService.addAdKey(key);
        } catch (Exception e) {
            log.error("新增广告位置失败", e);
            return ResponseBo.error("新增广告位置失败，请联系网站管理员！");
        }
    }

    @Log("修改广告位置")
    @RequiresPermissions("advert:key")
    @RequestMapping("adKey/update")
    @ResponseBody
    public ResponseBo updateAdKey(AdvertKey key) {
        try {
            return this.adertKeyService.updateAdKey(key);
        } catch (Exception e) {
            log.error("修改广告位置失败", e);
            return ResponseBo.error("修改广告位置失败，请联系网站管理员！");
        }
    }
    
    @Log("删除广告位置")
    @RequiresPermissions("advert:key")
    @RequestMapping("adKey/delete")
    @ResponseBody
    public ResponseBo deleteAdKey(String ids) {
        try {
            return this.adertKeyService.deleteAdKeys(ids);
        } catch (Exception e) {
            log.error("删除广告位置失败", e);
            return ResponseBo.error("删除广告位置失败，请联系网站管理员！");
        }
    }
}
