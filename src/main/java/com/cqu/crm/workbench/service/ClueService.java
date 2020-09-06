package com.cqu.crm.workbench.service;

import com.cqu.crm.exception.ClueException;
import com.cqu.crm.settings.domain.User;
import com.cqu.crm.vo.PageInotationVO;
import com.cqu.crm.workbench.domain.Activity;
import com.cqu.crm.workbench.domain.Clue;

import java.util.List;
import java.util.Map;

public interface ClueService {
    List<User> getUserList() throws ClueException;

    boolean addClue(Clue clue);

    PageInotationVO<Clue> pageList(Map<String, Object> map) throws ClueException;

    Clue detail(String id) throws ClueException;

    List<Activity> getActivityListById(String clueId) throws ClueException;

    boolean deleteRelatedActivityById(String id);

    List<Activity> getRelatedActivityByActivityName(Map<String, String> aname) throws ClueException;
}
