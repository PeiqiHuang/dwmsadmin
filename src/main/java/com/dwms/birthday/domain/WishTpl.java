package com.dwms.birthday.domain;

import java.util.Date;
import javax.persistence.*;

@Table(name = "tb_birthday_wish_template")
public class WishTpl {
    /**
     * 模板ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tplId;

    /**
     * 党支部id
     */
    private Integer partyId;

    private String wishText;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 获取模板ID
     *
     * @return tplId - 模板ID
     */
    public Integer getTplId() {
        return tplId;
    }

    /**
     * 设置模板ID
     *
     * @param tplId 模板ID
     */
    public void setTplId(Integer tplId) {
        this.tplId = tplId;
    }

    /**
     * 获取党支部id
     *
     * @return partyId - 党支部id
     */
    public Integer getPartyId() {
        return partyId;
    }

    /**
     * 设置党支部id
     *
     * @param partyId 党支部id
     */
    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
    }

    /**
     * @return wishText
     */
    public String getWishText() {
        return wishText;
    }

    /**
     * @param wishText
     */
    public void setWishText(String wishText) {
        this.wishText = wishText == null ? null : wishText.trim();
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