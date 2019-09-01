package com.hao.mybatis.util;

import com.hao.mybatis.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @Describe com.hao.mybatis.util
 * @Auther wenhao chen
 * @CreateDate 2019/9/1
 * @Version 1.0
 * 创建数据源
 */
public class DataSourceUtil {

    public static Connection getConnection(Configuration cfg) {
        try {
            Class.forName(cfg.getDriver());
            Connection connection = DriverManager.getConnection(cfg.getUrl(), cfg.getUsername(), cfg.getPassword());
            return connection;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
