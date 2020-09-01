package com.cqu.crm.workbench.service.impl;


import com.cqu.crm.exception.ActivityException;
import com.cqu.crm.settings.domain.User;
import com.cqu.crm.utils.SqlSessionUtil;
import com.cqu.crm.vo.PageInotationVO;
import com.cqu.crm.workbench.dao.ActivityDao;
import com.cqu.crm.workbench.dao.ActivityRemarkDao;
import com.cqu.crm.workbench.domain.Activity;
import com.cqu.crm.workbench.service.ActivityService;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ActivityServiceImpl implements ActivityService {
    private ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
    private ActivityRemarkDao activityRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ActivityRemarkDao.class);


    @Override
    public void save(Activity activity) throws ActivityException {
        int count = activityDao.save(activity);

        if (count != 1) {
            throw new ActivityException("创建市场活动失败");
        }

    }

    @Override
    public PageInotationVO<Activity> pageList(Map<String, Object> map) throws ActivityException {

        Integer total = activityDao.countByCondition(map);    //  获取活动列表数量
        List<Activity> activityList = activityDao.getActivityListByCondition(map);

        if (total == null || activityList == null) {
            throw new ActivityException("查询活动列表失败");
        }
        //  封装数据至Vo
        PageInotationVO<Activity> pageInotationVO = new PageInotationVO<Activity>();
        pageInotationVO.setTotal(total);
        pageInotationVO.setActivityList(activityList);

        return pageInotationVO;
    }

    @Override
    public boolean deleteById(String[] ids) {
        boolean flag = true;    //  删除活动成功标志

        //  查询与活动相关的备注条数
        int relatedCount = activityRemarkDao.getCountByIds(ids);
        //  删除该活动关联的备注
        int deleteCount = activityRemarkDao.deleteByIds(ids);

        if (relatedCount != deleteCount) {
            flag = false;
        }

        //  删除该活动
        Integer count = activityDao.deleteActivities(ids);

        if (count != ids.length) {
            flag = false;
        }
        return flag;
    }

    @Override
    public Map<String, Object> updateById(String id) throws ActivityException {
        //  查询用户信息
        List<User> userList = activityDao.getUserList();
        //  根据id查询活动信息
        Activity activity = activityDao.getActivityById(id);

        if (userList == null || activity == null) {
            throw new ActivityException("更新失败");
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("uList", userList);
        map.put("activity", activity);
        return map;
    }

    @Override
    public boolean update(Activity activity) {
        boolean flag = true;

        int count = activityDao.update(activity);

        if (count != 1) {
            flag = false;
        }

        return flag;
    }

}
