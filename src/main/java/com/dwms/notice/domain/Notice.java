package com.dwms.notice.domain;

import java.util.Date;

import javax.persistence.*;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Table(name = "tb_notice_info")
@Data
public class Notice {
    
    /**
     * 状态
     */
    public static final int STATUS_DELETE = -9;
    public static final int STATUS_CANCEL = -1;
    public static final int STATUS_WAIT = 0;
    public static final int STATUS_VALID = 1;
    
    /**
     * 类型
     */
    public static final int TYPE_TEXT = 1;
    public static final int TYPE_IMG = 2;
    
    /**
     * 公告ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ntiId;

    /**
     * 党支部ID
     */
    private Integer partyId;

    /**
     * 标题
     */
    private String title;

    /**
     * 副标题
     */
    private String subTitle;

    /**
     * 通告封面
     */
    private String cover;
    
    @Transient
    private MultipartFile img;

    /**
     * 类型 1文字消息 2图文消息
     */
    private Integer type;

    /**
     * 文件地址
     */
    private String filePath;

    /**
     * 操作人ID（后台管理员或者app用户）
     */
    private String operator;

    /**
     * 状态 -9已删除 -1已取消 0待发布 1已发布
     */
    private Integer status;

    /**
     * 发布时间
     */
    private Date releaseTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 创建时间
     */
    private Date createTime;
    
    // 编辑器内容
    @Transient
    private String content;
    
    // 用于搜索条件中的时间字段
    @Transient
    private String timeField;
}