package com.hao.dao;

import com.hao.domain.User;
import com.hao.mybatis.annotations.Select;

import java.util.List;

/**
 * @Describe com.hao.dao
 * @Auther wenhao chen
 * @CreateDate 2019/8/28
 * @Version 1.0
 *
 * 使用注解，不再需要单独的mapper.xml配置文件
 */
public interface AnnoUserDao {
    @Select("select * from user")
    public List<User> findAll();

}
