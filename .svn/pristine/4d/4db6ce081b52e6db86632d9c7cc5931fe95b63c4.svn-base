package com.dwms.push.domain;

import java.util.Date;
import javax.persistence.*;

@Table(name = "tb_news_info")
public class News {
    /**
     * 新闻ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer newsId;

    /**
     * 标题
     */
    private String title;

    /**
     * 状态 0 未发布 1 发布 -1 已删除
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 详情正文
     */
    private String content;

    /**
     * 获取新闻ID
     *
     * @return newsId - 新闻ID
     */
    public Integer getNewsId() {
        return newsId;
    }

    /**
     * 设置新闻ID
     *
     * @param newsId 新闻ID
     */
    public void setNewsId(Integer newsId) {
        this.newsId = newsId;
    }

    /**
     * 获取标题
     *
     * @return title - 标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置标题
     *
     * @param title 标题
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * 获取状态 0 未发布 1 发布 -1 已删除
     *
     * @return status - 状态 0 未发布 1 发布 -1 已删除
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态 0 未发布 1 发布 -1 已删除
     *
     * @param status 状态 0 未发布 1 发布 -1 已删除
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取创建时间
     *
     * @return createTime - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取详情正文
     *
     * @return content - 详情正文
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置详情正文
     *
     * @param content 详情正文
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

	@Override
	public String toString() {
		return "News [newsId=" + newsId + ", title=" + title + ", status="
				+ status + ", createTime=" + createTime + ", content="
				+ content + "]";
	}
}