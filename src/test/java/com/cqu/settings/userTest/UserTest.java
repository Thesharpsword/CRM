package com.cqu.settings.userTest;

import com.cqu.crm.utils.MD5Util;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UserTest {

    @Test
    public void testExpireTime() {
        String expireTime = "2020-08-30 11:09:08";

        Date sysDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sysTime = dateFormat.format(sysDate);
        System.out.println(sysTime);
    }

    @Test
    public void testMD5() {
        String password = "IloveYOUbaby";
        String md5Pwd = MD5Util.getMD5(password);
        System.out.println(md5Pwd);
    }

}
