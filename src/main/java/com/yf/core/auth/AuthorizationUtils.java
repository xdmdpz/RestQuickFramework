package com.yf.core.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.yf.core.exception.Exceptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by if on Created by if on 2018/6/26
 */
@Configuration
public class AuthorizationUtils {

    @Value("${custom.jwt.secret}")
    private String secret;
    @Value("${custom.jwt.iss}")
    private String iss;
    @Value("${custom.jwt.sub}")
    private String sub;
    @Value("${custom.jwt.header-name}")
    private String headerName;
    @Value("${custom.jwt.expire-in-minute}")
    private Integer expireInMinute;



    /**
     * 生成Token
     */
    public AccessToken create(Integer id, String username, Integer type , HashMap<String,String> others) {
        try {
            long expireIn = expireInMinute * 60 * 1000;
            Date expiresAt = new Date(System.currentTimeMillis() + expireIn);
            Date issuedAt = new Date();
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTCreator.Builder builder = JWT.create()
                    .withIssuer(iss)
                    .withSubject(sub)
                    .withExpiresAt(expiresAt)
                    .withIssuedAt(issuedAt);
            //通常放用户id
            builder.withAudience(id+"");
            //放入用户名、用户类型
            builder.withClaim("username",username)
                    .withClaim("type",type);
            //放入其他参数
            others.forEach(builder::withClaim);

            return AccessToken.builder()
                    .token(builder.sign(algorithm))
                    .expiresIn(String.valueOf(expireIn))
                    .type(type)
                    .build();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
    public AccessToken create(Integer id,String username ,Integer type){
        return create(id, username, type,new HashMap<>());
    }

    /**
     * 验证token
     *
     * @param request
     * @param response
     * @param myHandlerMethod
     * @return
     * @throws IOException
     */
    public boolean verify(HttpServletRequest request, HttpServletResponse response, HandlerMethod myHandlerMethod) throws IOException {
        Object bean = myHandlerMethod.getBean();
        Method method = myHandlerMethod.getMethod();
        //类上有该标记
        Annotation classAnnotation = bean.getClass().getAnnotation(AuthorizationAnnotation.RequireValidate.class);
        //方法上有该标记
        Annotation methodAnnotation = method.getAnnotation(AuthorizationAnnotation.RequireValidate.class);
        //白名单标记
        Annotation methodAuthIgnoreAnnotation = method.getAnnotation(AuthorizationAnnotation.NoRequireValidate.class);

        //拦截判断
        if ((classAnnotation != null && methodAuthIgnoreAnnotation == null)
                || (classAnnotation == null && methodAnnotation != null)) {
            //token判断
            if (!verify(request)) {
                throw new Exceptions.UnauthorizedException("token验证失败");
            }
        }
        return true;
    }


    /**
     * 获取用户id
     */
    public Integer getUserId(HttpServletRequest request) {
        return getUserId(getToken(request));
    }

    public Integer getUserId(String token) {
        return Integer.valueOf(decode(token)
                .getAudience().get(0));
    }


    /**
     * 获取用户类型
     */
    public Integer getType(String token) {
        return decode(token)
                .getClaim("type")
                .asInt();
    }

    public Integer getType(HttpServletRequest request) {
        return getType(getToken(request));
    }

    /**
     * 获取用户名
     */
    public Integer getUsername(String token) {
        return decode(token)
                .getClaim("username")
                .asInt();
    }

    public Integer getUsername(HttpServletRequest request) {
        return getUsername(getToken(request));
    }

    /**
     * 验证token
     *
     * @param token
     * @return
     */
    public boolean verify(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(iss)
                    .build(); //Reusable verifier instance
            verifier.verify(token);
            return true;
        } catch (UnsupportedEncodingException | JWTVerificationException exception) {
            return false;
        }

    }

    /**
     * 验证token
     *
     * @param request
     * @return
     */
    public boolean verify(HttpServletRequest request) {
        String accessToken = getToken(request);
        return verify(accessToken);
    }

    /**
     * 从Header或者parameter获取TOKEN
     */
    private String getToken(HttpServletRequest request) {
        String accessToken = request.getHeader(headerName);
        if (StringUtils.isEmpty(request)) {
            accessToken = request.getParameter("token");
            if (StringUtils.isEmpty(accessToken)) {
                throw new Exceptions.ForbiddenException("token is not null or empty");
            }
        }
        return accessToken;
    }

    /**
     * 解密token
     *
     * @param token
     * @return
     */
    private DecodedJWT decode(String token) {
        return JWT.decode(token);
    }


}
