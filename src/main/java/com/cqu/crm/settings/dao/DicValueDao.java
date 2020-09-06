package com.cqu.crm.settings.dao;

import com.cqu.crm.settings.domain.DicType;
import com.cqu.crm.settings.domain.DicValue;

import java.util.List;

public interface DicValueDao {

    List<DicValue> getByTypecode(String code);
}
