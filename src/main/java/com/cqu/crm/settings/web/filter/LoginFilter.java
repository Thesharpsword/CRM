package com.cqu.crm.settings.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {

        if (req instanceof HttpServletRequest) {

            HttpServletRequest request = (HttpServletRequest) req;
            HttpServletResponse response = (HttpServletResponse) resp;

            //  放行登录页面
            String path = request.getServletPath();
            if ("/login.jsp".equals(path) || "/settings/user/login.do".equals(path) || "/index.jsp".equals(path)) {
                chain.doFilter(req, resp);
            } else {

                //  若Session对象中有User对象，则表明已经登录成功了
                HttpSession session = request.getSession();

                if (session.getAttribute("user") != null) {
                    //  Session对象中有User对象，则放行
                    chain.doFilter(req, resp);
                } else {
                    //  恶意登录
                    response.sendRedirect(request.getContextPath() + "/index.jsp");
                }
            }

        }
    }
}
