package com.dwms.birthday.controller;

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

import com.dwms.birthday.domain.Wish;
import com.dwms.birthday.domain.form.WishForm;
import com.dwms.birthday.domain.vo.WishVo;
import com.dwms.birthday.service.WishService;
import com.dwms.common.annotation.Log;
import com.dwms.common.controller.BaseController;
import com.dwms.common.domain.QueryRequest;
import com.dwms.common.domain.ResponseBo;
import com.dwms.common.util.FileUtils;

@Controller
public class WishController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WishService wishService;

    @Log("获取祝福语信息")
    @RequestMapping("wish")
    @RequiresPermissions("wish:list")
    public String index(Model model) {
        model.addAttribute("isAdmin", isAdmin());
        return "birthday/wish";
    }

    @RequestMapping("wish/getWish")
    @ResponseBody
    public ResponseBo getWish(Integer wishId) {
        try {
            WishVo wish = this.wishService.findById(wishId);
            return ResponseBo.ok(wish);
        } catch (Exception e) {
            log.error("获取祝福语信息失败", e);
            return ResponseBo.error("获取祝福语信息失败，请联系网站管理员！");
        }
    }

    @RequestMapping("wish/list")
    @RequiresPermissions("wish:list")
    @ResponseBody
    public Map<String, Object> list(QueryRequest request, WishForm wish) {
    	return super.selectByPageNumSize(request, () -> this.wishService.findAllWishs(wish));
    }

    @RequestMapping("wish/excel")
    @ResponseBody
    public ResponseBo wishExcel(WishForm wish) {
        try {
            List<WishVo> list = this.wishService.findAllWishs(wish);
            return FileUtils.createExcelByPOIKit("祝福语表", list, Wish.class);
        } catch (Exception e) {
            log.error("导出祝福语信息Excel失败", e);
            return ResponseBo.error("导出Excel失败，请联系网站管理员！");
        }
    }

    @RequestMapping("wish/csv")
    @ResponseBody
    public ResponseBo wishCsv(WishForm wish) {
        try {
            List<WishVo> list = this.wishService.findAllWishs(wish);
            return FileUtils.createCsv("祝福语表", list, Wish.class);
        } catch (Exception e) {
            log.error("获取祝福语信息Csv失败", e);
            return ResponseBo.error("导出Csv失败，请联系网站管理员！");
        }
    }
    
    @Log("新增祝福语")
    @RequiresPermissions("wish:add")
    @RequestMapping("wish/add")
    @ResponseBody
    public ResponseBo addWish(Wish wish) {
        try {
            this.wishService.addWish(wish);
            return ResponseBo.ok("新增祝福语成功！");
        } catch (Exception e) {
            log.error("新增祝福语失败", e);
            return ResponseBo.error("新增祝福语失败，请联系网站管理员！");
        }
    }
    
    @Log("修改祝福语")
    @RequiresPermissions("wish:update")
    @RequestMapping("wish/update")
    @ResponseBody
    public ResponseBo updateWishes(Wish wish) {
        try {
            this.wishService.updateWishes(wish);
            return ResponseBo.ok("修改祝福语成功！");
        } catch (Exception e) {
            log.error("修改祝福语失败", e);
            return ResponseBo.error("修改祝福语失败，请联系网站管理员！");
        }
    }
}
