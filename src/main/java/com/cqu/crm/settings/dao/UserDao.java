package com.cqu.crm.settings.dao;

import com.cqu.crm.settings.domain.User;

import java.util.Map;

public interface UserDao {
    User login(Map<String, String> map);
}
