package com.cqu.crm.workbench.service;

import com.cqu.crm.exception.ActivityException;
import com.cqu.crm.vo.PageInotationVO;
import com.cqu.crm.workbench.domain.Activity;

import java.util.Map;

public interface ActivityService {
    void save(Activity activity) throws ActivityException;

    PageInotationVO<Activity> pageList(Map<String, Object> map) throws ActivityException;

    boolean deleteById(String[] ids) throws ActivityException;

    Map<String, Object> updateById(String id) throws ActivityException;

    boolean update(Activity activity);
}
