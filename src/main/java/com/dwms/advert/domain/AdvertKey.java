package com.dwms.advert.domain;

import java.util.Date;

import javax.persistence.*;

import lombok.Data;

@Table(name = "tb_advert_key")
@Data
public class AdvertKey {
    /**
     * 状态
     */
    public static final int STATUS_VALID = 1;
    public static final int STATUS_LOCK = 0;
    
    /**
     * 广告位置key
     */
    @Id
    private String adKey;

    /**
     * 广告位置名称
     */
    private String keyName;

    /**
     * 状态 0禁用 1启用
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

}