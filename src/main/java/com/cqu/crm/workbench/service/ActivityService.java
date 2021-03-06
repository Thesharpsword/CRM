package com.cqu.crm.workbench.service;

import com.cqu.crm.exception.ActivityException;
import com.cqu.crm.vo.PageInotationVO;
import com.cqu.crm.workbench.domain.Activity;
import com.cqu.crm.workbench.domain.ActivityRemark;

import java.util.List;
import java.util.Map;

public interface ActivityService {
    void save(Activity activity) throws ActivityException;

    PageInotationVO<Activity> pageList(Map<String, Object> map) throws ActivityException;

    boolean deleteById(String[] ids) throws ActivityException;

    Map<String, Object> getUpdateInfo(String id) throws ActivityException;

    boolean update(Activity activity);

    Activity detail(String id) throws ActivityException;

    List<ActivityRemark> getActivityRemarkList(String id);

    boolean deleteRemark(String id);

    boolean saveRemark(ActivityRemark remark);

    boolean updateRemark(ActivityRemark remark);
}
