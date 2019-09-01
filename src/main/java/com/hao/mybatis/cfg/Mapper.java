package com.hao.mybatis.cfg;

/**
 * @Describe com.hao.mybatis.cfg
 * @Auther wenhao chen
 * @CreateDate 2019/8/29
 * @Version 1.0
 * 封装执行的sql语句，和结果类型的全限定名称
 */
public class Mapper {

    private String querySql;//sql语句
    private String resultType;//结果类型的全限定名称

    public String getQuerySql() {
        return querySql;
    }

    public void setQuerySql(String querySql) {
        this.querySql = querySql;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }
}
