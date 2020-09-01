package com.cqu.crm.workbench.dao;

public interface ActivityRemarkDao {
    int getCountByIds(String[] ids);

    int deleteByIds(String[] ids);
}
