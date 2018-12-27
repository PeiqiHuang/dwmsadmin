package com.dwms.album.domain;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.springframework.web.multipart.MultipartFile;

@Table(name = "tb_album_img")
@ToString
public class AlbumImg {
    /**
     * 相册图片id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer aiId;

    /**
     * 相册id
     */
    private Integer albumId;

    /**
     * 图片地址
     */
    private String imgPath;

    /**
     * 图片序号1 2 3...
     */
    private Integer aiNo;

    /**
     * 创建时间
     */
    private Date createTime;

    
    @Transient
    @Getter
    @Setter
    private MultipartFile[] imgs;//上传图片
    
    /**
     * 获取相册图片id
     *
     * @return aiId - 相册图片id
     */
    public Integer getAiId() {
        return aiId;
    }

    /**
     * 设置相册图片id
     *
     * @param aiId 相册图片id
     */
    public void setAiId(Integer aiId) {
        this.aiId = aiId;
    }

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
     * 获取图片地址
     *
     * @return imgPath - 图片地址
     */
    public String getImgPath() {
        return imgPath;
    }

    /**
     * 设置图片地址
     *
     * @param imgPath 图片地址
     */
    public void setImgPath(String imgPath) {
        this.imgPath = imgPath == null ? null : imgPath.trim();
    }

    /**
     * 获取图片序号1 2 3...
     *
     * @return aiNo - 图片序号1 2 3...
     */
    public Integer getAiNo() {
        return aiNo;
    }

    /**
     * 设置图片序号1 2 3...
     *
     * @param aiNo 图片序号1 2 3...
     */
    public void setAiNo(Integer aiNo) {
        this.aiNo = aiNo;
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((aiId == null) ? 0 : aiId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AlbumImg other = (AlbumImg) obj;
        if (aiId == null) {
            if (other.aiId != null)
                return false;
        } else if (!aiId.equals(other.aiId))
            return false;
        return true;
    }
    
    
}