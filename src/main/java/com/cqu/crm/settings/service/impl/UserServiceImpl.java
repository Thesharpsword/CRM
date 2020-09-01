package com.cqu.crm.settings.service.impl;

import com.cqu.crm.exception.LoginException;
import com.cqu.crm.settings.dao.UserDao;
import com.cqu.crm.settings.domain.User;
import com.cqu.crm.settings.service.UserService;
import com.cqu.crm.utils.DateTimeUtil;
import com.cqu.crm.utils.SqlSessionUtil;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {
    private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

    @Override
    public User login(String loginAct, String loginPwd, String ip) throws LoginException {

        Map<String, String> map = new HashMap<String, String>();
        map.put("loginAct", loginAct);
        map.put("loginPwd", loginPwd);

        User user = userDao.login(map);

        //  若查无此用户，则抛出异常信息
        if (user == null) {
            throw new LoginException("用户名或密码错误");
        }

        //  账号密码正确，继续向下验证

        //  验证失效时间
        String expireTime = user.getExpireTime();
        String sysTime = DateTimeUtil.getSysTime();

        if (expireTime.compareTo(sysTime) < 0) {
            throw new LoginException("该账户已失效");
        }

        //  验证锁定状态
        if ("0".equals(user.getLockState())) {
            throw new LoginException("该账户已被锁定");
        }

        //  验证ip地址
        if (!user.getAllowIps().contains(ip)) {
            throw new LoginException("IP地址非法");
        }

        return user;
    }

    @Override
    public List<User> getUserList() throws LoginException {
        List<User> userList = userDao.getUserList();

        if (userList == null) {
            throw new LoginException("查询失败");
        }

        return userList;
    }
}
