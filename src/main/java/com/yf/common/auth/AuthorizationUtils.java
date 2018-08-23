package com.yf.common.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.yf.common.base.RestResponse;
import com.yf.common.user.UserInfo;
import org.hibernate.annotations.common.util.StringHelper;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by if on Created by if on 2018/6/26
 */
public class AuthorizationUtils {

    private static String secret = "#KrzQ!XYo%LVm1Igi7SUdh!8Xss7rz63BCyJlDZsq!1WFe0qqJJvsl%4Rt4DWgke";

    private static String iss = "TechFantasy";

    private static String sub = "NebulaData";

    public static final String HeaderTokenKey = "token";


    private static class AuthorizationUtilsInstance {

        private static final AuthorizationUtils INSTANCE = new AuthorizationUtils();

    }

    private AuthorizationUtils() {
    }

    public static final AuthorizationUtils getInstance() {

        return AuthorizationUtilsInstance.INSTANCE;

    }


    /**
     * 生成Token
     *
     * @param user
     * @return
     */
    public AccessToken create(UserInfo user) {
        try {
            long expireIn = 1000 * 60 * 30;
            Date expiresAt = new Date(System.currentTimeMillis() + expireIn);
            Date issuedAt = new Date();
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer(iss)
                    .withSubject(sub)
                    .withAudience(user.getId() + "")
                    .withExpiresAt(expiresAt)
                    .withIssuedAt(issuedAt)
                    .withClaim("type", user.getType())
                    .withClaim("realName", user.getRealname())
                    .withClaim("userName", user.getUsername())
                    .sign(algorithm);
            AccessToken accessToken = new AccessToken();
            accessToken.setToken(token);
            accessToken.setExpiresIn(String.valueOf(expireIn));
            accessToken.setType(user.getType());
            return accessToken;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
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
        Annotation classAnnotation = bean.getClass().getAnnotation(AuthorizationAnnotation.RequireValidate.class);//类上有该标记
        Annotation methodAnnotation = method.getAnnotation(AuthorizationAnnotation.RequireValidate.class);//方法上有该标记
        Annotation methodNologinAnnotation = method.getAnnotation(AuthorizationAnnotation.NoRequireValidate.class);//

        //拦截判断
        if ((classAnnotation != null
                && methodNologinAnnotation == null)
                || (classAnnotation == null
                && methodAnnotation != null)) {

            Annotation requiresPermissionsAnnotation = method.getAnnotation(AuthorizationAnnotation.RequiresPermissions.class);
            AuthorizationAnnotation.RequiresPermissions requiresPermissions = (AuthorizationAnnotation.RequiresPermissions) requiresPermissionsAnnotation;
            //token判断
            if (!verify(request)) {
                writerResponse(response, (new RestResponse()).failed(403, "token验证失败"));
                return false;
            }
//            //操作元判断
//            if (requiresPermissionsAnnotation != null && !requiresPermissions.value().equals("")) {
//                if (!ValidateOperator(request, accessToken, requiresPermissions.value())){
//                    //操作元验证失败
//                    writerResponse(response ,(new RestResponse()).builder(RestResponseCodeEnum.VERIFY_TOKEN_OPERATOR));
//                    return false;
//                }
//
//            }

        }
        return true;
    }


    /**
     * 获取用户id
     *
     * @param token
     * @return
     */
    public Long getUserId(String token) {
        if (StringHelper.isEmpty(token))
            throw new NullPointerException("token is not null or empty");
        DecodedJWT jwt = decode(token);
        return Long.parseLong(jwt.getAudience().get(0));
    }

    /**
     * 获取用户id
     *
     * @param request
     * @return
     */
    public Long getUserId(HttpServletRequest request) {
        String accessToken = request.getHeader(HeaderTokenKey);
        if (accessToken == null || accessToken.equals(""))
            accessToken = request.getParameter("token");
        return getUserId(accessToken);
    }

    /**
     * 获取用户类型
     *
     * @param request
     * @return
     */
    public Integer getType(HttpServletRequest request) {
        String accessToken = request.getHeader(HeaderTokenKey);
        if (accessToken == null || accessToken.equals(""))
            accessToken = request.getParameter("token");
        return getType(accessToken);
    }

    /**
     * 获取用户类型
     *
     * @param token
     * @return
     */
    public Integer getType(String token) {
        if (StringHelper.isEmpty(token))
            throw new NullPointerException("token is not null or empty");
        DecodedJWT jwt = decode(token);
        Claim claim = jwt.getClaim(token);
        return jwt.getClaim("type").asInt();
    }


//    /**
//     * 验证操作元
//     *
//     * @param request
//     * @param token
//     * @param operator
//     * @return
//     */
//    private boolean ValidateOperator(HttpServletRequest request, String token, String operator) {
//        boolean res = false;
//
//        return res;
//    }

    /**
     * 验证token
     *
     * @param token
     * @return
     */
    public boolean verify(String token) {
        boolean result = false;
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(iss)
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            result = true;
        } catch (UnsupportedEncodingException exception) {
            result = false;
        } catch (JWTVerificationException exception) {
            result = false;
        }
        return result;
    }

    /**
     * 验证token
     *
     * @param request
     * @return
     */
    public boolean verify(HttpServletRequest request) {
        String accessToken = request.getHeader(HeaderTokenKey);
        if (accessToken == null || accessToken.equals("")) {
            accessToken = request.getParameter("token");
            if (accessToken == null || accessToken.equals("")) {
                //tocken验证失败
                return false;
            }
        }
        if (!verify(accessToken)) {
            return false;
        }
        return true;
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

    /**
     * 操作元验证失败 封装response
     *
     * @param response
     * @throws IOException
     */
    private void writerResponse(HttpServletResponse response, RestResponse json) throws IOException {
        response.setStatus(200);
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Expose-Headers", "header1, header2, Authorization");
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        String result = new JsonObjectMapper().writeValueAsString(json);
        writer.print(result);
        writer.close();
    }

    /**
     * 模拟请求失败返回值
     */
    class JsonObjectMapper extends ObjectMapper {
        protected JsonObjectMapper() {
            super();
            super.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            super.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
            super.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
            super.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        }
    }

}
