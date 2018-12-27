package com.dwms.birthday.controller;

import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dwms.birthday.domain.WishTpl;
import com.dwms.birthday.service.impl.WishTplService;
import com.dwms.common.annotation.Log;
import com.dwms.common.controller.BaseController;
import com.dwms.common.domain.QueryRequest;
import com.dwms.common.domain.ResponseBo;

@Controller
public class WishTplController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WishTplService wishTplService;
    
    @RequestMapping("wishTpl")
    @RequiresPermissions("wishTpl:list")
    public String index(Model model) {
        model.addAttribute("isAdmin", isAdmin());
        return "birthday/template/wishTpl";
    }
    
    @Log("获取祝福语模板信息")
    @RequestMapping("wishTpl/list")
    @RequiresPermissions("wishTpl:list")
    @ResponseBody
    public Map<String, Object> wishTplList(QueryRequest request, WishTpl wishTpl) {
        if (!isAdmin()) {
            wishTpl.setPartyId(getPartyId());
        }
        return super.selectByPageNumSize(request, () -> this.wishTplService.findAll(wishTpl));
    }
    
    @RequestMapping("wishTpl/getWishTpl")
    @ResponseBody
    public ResponseBo getWishTpl(Integer tplId) {
        try {
            WishTpl wishTpl = this.wishTplService.selectByKey(tplId);
            return ResponseBo.ok(wishTpl);
        } catch (Exception e) {
            log.error("获取祝福语模板失败", e);
            return ResponseBo.error("获取祝福语模板失败，请联系网站管理员！");
        }
    }
    
    @Log("新增祝福语模板")
    @RequiresPermissions("wishTpl:add")
    @RequestMapping("wishTpl/add")
    @ResponseBody
    public ResponseBo addWishTpl(WishTpl wishTpl) {
        if (null == wishTpl.getPartyId()) {
            wishTpl.setPartyId(getPartyId());
        }
        try {
            return this.wishTplService.addWishTpl(wishTpl);
//            return ResponseBo.ok("新增祝福语模板成功！");
        } catch (Exception e) {
            log.error("新增祝福语模板失败", e);
            return ResponseBo.error("新增祝福语模板失败，请联系网站管理员！");
        }
    }

    @Log("修改祝福语模板")
    @RequiresPermissions("wishTpl:update")
    @RequestMapping("wishTpl/update")
    @ResponseBody
    public ResponseBo updateWishTpl(WishTpl wishTpl) {
        try {
            return this.wishTplService.updateWishTpl(wishTpl);
            //return ResponseBo.ok("修改祝福语模板成功！");
        } catch (Exception e) {
            log.error("修改祝福语模板失败", e);
            return ResponseBo.error("修改祝福语模板失败，请联系网站管理员！");
        }
    }
    
    @Log("删除祝福语模板")
    @RequiresPermissions("wishTpl:delete")
    @RequestMapping("wishTpl/delete")
    @ResponseBody
    public ResponseBo deleteSum(String ids) {
        try {
        	return this.wishTplService.deleteWishTpls(ids);
        } catch (Exception e) {
            log.error("删除祝福语模板失败", e);
            return ResponseBo.error("删除祝福语模板失败，请联系网站管理员！");
        }
    }

}
