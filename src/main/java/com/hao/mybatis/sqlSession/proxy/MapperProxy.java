package com.hao.mybatis.sqlSession.proxy;

import com.hao.mybatis.cfg.Mapper;
import com.hao.mybatis.util.Executor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 * @Describe com.hao.mybatis.sqlSession.proxy
 * @Auther wenhao chen
 * @CreateDate 2019/9/1
 * @Version 1.0
 *
 */
public class MapperProxy implements InvocationHandler {

    //key是 全限定类名+方法名
    private Map<String, Mapper> mappers;
    private Connection conn;

    public MapperProxy(Map<String, Mapper> mappers,Connection conn) {
        this.mappers = mappers;
        this.conn = conn;
    }

    /**
     * 增强方法，调用selectList方法
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //获取方法名
        String methodName = method.getName();
        //获取方法所在类的名称
        String className = method.getDeclaringClass().getName();
        //组合key
        String key = className+"."+methodName;
        //获取mappers中的Mapper对象
        Mapper mapper = mappers.get(key);
        //判断是否有mapper
        if (mapper == null) {
            throw new IllegalArgumentException("传入参数有误");
        }
        //调用工具类执行sql
        List<Object> objectList = new Executor().selectList(mapper, conn);
        return objectList;
    }
}
