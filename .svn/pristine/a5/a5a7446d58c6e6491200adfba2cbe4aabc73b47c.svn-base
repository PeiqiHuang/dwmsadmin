package com.dwms.album.domain;

import java.util.Date;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Table(name = "tb_album_info")
@ToString
public class Album {
    /**
     * 状态
     */
    public static final int STATUS_DELETE = -9;
    public static final int STATUS_CANCEL = -1;
    public static final int STATUS_WAIT = 0;
    public static final int STATUS_VALID = 1;
    
    /**
     * 相册id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer albumId;

    /**
     * 党支部ID
     */
    private Integer partyId;

    /**
     * 相册名称
     */
    private String albumName;

    /**
     * 相册描述
     */
    private String albumDesc;

    /**
     * 封面图片地址
     */
    private String cover;

    /**
     * 状态 -9已删除 -1已取消 0待发布 1已发布
     */
    private Integer status;

    /**
     * 发布时间
     */
    private Date releaseTime;

    /**
     * 创建时间
     */
    private Date createTime;
    
    // 用于搜索条件中的时间字段
    @Transient
    @Getter
    @Setter
    private String timeField;
    
    /**
     * 获取相册id
     *
     * @return albumId - 相册id
     */
    public Integer getAlbumId() {
        return albumId;
    }

    /**
     * 设置相册id
     *
     * @param albumId 相册id
     */
    public void setAlbumId(Integer albumId) {
        this.albumId = albumId;
    }

    /**
     * 获取党支部ID
     *
     * @return partyId - 党支部ID
     */
    public Integer getPartyId() {
        return partyId;
    }

    /**
     * 设置党支部ID
     *
     * @param partyId 党支部ID
     */
    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
    }

    /**
     * 获取相册名称
     *
     * @return albumName - 相册名称
     */
    public String getAlbumName() {
        return albumName;
    }

    /**
     * 设置相册名称
     *
     * @param albumName 相册名称
     */
    public void setAlbumName(String albumName) {
        this.albumName = albumName == null ? null : albumName.trim();
    }

    /**
     * 获取相册描述
     *
     * @return albumDesc - 相册描述
     */
    public String getAlbumDesc() {
        return albumDesc;
    }

    /**
     * 设置相册描述
     *
     * @param albumDesc 相册描述
     */
    public void setAlbumDesc(String albumDesc) {
        this.albumDesc = albumDesc == null ? null : albumDesc.trim();
    }

    /**
     * 获取封面图片地址
     *
     * @return cover - 封面图片地址
     */
    public String getCover() {
        return cover;
    }

    /**
     * 设置封面图片地址
     *
     * @param cover 封面图片地址
     */
    public void setCover(String cover) {
        this.cover = cover == null ? null : cover.trim();
    }

    /**
     * 获取状态 -9已删除 -1已取消 0待发布 1已发布
     *
     * @return status - 状态 -9已删除 -1已取消 0待发布 1已发布
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态 -9已删除 -1已取消 0待发布 1已发布
     *
     * @param status 状态 -9已删除 -1已取消 0待发布 1已发布
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取发布时间
     *
     * @return releaseTime - 发布时间
     */
    public Date getReleaseTime() {
        return releaseTime;
    }

    /**
     * 设置发布时间
     *
     * @param releaseTime 发布时间
     */
    public void setReleaseTime(Date releaseTime) {
        this.releaseTime = releaseTime;
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
}