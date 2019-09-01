package com.hao.mybatis.sqlSession;

/**
 * @Describe com.hao.mybatis.sqlSession
 * @Auther wenhao chen
 * @CreateDate 2019/8/29
 * @Version 1.0
 * 手写mybatis中和数据库交互的核心类
 * 创建dao接口的代理对象
 */
public interface SqlSession {

    /**
     * 根据参数创建一个代理对象
     * 使用泛型，先定义，后使用
     * @param daoInterfaceClass  dao接口字节码
     * @param <T>
     * @return
     */
    <T> T getMapper(Class<T> daoInterfaceClass);

    /**
     * 释放资源
     */
    void close();
}
