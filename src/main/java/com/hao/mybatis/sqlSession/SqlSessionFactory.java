package com.hao.mybatis.sqlSession;

/**
 * @Describe com.hao.mybatis.sqlSession
 * @Auther wenhao chen
 * @CreateDate 2019/8/29
 * @Version 1.0
 */
public interface SqlSessionFactory {

    /**
     * 开启一个新的SqlSession对象
     * @return
     */
    SqlSession openSession();
}
