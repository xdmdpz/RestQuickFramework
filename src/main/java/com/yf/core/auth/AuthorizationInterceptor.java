package com.yf.core.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 授权认证拦截器
 *
 * Created by xdmdpz on 2018/6/26.
 */
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private AuthorizationUtils authorizationUtils;

    /**
     * 请求拦截器
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getMethod().equals("OPTIONS")) {
            //跨域放行
            return true;
        }
        if (handler instanceof HandlerMethod) {
            try {
                authorizationUtils.verify(request,response, (HandlerMethod) handler);
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }


}

