package com.dwms.push.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.dwms.common.domain.QueryRequest;
import com.dwms.common.service.impl.BaseService;
import com.dwms.push.domain.News;
import com.dwms.push.service.NewsService;
import com.dwms.system.domain.Dict;

@Service("newsService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class NewsServiceImpl extends BaseService<News> implements NewsService {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public List<News> findAllNews(News news, QueryRequest request) {
		try {
			Example example = new Example(Dict.class);
			Criteria criteria = example.createCriteria();
			if (StringUtils.isNotBlank(news.getTitle())) {
				criteria.andCondition("title like", "%" + news.getTitle() + "%");
			}
			example.setOrderByClause("createTime");
			return this.selectByExample(example);
		} catch (Exception e) {
			log.error("获取党建要闻失败", e);
			return new ArrayList<>();
		}
	}

	@Override
	public void addNews(News news) {
		news.setCreateTime(new Date());
		this.save(news);
	}

	@Override
	public void updateNews(News news) {
		this.updateNotNull(news);
	}

}
