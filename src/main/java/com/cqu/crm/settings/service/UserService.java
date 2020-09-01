package com.cqu.crm.settings.service;

import com.cqu.crm.exception.LoginException;
import com.cqu.crm.settings.domain.User;

import java.util.List;

public interface UserService {
    User login(String loginAct, String loginPwd, String ip) throws LoginException;

    List<User> getUserList() throws LoginException;
}
