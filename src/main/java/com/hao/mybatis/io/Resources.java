package com.hao.mybatis.io;

import java.io.InputStream;

/**
 * @Describe com.hao.mybatis.io
 * @Auther wenhao chen
 * @CreateDate 2019/8/29
 * @Version 1.0
 * 使用类加载器读取配置文件的类
 */
public class Resources {
    /**
     *  根据传入的参数，获取一个字节输入流
     * @return
     */
    public static InputStream getResourceAsStream(String filePath){
        return Resources.class.getClassLoader().getResourceAsStream(filePath);
    }
}
