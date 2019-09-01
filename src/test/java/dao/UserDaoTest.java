package dao;

import com.hao.dao.AnnoUserDao;
import com.hao.dao.UserDao;
import com.hao.domain.User;
import com.hao.mybatis.io.Resources;
import com.hao.mybatis.sqlSession.SqlSession;
import com.hao.mybatis.sqlSession.SqlSessionFactory;
import com.hao.mybatis.sqlSession.SqlSessionFactoryBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @Describe com.com.hao.dao
 * @Auther wenhao chen
 * @CreateDate 2019/8/28
 * @Version 1.0
 * xml配置，注解，两种测试
 */
public class UserDaoTest {

    public static void main(String[] args) throws IOException {

        //读取配置文件
        InputStream in = Resources.getResourceAsStream("SqlMapConfig.xml");
        //获取SqlSessionFactory对象
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory factory = builder.build(in);
        //获取SqlSession对象
        SqlSession sqlSession = factory.openSession();
        //获得dao接口代理对象，xml配置及注解两种
//        UserDao userDao = sqlSession.getMapper(UserDao.class);
//        //执行方法
//        List<User> users = userDao.findAll();
//        if (users != null) {
//            System.out.println("xml--");
//            for(User user : users){
//                System.out.println(user);
//            }
//        }

        AnnoUserDao annoUserDao = sqlSession.getMapper(AnnoUserDao.class);
        List<User> annoUserDaoAll = annoUserDao.findAll();
        if (annoUserDaoAll != null){
            System.out.println("注解--");
            for (User user:annoUserDaoAll){
                System.out.println(user);
            }
        }
        //关闭资源
        sqlSession.close();
        in.close();
    }
}
