package com.cqu.crm.settings.web.filter;

import javax.servlet.*;
import java.io.IOException;

public class EncodingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        //  过滤post请求中文参数乱码
        req.setCharacterEncoding("UTF-8");
        //  过滤响应流中文参数乱码
        resp.setContentType("text/html;charset=utf-8");

        //  过滤后放行
        chain.doFilter(req, resp);
    }
}
