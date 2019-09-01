package com.hao.mybatis.sqlSession.Impl;

import com.hao.mybatis.cfg.Configuration;
import com.hao.mybatis.sqlSession.SqlSession;
import com.hao.mybatis.sqlSession.SqlSessionFactory;

/**
 * @Describe com.hao.mybatis.sqlSession
 * @Auther wenhao chen
 * @CreateDate 2019/9/1
 * @Version 1.0
 *SqlSessionFactory实现类
 */
public class SqlSessionFactoryImpl implements SqlSessionFactory {

    private Configuration configuration;

    public SqlSessionFactoryImpl(Configuration configuration) {
        this.configuration = configuration;
    }

    //创建一个新的操作数据库对象
    public SqlSession openSession() {
        SqlSessionImpl sqlSession = new SqlSessionImpl(configuration);
        return sqlSession;
    }
}
