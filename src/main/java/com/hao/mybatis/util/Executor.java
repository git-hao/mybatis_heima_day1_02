package com.hao.mybatis.util;

import com.hao.mybatis.cfg.Mapper;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * @Describe com.hao.mybatis.util
 * @Auther wenhao chen
 * @CreateDate 2019/9/1
 * @Version 1.0
 *
 * 执行sql语句,并封装结果集
 */
public class Executor {

    public <E> List<E> selectList(Mapper mapper, Connection conn){
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            //1,取出mapper中的数据
            String queryString = mapper.getQuerySql();
            String resultType = mapper.getResultType();
            Class domainClass = Class.forName(resultType);
            //2，获取PreparedStatement对象
            pstm = conn.prepareStatement(queryString);
            //3,执行sql语句，获取结果集
            rs = pstm.executeQuery();
            //4，封装结果集
            ArrayList<E> list = new ArrayList<E>();//定义返回值
            while (rs.next()){
                //实例化要封装的实体类对象
                E obj = (E)domainClass.newInstance();
                //取出结果集元信息，
                ResultSetMetaData rsmd = rs.getMetaData();
                //取出总列数
                int columnCount = rsmd.getColumnCount();
                //遍历
                for (int i = 1; i <= columnCount; i++) {
                    //获取每列的名称，序号从1开始
                    String columnName = rsmd.getColumnName(i);
                    //根据列名，获取每列的值
                    Object columnValue = rs.getObject(columnName);
                    //给obj赋值，使用java内省机制（借助PropertyDescriptor实现属性的封装）
                    PropertyDescriptor pd = new PropertyDescriptor(columnName,domainClass);
                    //获取写入方法
                    Method writeMethod = pd.getWriteMethod();
                    //获取列的值，给对象复制
                    writeMethod.invoke(obj,columnValue);
                }
                //赋好的值加入到集合中
                list.add(obj);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (rs != null) {
                try {
                    rs.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            if (pstm != null) {
                try {
                    pstm.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
