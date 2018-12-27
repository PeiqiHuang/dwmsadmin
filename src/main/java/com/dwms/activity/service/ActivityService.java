package com.dwms.activity.service;

import java.util.List;

import com.dwms.activity.domain.Activity;
import com.dwms.activity.domain.vo.ActivityWithUser;
import com.dwms.common.domain.ResponseBo;
import com.dwms.common.service.IService;

public interface ActivityService extends IService<Activity> {

    List<Activity> findAllActivitys(Activity activity);

    ResponseBo addActivity(Activity activity, String[] users);
    
    ResponseBo updateActivity(Activity activity, String[] users);

    ResponseBo deleteActivitys(String ids);

	ActivityWithUser findById(Integer activityId);

}
