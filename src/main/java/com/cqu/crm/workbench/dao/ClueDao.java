package com.cqu.crm.workbench.dao;


import com.cqu.crm.workbench.domain.Clue;

import java.util.List;
import java.util.Map;

public interface ClueDao {

    int addClue(Clue clue);

    int getCount();

    List<Clue> getClueListByCondition(Map<String, Object> map);

    Clue detail(String id);

    List<String> getActivityIdListByClueId(String clueId);

}
