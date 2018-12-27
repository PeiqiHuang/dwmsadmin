package com.dwms.push.controller;

import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dwms.common.annotation.Log;
import com.dwms.common.controller.BaseController;
import com.dwms.common.domain.QueryRequest;
import com.dwms.common.domain.ResponseBo;
import com.dwms.push.domain.News;
import com.dwms.push.service.NewsService;

@Controller
public class NewsController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private NewsService newsService;
    
    @Log("党建要闻")
    @RequestMapping("news")
    @RequiresPermissions("news:list")
    public String index() {
        return "push/news/news";
    }

    @RequestMapping("news/list")
    @RequiresPermissions("news:list")
    @ResponseBody
    public Map<String, Object> list(QueryRequest request, News news) {
        return super.selectByPageNumSize(request, () -> this.newsService.findAllNews(news, request));
    }
    
    @Log("新增新闻")
    @RequiresPermissions("news:add")
    @RequestMapping("news/add")
    @ResponseBody
    public ResponseBo addNews(News news) {
        try {
            this.newsService.addNews(news);
            return ResponseBo.ok("新增新闻成功！");
        } catch (Exception e) {
            log.error("新增新闻失败", e);
            return ResponseBo.error("新增新闻失败，请联系网站管理员！");
        }
    }
    
    @Log("修改新闻")
    @RequiresPermissions("news:update")
    @RequestMapping("news/update")
    @ResponseBody
    public ResponseBo updateNews(News news) {
    	try {
    		this.newsService.updateNews(news);
    		return ResponseBo.ok("新增新闻成功！");
    	} catch (Exception e) {
    		log.error("新增新闻失败", e);
    		return ResponseBo.error("新增新闻失败，请联系网站管理员！");
    	}
    }
    
    @RequestMapping("news/getNews")
    @ResponseBody
    public ResponseBo getNews(Integer newsId) {
        try {
            News news = this.newsService.selectByKey(newsId);
            return ResponseBo.ok(news);
        } catch (Exception e) {
            log.error("获取新闻失败", e);
            return ResponseBo.error("获取新闻失败，请联系网站管理员！");
        }
    }
}
