package com.hao.mybatis.sqlSession.Impl;

import com.hao.mybatis.cfg.Configuration;
import com.hao.mybatis.sqlSession.SqlSession;
import com.hao.mybatis.sqlSession.proxy.MapperProxy;
import com.hao.mybatis.util.DataSourceUtil;

import java.lang.reflect.Proxy;
import java.sql.Connection;

/**
 * @Describe com.hao.mybatis.sqlSession.Impl
 * @Auther wenhao chen
 * @CreateDate 2019/9/1
 * @Version 1.0
 * SqlSession实现类
 */
public class SqlSessionImpl implements SqlSession {

    private Configuration configuration;
    private Connection connection;

    public SqlSessionImpl(Configuration configuration) {
        this.configuration = configuration;
        connection = DataSourceUtil.getConnection(configuration);
    }


    public <T> T getMapper(Class<T> daoInterfaceClass) {
        Object o = Proxy.newProxyInstance(daoInterfaceClass.getClassLoader(),
                new Class[]{daoInterfaceClass}, new MapperProxy(configuration.getMappers(), connection));
        return (T) o;
    }

    public void close() {
        if (configuration == null) {
            try {
                connection.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }
}
