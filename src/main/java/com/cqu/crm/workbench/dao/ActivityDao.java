package com.cqu.crm.workbench.dao;

import com.cqu.crm.settings.domain.User;
import com.cqu.crm.vo.PageInotationVO;
import com.cqu.crm.workbench.domain.Activity;

import java.util.Enumeration;
import java.util.List;
import java.util.Map;

public interface ActivityDao {
    int save(Activity activity);

    Integer countByCondition(Map<String, Object> map);

    List<Activity> getActivityListByCondition(Map<String, Object> map);

    Integer deleteActivities(String[] ids);

    Activity getActivityById(String id);

    int update(Activity activity);

    Activity detail(String id);

    List<Activity> getActivityListByIds(List<String> activityList);

    List<Activity> getRelatedActivityByActivityName(Map<String, String> map);
}
