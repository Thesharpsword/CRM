package com.cqu.crm.workbench.web.controller;

import com.cqu.crm.exception.ActivityException;
import com.cqu.crm.exception.LoginException;
import com.cqu.crm.settings.domain.User;
import com.cqu.crm.settings.service.UserService;
import com.cqu.crm.settings.service.impl.UserServiceImpl;
import com.cqu.crm.utils.DateTimeUtil;
import com.cqu.crm.utils.PrintJson;
import com.cqu.crm.utils.ServiceFactory;
import com.cqu.crm.utils.UUIDUtil;
import com.cqu.crm.vo.PageInotationVO;
import com.cqu.crm.workbench.domain.Activity;
import com.cqu.crm.workbench.domain.ActivityRemark;
import com.cqu.crm.workbench.service.ActivityService;
import com.cqu.crm.workbench.service.impl.ActivityServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("进入到市场活动控制器");

        String path = request.getServletPath();

        if ("/workbench/activity/getUserList.do".equals(path)) {
            getUserList(request, response);
        } else if ("/workbench/activity/save.do".equals(path)) {
            save(request, response);
        } else if ("/workbench/activity/getActivityList.do".equals(path)) {
            getActivityList(request, response);
        } else if ("/workbench/activity/delete.do".equals(path)) {
            deleteById(request, response);
        } else if ("/workbench/activity/updateQuery.do".equals(path)) {
            updateQuery(request, response);
        } else if ("/workbench/activity/update.do".equals(path)) {
            update(request, response);
        } else if ("/workbench/activity/detail.do".equals(path)) {
            detail(request, response);
        } else if ("/workbench/activity/getActivityRemarkList.do".equals(path)) {
            getActivityRemarkList(request, response);
        } else if ("/workbench/activity/deleteRemark.do".equals(path)) {
            deleteRemark(request, response);
        } else if ("/workbench/activity/saveRemark.do".equals(path)) {
            saveRemark(request, response);
        } else if ("/workbench/activity/updateRemark.do".equals(path)) {
            updateRemark(request, response);
        }
    }

    private void updateRemark(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入到市场活动备注修改方法");

        String noteContent = request.getParameter("updateRemark");
        String editTime = DateTimeUtil.getSysTime();
        String editBy =request.getParameter("editBy");
        String editFlag = "1";
        String id = request.getParameter("id");

        ActivityRemark remark = new ActivityRemark();

        remark.setNoteContent(noteContent);
        remark.setEditTime(editTime);
        remark.setEditBy(editBy);
        remark.setEditFlag(editFlag);
        remark.setId(id);

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        boolean flag = as.updateRemark(remark);

        if (flag) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("success", true);
            map.put("ar", remark);
            PrintJson.printJsonObj(response, map);
        } else {
            PrintJson.printJsonFlag(response, false);
        }
    }

    private void saveRemark(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入到市场活动备注添加方法");

        String id = UUIDUtil.getUUID();
        String noteContent = request.getParameter("noteContent");
        String createTime = DateTimeUtil.getSysTime();
        String createBy = request.getParameter("createBy");
        String editFlag = "0";
        String activityId = request.getParameter("activityId");

        ActivityRemark remark = new ActivityRemark();

        remark.setId(id);
        remark.setNoteContent(noteContent);
        remark.setCreateTime(createTime);
        remark.setCreateBy(createBy);
        remark.setEditFlag(editFlag);
        remark.setActivityId(activityId);

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        boolean flag = as.saveRemark(remark);

        if (flag) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("success", true);
            map.put("ar", remark);
            PrintJson.printJsonObj(response, map);
        } else {
            PrintJson.printJsonFlag(response, false);
        }
    }

    private void deleteRemark(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入到市场活动备注删除方法");
        //  获取备注id
        String id = request.getParameter("remarkId");

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        boolean flag = as.deleteRemark(id);

        if (flag) {
            PrintJson.printJsonFlag(response, flag);
        } else {
            PrintJson.printJsonFlag(response, flag);
        }
    }

    private void getActivityRemarkList(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入到市场活动备注方法");

        String id = request.getParameter("activityId");
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        List<ActivityRemark> remarkList = as.getActivityRemarkList(id);

        PrintJson.printJsonObj(response, remarkList);
    }

    private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入到市场活动详情方法");

        String id = request.getParameter("id");
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        try {
            Activity a = as.detail(id);
            request.setAttribute("a", a);
            //  请求转发至detail.jsp
            request.getRequestDispatcher("detail.jsp").forward(request, response);

        } catch (ActivityException e) {
            e.printStackTrace();
        }
    }

    private void update(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入到市场活动修改方法");

        //  获取修改信息
        String id = request.getParameter("id");
        String owner = request.getParameter("owner");
        String name = request.getParameter("name");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String cost = request.getParameter("cost");
        String description = request.getParameter("description");
        String editTime = DateTimeUtil.getSysTime();
        String editBy = request.getParameter("editBy");

        Activity activity = new Activity();
        activity.setId(id);
        activity.setOwner(owner);
        activity.setName(name);
        activity.setStartDate(startDate);
        activity.setEndDate(endDate);
        activity.setCost(cost);
        activity.setDescription(description);
        activity.setEditTime(editTime);
        activity.setEditBy(editBy);

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        boolean flag = as.update(activity);
        if (flag) {
            PrintJson.printJsonFlag(response, true);
        } else {
            PrintJson.printJsonFlag(response, false);
        }
    }

    private void updateQuery(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入到市场活动修改前查询方法");

        //  获取id
        String id = request.getParameter("id");
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        try {
            Map<String, Object> map = as.getUpdateInfo(id);
            PrintJson.printJsonObj(response, map);
        } catch (ActivityException e) {
            e.printStackTrace();
        }

    }

    private void deleteById(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入到市场活动删除方法");

        //  获取id参数信息
        String ids[] = request.getParameterValues("id");

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        try {
            boolean flag = as.deleteById(ids);
            PrintJson.printJsonFlag(response, flag);
        } catch (ActivityException e) {
            e.printStackTrace();
            String msg = e.getMessage();
            PrintJson.printJsonFlag(response, false);
        }

    }

    private void getActivityList(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入到市场活动列表查询方法（结合条件查询与分页查询）");

        String pageNoStr  = request.getParameter("pageNo");
        String pageSizeStr = request.getParameter("pageSize");
        String name  = request.getParameter("name");
        String owner = request.getParameter("owner");
        String startDate = request.getParameter("startDate");
        String endDate  = request.getParameter("endDate");

        //  计算分页数
        Integer pageNo = Integer.valueOf(pageNoStr);
        Integer pageSize = Integer.valueOf(pageSizeStr);
        Integer skipCount = (pageNo - 1) * pageSize;

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", name);
        map.put("owner", owner);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        map.put("skipCount", skipCount);
        map.put("pageSize", pageSize);

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        PageInotationVO<Activity> vo = null;
        try {
            vo = as.pageList(map);
        } catch (ActivityException e) {
            e.printStackTrace();
        }
        PrintJson.printJsonObj(response, vo);
    }

    private void save(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入到市场活动添加方法");

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        //  获取ajax传来的信息，并实例化Activity对象

        Activity activity = new Activity();
        activity.setId(UUIDUtil.getUUID());
        activity.setOwner(request.getParameter("owner"));
        activity.setName(request.getParameter("name"));
        activity.setStartDate(request.getParameter("startDate"));
        activity.setEndDate(request.getParameter("endDate"));
        activity.setCost(request.getParameter("cost"));
        activity.setDescription(request.getParameter("description"));
        activity.setCreateTime(DateTimeUtil.getSysTime());
        activity.setCreateBy(((User)request.getSession().getAttribute("user")).getName());

        try {
            as.save(activity);  //  返回值使用boolean
            PrintJson.printJsonFlag(response, true);

        } catch (ActivityException e) {
            e.printStackTrace();
            //  错误信息
            String msg = e.getMessage();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("success", false);
            map.put("msg", msg);

            PrintJson.printJsonObj(response, map);
        }
    }

    private void getUserList(HttpServletRequest request, HttpServletResponse response) {
        //  创建service对象，业务层开发统一使用代理类形态的接口对象
        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());

        //  获取数据库中所有用户信息
        List<User> userList = null;
        try {
            userList = us.getUserList();
            PrintJson.printJsonObj(response, userList);

        } catch (LoginException e) {
            e.printStackTrace();
        }
    }
}
