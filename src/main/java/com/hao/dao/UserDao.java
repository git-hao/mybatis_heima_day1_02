package com.hao.dao;


import com.hao.domain.User;

import java.util.List;

/**
 * @Describe com.com.hao.dao
 * @Auther wenhao chen
 * @CreateDate 2019/8/28
 * @Version 1.0
 *
 */
public interface UserDao {

    List<User> findAll();
}
