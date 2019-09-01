package com.hao.mybatis.sqlSession;

import com.hao.mybatis.cfg.Configuration;
import com.hao.mybatis.sqlSession.Impl.SqlSessionFactoryImpl;
import com.hao.mybatis.util.XMLConfigBuilder;

import java.io.InputStream;

/**
 * @Describe com.hao.mybatis.sqlSession
 * @Auther wenhao chen
 * @CreateDate 2019/8/29
 * @Version 1.0
 * 创建要给sqlSessionFactory对象
 */
public class SqlSessionFactoryBuilder {
    /**
     * 根据参数的字节输入流创建一个SqlSessionFactory
     * @param inputStream
     * @return
     */
    public SqlSessionFactory build(InputStream inputStream){
        Configuration cfg = XMLConfigBuilder.loadConfiguration(inputStream);
        SqlSessionFactoryImpl sqlSessionFactory = new SqlSessionFactoryImpl(cfg);
        return sqlSessionFactory;
    }
}
