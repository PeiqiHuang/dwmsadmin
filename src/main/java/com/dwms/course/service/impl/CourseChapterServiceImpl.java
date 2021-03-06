package com.dwms.course.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.dwms.common.domain.Tree;
import com.dwms.common.service.impl.BaseService;
import com.dwms.common.util.TreeUtils;
import com.dwms.course.dao.CourseChapterMapper;
import com.dwms.course.dao.CourseMapper;
import com.dwms.course.dao.CourseUserMapper;
import com.dwms.course.domain.Course;
import com.dwms.course.domain.CourseChapter;
import com.dwms.course.domain.CourseUser;
import com.dwms.course.service.CourseChapterService;

@Service("courseChapterService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class CourseChapterServiceImpl extends BaseService<CourseChapter> implements CourseChapterService {

    private Logger log = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private CourseMapper courseMapper;
    
    @Autowired
    private CourseChapterMapper courseChapterMapper;

    @Autowired
    private CourseUserMapper courseUserMapper;

    @Override
    public List<CourseChapter> findAll(CourseChapter chapter) {
        try {
            Example example = new Example(CourseChapter.class);
            Criteria criteria = example.createCriteria();
            if (null != chapter.getCourseId()) {
                criteria.andCondition("courseId = ", chapter.getCourseId());
            }
            if (null != chapter.getParentId()) {
                criteria.andCondition("parentId = ", chapter.getParentId());
            }
            if (null != chapter.getChapterType()) {
                criteria.andCondition("chapterType = ", chapter.getChapterType());
            }
            if (StringUtils.isNotBlank(chapter.getChapterTitle())) {
                criteria.andCondition("chapterTitle like ", "%" + chapter.getChapterTitle() + "%");
            }
            example.setOrderByClause("chapterNo asc");
            List<CourseChapter> list = this.selectByExample(example);
            return list;
        } catch (Exception e) {
            log.error("获取党课章节列表失败", e);
            return new ArrayList<>();
        }
    }

    @Override
    public Tree<CourseChapter> getChapterTree() {
        List<Tree<CourseChapter>> trees = new ArrayList<>();
        CourseChapter c = new CourseChapter();
        c.setChapterType(CourseChapter.CHAPTER_TYPE_CHAPTER);//只获取章
        List<CourseChapter> chapters = this.findAll(c);
        chapters.forEach(chapter -> {
            Tree<CourseChapter> tree = new Tree<>();
            tree.setId(chapter.getChapterId().toString());
            tree.setParentId(chapter.getParentId().toString());
            tree.setText(chapter.getChapterTitle());
            trees.add(tree);
        });
        return TreeUtils.build(trees);
    }

    @Override
    @Transactional
    public void addChapter(CourseChapter c) {
        if (c.getChapterType() == CourseChapter.CHAPTER_TYPE_CHAPTER) {
            c.setParentId(0);
        }
        this.courseChapterMapper.insertSelective(c);
        if(c.getChapterType() == CourseChapter.CHAPTER_TYPE_CHAPTER) {
            //更新章总时长
            updateChapterTimeLength(c.getChapterId());
        } else {
            //更新节的上级章总时长
            updateChapterTimeLength(c.getParentId());
        }
    }

    @Override
    @Transactional
    public void updateChapter(CourseChapter c) {
        if (c.getChapterType() == CourseChapter.CHAPTER_TYPE_CHAPTER) {
            c.setParentId(0);
        }
        this.updateNotNull(c);
        if(c.getChapterType() == CourseChapter.CHAPTER_TYPE_CHAPTER) {
            //更新章总时长
            updateChapterTimeLength(c.getChapterId());
        } else {
            //更新节的上级章总时长
            updateChapterTimeLength(c.getParentId());
        }
    }

    @Override
    @Transactional
    public void deleteChapters(String ids) {
        List<String> list = Arrays.asList(ids.split(","));
        Integer courseId = null;
        if (!list.isEmpty()) {
            CourseChapter c = this.selectByKey(list.get(0));
            courseId = c.getCourseId();
        }
        this.batchDelete(list, "chapterId", CourseChapter.class);
        //章删掉了节也一起删掉
        Example example = new Example(CourseChapter.class);
        example.createCriteria().andIn("parentId", list);
        this.courseChapterMapper.deleteByExample(example);
        
        //更新所有章和课程的总时长
        if (null != courseId) {
            updateCourseAndAllChapters(courseId);
        }
    }
    
    /**
     * 重新计算所有章和课程的时长
     * 
     * @author PeiqiHuang
     * @date 2018年12月6日 下午4:38:32
     * @param courseId
     */
    @Override
    public void updateCourseAndAllChapters(Integer courseId) {
        CourseChapter chapter = new CourseChapter();
        chapter.setCourseId(courseId);
        List<CourseChapter> chapters = this.findAll(chapter);
        for (CourseChapter c : chapters) {
            if (c.getChapterType() == CourseChapter.CHAPTER_TYPE_CHAPTER) {
                updateChapterTimeLength(c.getChapterId(), false);
            }
        }
        updateCourseNumAndTime(courseId);
    }
    
    /**
     * 重新计算一个章的时长
     * 
     * 
     * @author PeiqiHuang
     * @date 2018年12月7日 上午11:27:11
     * @param chapterId
     * @param courseCheck 重新计算课程的时长和课时
     */
    private void updateChapterTimeLength(Integer chapterId, boolean courseCheck) {
        CourseChapter c = this.selectByKey(chapterId);
        if (c.getChapterType() == CourseChapter.CHAPTER_TYPE_CHAPTER) {
            Integer oldTimeLength = c.getTimeLength();
            CourseChapter chapter = new CourseChapter();
            chapter.setParentId(chapterId);
            List<CourseChapter> subs = this.findAll(chapter);
            int timeLength = 0;
            for (CourseChapter s : subs) {
                timeLength += s.getTimeLength();
            }
            if (timeLength == 0) {//章没有节时timeLength为0
                timeLength = c.getTimeLength();
            }
            if (oldTimeLength != timeLength) {
                c.setTimeLength(timeLength);
                this.updateNotNull(c);
            }
            if (courseCheck) {// 重新计算课程总时长，总课时
                //Integer newTimeOffset = timeLength - oldTimeLength;
                updateCourseNumAndTime(c.getCourseId());
            }
        }
    }
    
    private void updateChapterTimeLength(Integer chapterId) {
        updateChapterTimeLength(chapterId, true);
    }
    
    /**
     * 重新计算课程总课时
     * 
     * @author PeiqiHuang
     * @date 2018年12月7日 上午10:38:25
     * @param courseId
     * @param newTimeOffset 
     */
    private void updateCourseNumAndTime(Integer courseId) {
        Course course = this.courseMapper.selectByPrimaryKey(courseId);
        
        // 总时长timeLength
        int timeLength = 0;
        CourseChapter chapter = new CourseChapter();
        chapter.setCourseId(courseId);
        chapter.setChapterType(CourseChapter.CHAPTER_TYPE_CHAPTER);
        List<CourseChapter> chapters = this.findAll(chapter);//所有章
        for (CourseChapter c : chapters) {
            timeLength += c.getTimeLength();
        }
        course.setTimeLength(timeLength);
        
        // 总课时chapterNum
        int chapterNum = this.courseChapterMapper.getChapterNum(courseId);
        course.setChapterNum(chapterNum);
        
        this.courseMapper.updateByPrimaryKey(course);
        
		//更新课程所有参与人员的剩余课时
        CourseUser courseUser = new CourseUser();
        courseUser.setRestChapter(chapterNum);
        Example example = new Example(CourseUser.class);
        example.createCriteria().andCondition("courseId = ", courseId);
        this.courseUserMapper.updateByExampleSelective(courseUser, example);
    }
}
