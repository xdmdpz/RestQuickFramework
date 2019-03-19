package com.yf.common.auth;

/**
 * Created by xdmdpz on 2018/6/26.
 */

import org.apache.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 授权认证拦截器
 */
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    Logger logger = Logger.getLogger(this.getClass());


    /**
     * 请求拦截器
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getMethod().equals("OPTIONS")) {
            //跨域放行
            return true;
        }
        if (handler instanceof HandlerMethod) {
            try {
                AuthorizationUtils.getInstance().verify(request,response, (HandlerMethod) handler);
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }


}

