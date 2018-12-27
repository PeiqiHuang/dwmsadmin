package com.dwms.system.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dwms.common.annotation.Log;
import com.dwms.common.controller.BaseController;
import com.dwms.common.domain.ResponseBo;
import com.dwms.common.domain.Tree;
import com.dwms.common.util.FileUtils;
import com.dwms.system.domain.Party;
import com.dwms.system.service.PartyService;

@Controller
public class PartyController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PartyService partyService;

    @Log("获取党支部信息")
    @RequestMapping("party")
    @RequiresPermissions("party:list")
    public String index() {
        return "system/party/party";
    }

    @RequestMapping("party/tree")
    @ResponseBody
    public ResponseBo getPartyTree() {
        try {
            Tree<Party> tree = this.partyService.getPartyTree();
            return ResponseBo.ok(tree);
        } catch (Exception e) {
            log.error("获取党支部树失败", e);
            return ResponseBo.error("获取党支部树失败！");
        }
    }

    @RequestMapping("party/getParty")
    @ResponseBody
    public ResponseBo getParty(Integer partyId) {
        try {
            Party party = this.partyService.findById(partyId);
            return ResponseBo.ok(party);
        } catch (Exception e) {
            log.error("获取党支部信息失败", e);
            return ResponseBo.error("获取党支部信息失败，请联系网站管理员！");
        }
    }

    @RequestMapping("party/list")
    @RequiresPermissions("party:list")
    @ResponseBody
    /*public Map<String, Object> list(QueryRequest request, Party party) {
    	return super.selectByPageNumSize(request, () -> this.partyService.findAllPartys(party, request));
    }*/
    public List<Party> partyList(Party party) {
        try {
        	return this.partyService.findAllPartys(party);
        } catch (Exception e) {
            log.error("获取党支部列表失败", e);
            return new ArrayList<>();
        }
    }
    
    @RequestMapping("party/excel")
    @ResponseBody
    public ResponseBo partyExcel(Party party) {
        try {
            List<Party> list = this.partyService.findAllPartys(party);
            return FileUtils.createExcelByPOIKit("党支部表", list, Party.class);
        } catch (Exception e) {
            log.error("导出党支部信息Excel失败", e);
            return ResponseBo.error("导出Excel失败，请联系网站管理员！");
        }
    }

    @RequestMapping("party/csv")
    @ResponseBody
    public ResponseBo partyCsv(Party party) {
        try {
            List<Party> list = this.partyService.findAllPartys(party);
            return FileUtils.createCsv("党支部表", list, Party.class);
        } catch (Exception e) {
            log.error("获取党支部信息Csv失败", e);
            return ResponseBo.error("导出Csv失败，请联系网站管理员！");
        }
    }

    @RequestMapping("party/checkPartyName")
    @ResponseBody
    public boolean checkPartyName(String partyName, String oldPartyName) {
        if (StringUtils.isNotBlank(oldPartyName) && partyName.equalsIgnoreCase(oldPartyName)) {
            return true;
        }
        Party result = this.partyService.findByName(partyName);
        return result == null;
    }

    @Log("新增党支部 ")
    @RequiresPermissions("party:add")
    @RequestMapping("party/add")
    @ResponseBody
    public ResponseBo add(Party party) {
        try {
            this.partyService.addParty(party);
            return ResponseBo.ok("新增党支部成功！");
        } catch (Exception e) {
            log.error("新增党支部失败", e);
            return ResponseBo.error("新增党支部失败，请联系网站管理员！");
        }
    }

    /*@Log("删除党支部")
    @RequiresPermissions("party:delete")
    @RequestMapping("party/delete")
    @ResponseBody
    public ResponseBo deletePartys(String ids) {
        try {
            this.partyService.deletePartys(ids);
            return ResponseBo.ok("删除党支部成功！");
        } catch (Exception e) {
            log.error("删除党支部失败", e);
            return ResponseBo.error("删除党支部失败，请联系网站管理员！");
        }
    }*/

    @Log("修改党支部 ")
    @RequiresPermissions("party:update")
    @RequestMapping("party/update")
    @ResponseBody
    public ResponseBo update(Party party) {
        try {
            this.partyService.updateParty(party);
            return ResponseBo.ok("修改党支部成功！");
        } catch (Exception e) {
            log.error("修改党支部失败", e);
            return ResponseBo.error("修改党支部失败，请联系网站管理员！");
        }
    }

    
    /**
     * 获取所有党支部，搜索框选择
     * 
     * 
     * @author PeiqiHuang
     * @date 2018年11月27日 下午4:34:49
     * @return
     */
    @RequestMapping("party/all")
    @ResponseBody
    public List<Party> partyAll() {
    	try {
    		return this.partyService.findAllPartys(new Party());
    	} catch (Exception e) {
    		log.error("获取党支部列表失败", e);
    		return new ArrayList<>();
    	}
    }

}
