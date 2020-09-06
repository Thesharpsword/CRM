package com.cqu.crm.workbench.service.impl;

import com.cqu.crm.exception.ClueException;
import com.cqu.crm.settings.dao.UserDao;
import com.cqu.crm.settings.domain.User;
import com.cqu.crm.utils.SqlSessionUtil;
import com.cqu.crm.vo.PageInotationVO;
import com.cqu.crm.workbench.dao.ActivityDao;
import com.cqu.crm.workbench.dao.ClueActivityRelationDao;
import com.cqu.crm.workbench.dao.ClueDao;
import com.cqu.crm.workbench.domain.Activity;
import com.cqu.crm.workbench.domain.Clue;
import com.cqu.crm.workbench.service.ClueService;

import java.util.List;
import java.util.Map;

public class ClueServiceImpl implements ClueService {
    ClueDao clueDao = SqlSessionUtil.getSqlSession().getMapper(ClueDao.class);
    UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);
    ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
    ClueActivityRelationDao clueActivityRelationDao = SqlSessionUtil.getSqlSession().getMapper(ClueActivityRelationDao.class);

    @Override
    public List<User> getUserList() throws ClueException {
        List<User> userList = userDao.getUserList();
        if (userList == null) {
            throw new ClueException("获取用户列表失败");
        }
        return userList;
    }

    @Override
    public boolean addClue(Clue clue) {
        boolean flag = true;
        int count = clueDao.addClue(clue);
        if (count != 1) {
            flag = false;
        }
        return flag;
    }

    @Override
    public PageInotationVO<Clue> pageList(Map<String, Object> map) throws ClueException {
        PageInotationVO<Clue> vo = new PageInotationVO<Clue>();

        //  获取线索总条数
        Integer total = clueDao.getCount();

        //  获取条件查询匹配的线索
        List<Clue> clueList = clueDao.getClueListByCondition(map);

        if (total == null || clueList == null) {
            throw new ClueException("条件查询线索失败");
        }

        vo.setTotal(total);
        vo.setActivityList(clueList);
        return vo;
    }

    @Override
    public Clue detail(String id) throws ClueException {
        Clue clue = clueDao.detail(id);

        if (clue == null) {
            throw new ClueException("线索详情获取失败");
        }

        return clue;
    }

    @Override
    public List<Activity> getActivityListById(String clueId) throws ClueException {

        List<String> activityList = clueDao.getActivityIdListByClueId(clueId);

        List<Activity> activities = activityDao.getActivityListByIds(activityList);

        if (activityList == null || activities == null) {
            throw new ClueException("获取线索相关的市场活动失败");
        }

        return activities;
    }

    @Override
    public boolean deleteRelatedActivityById(String id) {
        boolean flag = true;
        int count = clueActivityRelationDao.deleteRelatedActivityById(id);
        if (count != 1) {
            flag = false;
        }
        return flag;
    }

    @Override
    public List<Activity> getRelatedActivityByActivityName(Map<String, String> map) throws ClueException {
        List<Activity> activityList = activityDao.getRelatedActivityByActivityName(map);

        if (activityList == null) {
            throw new ClueException("线索关联市场活动时获取市场活动列表失败");
        }
        return activityList;
    }


}
