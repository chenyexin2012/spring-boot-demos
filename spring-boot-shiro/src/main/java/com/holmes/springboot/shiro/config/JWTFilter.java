package com.holmes.springboot.shiro.config;

import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 修改BasicHttpAuthenticationFilter采用JWT认证方式
 *
 */
public class JWTFilter extends BasicHttpAuthenticationFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        // 获取Header中的Token
        if (null != req.getHeader("Token")) {
            //如果存在，则进入 executeLogin 方法执行登入，检查 token 是否正确
            try {
                return executeLogin(req, res);
            } catch (Exception e) {
//                responseError(response, e.getMessage());
            }
        }
        return true;
    }
}
