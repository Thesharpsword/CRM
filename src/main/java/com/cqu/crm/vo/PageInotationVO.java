package com.cqu.crm.vo;

import java.util.List;

public class PageInotationVO<T> {
    private int total;  //  活动记录总条数
    private List<T> activityList;    //  活动记录列表

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getActivityList() {
        return activityList;
    }

    public void setActivityList(List<T> activityList) {
        this.activityList = activityList;
    }
}
