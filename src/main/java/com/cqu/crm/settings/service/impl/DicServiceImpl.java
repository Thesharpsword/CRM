package com.cqu.crm.settings.service.impl;

import com.cqu.crm.settings.dao.DicTypeDao;
import com.cqu.crm.settings.dao.DicValueDao;
import com.cqu.crm.settings.domain.DicType;
import com.cqu.crm.settings.domain.DicValue;
import com.cqu.crm.settings.service.DicService;
import com.cqu.crm.utils.SqlSessionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DicServiceImpl implements DicService {
    DicTypeDao dicTypeDao = SqlSessionUtil.getSqlSession().getMapper(DicTypeDao.class);
    DicValueDao dicValueDao = SqlSessionUtil.getSqlSession().getMapper(DicValueDao.class);

    @Override
    public Map<String, List<DicValue>> getAll() {

        Map<String, List<DicValue>> map = new HashMap<String, List<DicValue>>();

        //  查DicType表格获取typecode
        List<DicType> dicTypes = dicTypeDao.getAll();

        //  根据typecode查DicValue表格获取对应的值
        for (DicType type : dicTypes) {

            String code = type.getCode();

            List<DicValue> dicValues = dicValueDao.getByTypecode(code);
            map.put(code + "List", dicValues);

        }
        return map;
    }
}
