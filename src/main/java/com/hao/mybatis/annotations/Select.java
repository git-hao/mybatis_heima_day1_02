package com.hao.mybatis.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Describe com.hao.mybatis.annotations
 * @Auther wenhao chen
 * @CreateDate 2019/9/1
 * @Version 1.0
 * 查询注解
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Select {
    /**
     * 配置sql语句
     * @return
     */
    String value();

}
