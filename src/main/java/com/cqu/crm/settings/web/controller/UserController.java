package com.cqu.crm.settings.web.controller;

import com.cqu.crm.exception.LoginException;
import com.cqu.crm.settings.domain.User;
import com.cqu.crm.settings.service.UserService;
import com.cqu.crm.settings.service.impl.UserServiceImpl;
import com.cqu.crm.utils.MD5Util;
import com.cqu.crm.utils.PrintJson;
import com.cqu.crm.utils.ServiceFactory;
import com.cqu.crm.utils.SqlSessionUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("进入到用户控制器");

        String path = request.getServletPath();

        if ("/settings/user/login.do".equals(path)) {
            login(request, response);
        } else if ("/settings/user/xxx.do".equals(path)) {

        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response) {
        //  1获取用户名、密码、IP地址
        String loginAct = request.getParameter("loginAct");
        String loginPwd = request.getParameter("loginPwd");
        //  将密码通过MD5加密转换
        loginPwd = MD5Util.getMD5(loginPwd);
        //  获取IP地址
        String ip = request.getRemoteAddr();
        System.out.println("-----------------ip:" + ip);

        //  2创建service对象，业务层开发统一使用代理类形态的接口对象
        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());

        try {
            //  捕捉登录异常
            User user = us.login(loginAct, loginPwd, ip);
            request.getSession().setAttribute("user", user);
            //  登录成功
            PrintJson.printJsonFlag(response,true);

        } catch (LoginException e) {
            e.printStackTrace();

            //  登录失败
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("success", false);
            map.put("msg", e.getMessage());

            PrintJson.printJsonObj(response, map);
        }

    }
}
