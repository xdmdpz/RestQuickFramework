package com.yf.common.auth;

import java.lang.annotation.*;

/**
 * Created by if on 2018/6/26
 */
public class AuthorizationAnnotation {

    @Documented
    @Retention(RetentionPolicy.RUNTIME)//
    @Target({ElementType.METHOD, ElementType.TYPE})//该注解修饰类中的方法
    @Inherited
    public @interface RequireValidate {
        /**
         * token验证注解
         * 该注解可以标记Controller 或 Controller 中的方法.
         * 如果Controller 有该标记,那么这个Controller下面所有的方法都会被过滤器
         * 进行验证
         * 如果Controller 没有有该标记,但Controller中的某个方法拥有该标记
         * 那么这个方法将被过滤器验证(其他没有被标记的不会被验证)
         *
         * 特别注意,如果一个Controller 被标记RRequireValidate 需要验证
         * 但是其中某些方法不想被验证.请参见RequireValidate标记
         *
         */
    }

    @Documented
    @Retention(RetentionPolicy.RUNTIME)//
    @Target(ElementType.METHOD)//该注解修饰类中的方法
    @Inherited
    public @interface NoRequireValidate {
        /**
         * 不需要token验证的方法注解注解
         * 该注解在Controller 标记了 RequireValidate 特性时
         * 某个方法不需要验证登录,那么为该方法标记该注解
         */
    }

    @Target({ElementType.METHOD, ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Inherited
    @Documented
    public @interface RequiresPermissions {
        /**
         * 操作元验证注解 作用于方法上
         * 该注解可以标记在被作用在 @RequireValidate标记的Controller的方法上
         * 或者用于被@RequireValidate标记的方法的方法上
         * <p>
         * 如果被标记 那么这个方法将被过滤器验证(其他没有被标记的不会被验证)
         */
        String value() default "";
    }
}
