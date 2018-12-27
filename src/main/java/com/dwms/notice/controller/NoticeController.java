package com.dwms.notice.controller;

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

import com.dwms.common.annotation.Log;
import com.dwms.common.controller.BaseController;
import com.dwms.common.domain.QueryRequest;
import com.dwms.common.domain.ResponseBo;
import com.dwms.notice.domain.Notice;
import com.dwms.notice.service.NoticeService;

@Controller
public class NoticeController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private NoticeService noticeService;

    @RequestMapping("notice")
    @RequiresPermissions("notice:list")
    public String index(Model model) {
    	model.addAttribute("isAdmin", isAdmin());
        return "notice/notice";
    }
    
    @RequestMapping("notice/getNotice")
    @ResponseBody
    public ResponseBo getNotice(Integer ntiId) {
        try {
            Notice notice = this.noticeService.findById(ntiId);
            return ResponseBo.ok(notice);
        } catch (Exception e) {
            log.error("获取通知失败", e);
            return ResponseBo.error("获取通知失败，请联系网站管理员！");
        }
    }

    @Log("获取通知信息")
    @RequestMapping("notice/list")
    @RequiresPermissions("notice:list")
    @ResponseBody
    public Map<String, Object> noticeList(QueryRequest request, Notice notice) {
    	if (!isAdmin()) {
    		notice.setPartyId(getPartyId());
    	}
        return super.selectByPageNumSize(request, () -> this.noticeService.findAllNotices(notice));
    }

    @Log("新增通知")
    @RequiresPermissions("notice:add")
    @RequestMapping("notice/add")
    @ResponseBody
    public ResponseBo addNotice(Notice notice, MultipartFile coverImg) {
    	if (null == notice.getPartyId()) {
    		notice.setPartyId(getPartyId());
    	}
        try {
            notice.setImg(coverImg);
            return this.noticeService.addNotice(notice);
        } catch (Exception e) {
            log.error("新增通知失败", e);
            return ResponseBo.error("新增通知失败，请联系网站管理员！");
        }
    }
    
    @Log("修改通知")
    @RequiresPermissions("notice:update")
    @RequestMapping("notice/update")
    @ResponseBody
    public ResponseBo updateNotice(Notice notice, MultipartFile coverImg) {
        try {
            notice.setImg(coverImg);
            return this.noticeService.updateNotice(notice);
        } catch (Exception e) {
            log.error("修改通知失败", e);
            return ResponseBo.error("修改通知失败，请联系网站管理员！");
        }
    }
    
    @Log("删除通知")
    @RequiresPermissions("notice:delete")
    @RequestMapping("notice/delete")
    @ResponseBody
    public ResponseBo deleteNotice(String ids) {
        try {
        	return this.noticeService.deleteNotices(ids);
        } catch (Exception e) {
            log.error("删除通知失败", e);
            return ResponseBo.error("删除通知失败，请联系网站管理员！");
        }
    }
    
}
