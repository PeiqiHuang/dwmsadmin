package com.dwms.course.domain;

import javax.persistence.*;

@Table(name = "tb_course_chapter")
public class CourseChapter {
    /**
     * 类型
     */
    public static final Integer CHAPTER_TYPE_CHAPTER = 1;
    public static final Integer CHAPTER_TYPE_SECTION = 2;
    
    /**
     * 课程章节ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer chapterId;

    /**
     * 父级章节ID 为节时才有值
     */
    private Integer parentId;

    /**
     * 课程ID
     */
    private Integer courseId;

    /**
     * 章节序号 0，1，2
     */
    private Integer chapterNo;

    /**
     * 标题
     */
    private String chapterTitle;

    /**
     * 章节类型 1章 2节
     */
    private Integer chapterType;

    /**
     * 章节时长（分钟）
     */
    private Integer timeLength;

    /**
     * 内容
     */
    private String content;

    /**
     * 获取课程章节ID
     *
     * @return chapterId - 课程章节ID
     */
    public Integer getChapterId() {
        return chapterId;
    }

    /**
     * 设置课程章节ID
     *
     * @param chapterId 课程章节ID
     */
    public void setChapterId(Integer chapterId) {
        this.chapterId = chapterId;
    }

    /**
     * 获取父级章节ID 为节时才有值
     *
     * @return parentId - 父级章节ID 为节时才有值
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * 设置父级章节ID 为节时才有值
     *
     * @param parentId 父级章节ID 为节时才有值
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

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
     * 获取章节序号 0，1，2
     *
     * @return chapterNo - 章节序号 0，1，2
     */
    public Integer getChapterNo() {
        return chapterNo;
    }

    /**
     * 设置章节序号 0，1，2
     *
     * @param chapterNo 章节序号 0，1，2
     */
    public void setChapterNo(Integer chapterNo) {
        this.chapterNo = chapterNo;
    }

    /**
     * 获取标题
     *
     * @return chapterTitle - 标题
     */
    public String getChapterTitle() {
        return chapterTitle;
    }

    /**
     * 设置标题
     *
     * @param chapterTitle 标题
     */
    public void setChapterTitle(String chapterTitle) {
        this.chapterTitle = chapterTitle == null ? null : chapterTitle.trim();
    }

    /**
     * 获取章节类型 1章 2节
     *
     * @return chapterType - 章节类型 1章 2节
     */
    public Integer getChapterType() {
        return chapterType;
    }

    /**
     * 设置章节类型 1章 2节
     *
     * @param chapterType 章节类型 1章 2节
     */
    public void setChapterType(Integer chapterType) {
        this.chapterType = chapterType;
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
     * 获取内容
     *
     * @return content - 内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置内容
     *
     * @param content 内容
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}