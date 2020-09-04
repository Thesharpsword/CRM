package com.cqu.crm.workbench.dao;

import com.cqu.crm.workbench.domain.ActivityRemark;

import java.util.List;

public interface ActivityRemarkDao {
    int getCountByIds(String[] ids);

    int deleteByIds(String[] ids);

    List<ActivityRemark> getActivityRemarkListByAid(String id);

    int deleteById(String id);

    int saveRemark(ActivityRemark remark);

    int updateRemark(ActivityRemark remark);
}
