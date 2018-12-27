package com.dwms.push.service;

import java.util.List;

import com.dwms.common.domain.QueryRequest;
import com.dwms.common.service.IService;
import com.dwms.push.domain.News;

public interface NewsService extends IService<News> {

	List<News> findAllNews(News news, QueryRequest request);

	void addNews(News news);

	void updateNews(News news);
}
