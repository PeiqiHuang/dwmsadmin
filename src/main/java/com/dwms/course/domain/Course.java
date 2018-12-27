package com.dwms.course.domain;

import java.util.Date;

import javax.persistence.*;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonFormat;

@Table(name = "tb_course_info")
public class Course {
	
	/**
	 * 状态
	 */
	public static final int STATUS_DELETE = -9;
	public static final int STATUS_CANCEL = -1;
	public static final int STATUS_WAIT = 0;
	public static final int STATUS_VALID = 1;
	
    /**
     * 课程ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer courseId;

    /**
     * 党支部ID
     */
    private Integer partyId;

    /**
     * 课程分类ID
     */
    private Integer categoryId;
    
    @Transient
  	private String categoryName;

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 课程简介
     */
    private String courseDesc;

    /**
     * 课程封面
     */
    private String cover;
    
    /**
     * 课程详情顶部图片
     */
    private String banner;

    /**
     * 文件地址
     */
    private String filePath;

    /**
     * 状态 0选修 1必修
     */
    private Integer courseType;
    
    /**
     * 总课时
     */
    private Integer chapterNum;
    
    /**
     * 章节时长（分钟）
     */
    private Integer timeLength;
    
    /**
     * 状态 -9已删除 -1已取消 0待发布 1已发布
     */
    private Integer status;

    /**
     * 截止日期
     */
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date endTime;

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
  	private String timeField;
  	@Transient
  	private String beginField;
  	@Transient
  	private String endField;
  	
    /**
     * 获取课程ID
     *
     * @return courseId - 课程ID
     */
    public Integer getCourseId() {
        return courseId;
    }

    /**
     * 设置课程ID
     *
     * @param courseId 课程ID
     */
    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
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
     * 获取课程分类ID
     *
     * @return categoryId - 课程分类ID
     */
    public Integer getCategoryId() {
        return categoryId;
    }

    /**
     * 设置课程分类ID
     *
     * @param categoryId 课程分类ID
     */
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	/**
     * 获取课程名称
     *
     * @return courseName - 课程名称
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     * 设置课程名称
     *
     * @param courseName 课程名称
     */
    public void setCourseName(String courseName) {
        this.courseName = courseName == null ? null : courseName.trim();
    }

    /**
     * 获取课程简介
     *
     * @return courseDesc - 课程简介
     */
    public String getCourseDesc() {
        return courseDesc;
    }

    /**
     * 设置课程简介
     *
     * @param courseDesc 课程简介
     */
    public void setCourseDesc(String courseDesc) {
        this.courseDesc = courseDesc == null ? null : courseDesc.trim();
    }

    /**
     * 获取课程封面
     *
     * @return cover - 课程封面
     */
    public String getCover() {
        return cover;
    }

    /**
     * 设置课程封面
     *
     * @param cover 课程封面
     */
    public void setCover(String cover) {
        this.cover = cover == null ? null : cover.trim();
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    /**
     * 获取文件地址
     *
     * @return filePath - 文件地址
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * 设置文件地址
     *
     * @param filePath 文件地址
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath == null ? null : filePath.trim();
    }

    /**
     * 获取状态 0选修 1必修
     *
     * @return courseType - 状态 0选修 1必修
     */
    public Integer getCourseType() {
        return courseType;
    }

    /**
     * 设置状态 0选修 1必修
     *
     * @param courseType 状态 0选修 1必修
     */
    public void setCourseType(Integer courseType) {
        this.courseType = courseType;
    }

	public Integer getChapterNum() {
        return chapterNum;
    }

    public void setChapterNum(Integer chapterNum) {
        this.chapterNum = chapterNum;
    }

    /**
     * 获取章节时长（分钟）
     *
     * @return timeLength - 章节时长（分钟）
     */
    public Integer getTimeLength() {
        return timeLength;
    }

    /**
     * 设置章节时长（分钟）
     *
     * @param timeLength 章节时长（分钟）
     */
    public void setTimeLength(Integer timeLength) {
        this.timeLength = timeLength;
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
     * 获取截止日期
     *
     * @return endTime - 截止日期
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 设置截止日期
     *
     * @param endTime 截止日期
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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

	public String getTimeField() {
		return timeField;
	}

	public void setTimeField(String timeField) {
		this.timeField = timeField;
	}

    public String getBeginField() {
    	if (StringUtils.isNotBlank(timeField)) {
            String[] timeArr = timeField.split("~");
            return timeArr[0];
    	}
		return beginField;
	}

	public void setBeginField(String beginField) {
		this.beginField = beginField;
	}

	public String getEndField() {
		if (StringUtils.isNotBlank(timeField)) {
            String[] timeArr = timeField.split("~");
            return timeArr[1];
    	}
		return endField;
	}

	public void setEndField(String endField) {
		this.endField = endField;
	}

	@Override
    public String toString() {
        return "Course [courseId=" + courseId + ", partyId=" + partyId
                + ", categoryId=" + categoryId + ", courseName=" + courseName
                + ", courseDesc=" + courseDesc + ", cover=" + cover
                + ", filePath=" + filePath + ", courseType=" + courseType
                + ", chapterNum=" + chapterNum + ", timeLength=" + timeLength
                + ", status=" + status + ", endTime=" + endTime
                + ", releaseTime=" + releaseTime + ", createTime=" + createTime
                + "]";
    }

}