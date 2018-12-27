package com.dwms.meeting.domain;

import javax.persistence.*;

@Table(name = "tb_meeting_summary_user")
public class MtgSumUser {
    /**
     * 逻辑ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer msuId;

    /**
     * 会议纪要ID
     */
    private Integer sumId;

    /**
     * 会议参与党员
     */
    private String userId;

    /**
     * 获取逻辑ID
     *
     * @return msuId - 逻辑ID
     */
    public Integer getMsuId() {
        return msuId;
    }

    /**
     * 设置逻辑ID
     *
     * @param msuId 逻辑ID
     */
    public void setMsuId(Integer msuId) {
        this.msuId = msuId;
    }

    /**
     * 获取会议纪要ID
     *
     * @return sumId - 会议纪要ID
     */
    public Integer getSumId() {
        return sumId;
    }

    /**
     * 设置会议纪要ID
     *
     * @param sumId 会议纪要ID
     */
    public void setSumId(Integer sumId) {
        this.sumId = sumId;
    }

    /**
     * 获取会议参与党员
     *
     * @return userId - 会议参与党员
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置会议参与党员
     *
     * @param userId 会议参与党员
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }
}