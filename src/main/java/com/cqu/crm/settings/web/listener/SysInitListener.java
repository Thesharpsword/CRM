package com.cqu.crm.settings.web.listener;

import com.cqu.crm.settings.domain.DicValue;
import com.cqu.crm.settings.service.DicService;
import com.cqu.crm.settings.service.impl.DicServiceImpl;
import com.cqu.crm.utils.ServiceFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SysInitListener implements ServletContextListener {
    /*
    * event:该参数能够取得的监听对象
    *       监听的是什么对象，就可以通过event取得什么对象
    * */
    @Override
    public void contextInitialized(ServletContextEvent event) {
        System.out.println("服务器缓存数据字典开始获取");

        DicService dicService = (DicService) ServiceFactory.getService(new DicServiceImpl());

        Map<String, List<DicValue>> map =  dicService.getAll();
        Set<String> set = map.keySet();
        for (String key : set) {
            //  将数据字典中的数据读入到服务器缓存中
            event.getServletContext().setAttribute(key, map.get(key));
        }
        System.out.println("服务器缓存数据字典获取结束");
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {

    }
}
