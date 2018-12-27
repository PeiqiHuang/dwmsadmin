package com.dwms.course.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dwms.common.service.impl.BaseService;
import com.dwms.course.dao.CourseUserMapper;
import com.dwms.course.domain.CourseUser;
import com.dwms.course.service.CourseUserService;

@Service("courseUserService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class CourseUserServiceImpl extends BaseService<CourseUser> implements CourseUserService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CourseUserMapper courseUserMapper;

    @Override
    public List<CourseUser> findAll(CourseUser courseUser) {
        return courseUserMapper.findAll(courseUser);
    }


}
