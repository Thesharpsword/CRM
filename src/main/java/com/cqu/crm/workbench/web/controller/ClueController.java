package com.cqu.crm.workbench.web.controller;

import com.cqu.crm.exception.ActivityException;
import com.cqu.crm.exception.ClueException;
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
import com.cqu.crm.workbench.domain.Clue;
import com.cqu.crm.workbench.service.ActivityService;
import com.cqu.crm.workbench.service.ClueService;
import com.cqu.crm.workbench.service.impl.ActivityServiceImpl;
import com.cqu.crm.workbench.service.impl.ClueServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClueController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("进入到线索活动控制器");

        String path = request.getServletPath();

        if ("/workbench/clue/getUserList.do".equals(path)) {
            getUserList(request, response);
        } else if ("/workbench/clue/addClue.do".equals(path)) {
            addClue(request, response);
        } else if ("/workbench/clue/getClueList.do".equals(path)) {
            getClueList(request, response);
        } else if ("/workbench/clue/detail.do".equals(path)) {
            detail(request, response);
        } else if ("/workbench/clue/loadRelatedActivity.do".equals(path)) {
            loadRelatedActivity(request, response);
        } else if ("/workbench/clue/unbund.do".equals(path)) {
            unbund(request, response);
        } else if ("/workbench/clue/getRelatedActivityByActivityName.do".equals(path)) {
            getRelatedActivityByActivityName(request, response);
        }
    }

    private void getRelatedActivityByActivityName(HttpServletRequest request, HttpServletResponse response) {
        String aname = request.getParameter("aname");
        String clueId = request.getParameter("clueId");

        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        Map<String, String> map = new HashMap<String, String>();
        map.put("aname", aname);
        map.put("clueId", clueId);

        List<Activity> activityList = null;
        try {
            activityList = cs.getRelatedActivityByActivityName(map);
        } catch (ClueException e) {
            e.printStackTrace();
        }

        PrintJson.printJsonObj(response, activityList);
    }

    private void unbund(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("解除线索关联市场活动方法");

        String id = request.getParameter("id");
        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        boolean flag = cs.deleteRelatedActivityById(id);

        if (flag) {
            PrintJson.printJsonFlag(response, true);
        } else {
            PrintJson.printJsonFlag(response, false);
        }
    }

    private void loadRelatedActivity(HttpServletRequest request, HttpServletResponse response) {        System.out.println("进入线索详情获取方法");
        System.out.println("进入线索关联市场活动获取方法");

        String clueId = request.getParameter("id");

        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        List<Activity> activityList = null;
        try {
            activityList = cs.getActivityListById(clueId);
            PrintJson.printJsonObj(response, activityList);
        } catch (ClueException e) {
            e.printStackTrace();
        }
    }

    private void detail(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入线索详情获取方法");

        //  获取线索id
        String id = request.getParameter("id");

        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());


        try {
            Clue clue = cs.detail(id);
            request.setAttribute("clue", clue);
            request.getRequestDispatcher("detail.jsp").forward(request, response);

        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClueException e) {
            e.printStackTrace();
        }
    }

    private void getClueList(HttpServletRequest request, HttpServletResponse response) {
        String pageNoStr = request.getParameter("pageNo");
        String pageSizeStr = request.getParameter("pageSize");
        String fullname = request.getParameter("fullname");
        String company = request.getParameter("company");
        String phone  = request.getParameter("phone");
        String source = request.getParameter("source");
        String owner  = request.getParameter("owner");
        String mphone  = request.getParameter("mphone");

        int pageNo = Integer.parseInt(pageNoStr);
        int pageSize = Integer.parseInt(pageSizeStr);

        Integer skipPage = (pageNo - 1) * pageSize;

        Map<String , Object> map = new HashMap<String, Object>();

        map.put("pageSize", pageSize);
        map.put("skipPage", skipPage);
        map.put("fullname", fullname);
        map.put("company", company);
        map.put("phone", phone);
        map.put("source", source);
        map.put("owner", owner);
        map.put("mphone", mphone);

        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        //  获取条件查询出来的线索列表
        PageInotationVO<Clue> vo = null;
        try {
            vo = cs.pageList(map);
        } catch (ClueException e) {
            e.printStackTrace();
        }

        PrintJson.printJsonObj(response, vo);
    }

    private void addClue(HttpServletRequest request, HttpServletResponse response) {
        String id = UUIDUtil.getUUID();
        String fullname = request.getParameter("fullname");
        String appellation = request.getParameter("appellation");
        String owner = request.getParameter("owner");
        String company = request.getParameter("company");
        String job = request.getParameter("job");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String website = request.getParameter("website");
        String mphone = request.getParameter("mphone");
        String state = request.getParameter("state");
        String source = request.getParameter("source");
        String createBy = ((User) request.getSession().getAttribute("user")).getName();
        String createTime = DateTimeUtil.getSysTime();
        String description = request.getParameter("description");
        String contactSummary = request.getParameter("contactSummary");
        String nextContactTime = request.getParameter("nextContactTime");
        String address = request.getParameter("address");

        Clue clue = new Clue();

        clue.setId(id);
        clue.setFullname(fullname);
        clue.setAppellation(appellation);
        clue.setOwner(owner);
        clue.setCompany(company);
        clue.setJob(job);
        clue.setEmail(email);
        clue.setPhone(phone);
        clue.setWebsite(website);
        clue.setMphone(mphone);
        clue.setState(state);
        clue.setSource(source);
        clue.setCreateBy(createBy);
        clue.setCreateTime(createTime);
        clue.setDescription(description);
        clue.setContactSummary(contactSummary);
        clue.setNextContactTime(nextContactTime);
        clue.setAddress(address);

        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        boolean flag = cs.addClue(clue);
        if (flag) {
            PrintJson.printJsonFlag(response, true);
        } else {
            PrintJson.printJsonFlag(response, false);
        }
    }

    private void getUserList(HttpServletRequest request, HttpServletResponse response) {
        //  创建service对象，业务层开发统一使用代理类形态的接口对象
        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        //  获取数据库中所有用户信息
        List<User> userList = null;
        try {
            userList = cs.getUserList();
            PrintJson.printJsonObj(response, userList);

        } catch (ClueException e) {
            e.printStackTrace();
        }
    }
}
