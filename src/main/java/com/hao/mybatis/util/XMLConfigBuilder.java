package com.hao.mybatis.util;

import com.hao.mybatis.annotations.Select;
import com.hao.mybatis.cfg.Configuration;
import com.hao.mybatis.cfg.Mapper;
import com.hao.mybatis.io.Resources;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Describe com.hao.mybatis.util
 * @Auther wenhao chen
 * @CreateDate 2019/8/29
 * @Version 1.0
 *
 * xml解析类
 * 具体内容：https://www.bilibili.com/video/av47952553/?p=14
 */
public class XMLConfigBuilder {

    /**
     * 解析主配置文件，将里面的内容填充到DefaultSqlSession所需要的地方
     * 技术：dom4j+xpath
     */
    public static Configuration loadConfiguration(InputStream config){
        try {
            //定义封装连接信息的配置对象 （mybatis的配置对象）
            Configuration cfg = new Configuration();

            //1,获取SAXReader对象
            SAXReader saxReader = new SAXReader();
            //2,根据字节输入流获取Document对象
            Document document = saxReader.read(config);
            //3,获取根节点
            Element root = document.getRootElement();
            //4,使用xpath中选择指定节点的方式，获取所有property节点
            List<Element> propertyElements = root.selectNodes("//property");
            //5,遍历节点
            for (Element propertyElement:propertyElements){
                //判断节点是连接数据库的哪部分信息
                //去除name属性的值
                String name = propertyElement.attributeValue("name");
                if ("driver".equals(name)){
                    //驱动，获取property标签value属性的值
                    String driver = propertyElement.attributeValue("value");
                    cfg.setDriver(driver);
                }
                if ("url".equals(name)){
                    String url = propertyElement.attributeValue("value");
                    cfg.setUrl(url);
                }
                if ("username".equals(name)){
                    String username = propertyElement.attributeValue("value");
                    cfg.setUsername(username);
                }
                if ("password".equals(name)){
                    String password = propertyElement.attributeValue("value");
                    cfg.setPassword(password);
                }
            }
            //取出mappers中的所有mapper标签，判断是使用resource（xml配置）还是class（注解配置）
            List<Element> mapperElements = root.selectNodes("//mappers/mapper");
            for(Element mapperElement:mapperElements){
                //取出resource属性，判断mapperElement使用哪个属性
                Attribute attribute = mapperElement.attribute("resource");
                if (attribute != null){
                    System.out.println("使用的是xml配置方式");
                    //取出属性的值:resource="com/com.hao/dao/UserDao.xml"
                    String mapperPath = attribute.getValue();//mapperPath:"com/com.hao/dao/UserDao.xml"
                    System.out.println(mapperPath);
                    //获取配置文件内容，封装为一个map
                    Map<String, Mapper> mappers = loadMapperConfiguration(mapperPath);

                    cfg.setMappers(mappers);
                }else {
                    //注解方式配置
                    System.out.println("使用注解配置");
                    String daoClassPath = mapperElement.attributeValue("class");
                    //根据daoClassPath获取封装的必要信息
                    Map<String,Mapper> mappers = loadMapperAnnotation(daoClassPath);
                    cfg.setMappers(mappers);
                }
            }
            return cfg;
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            try {
                config.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    /**
     *    根据传入的参数，解析xml，并封装到Map中
     * @param mapperPath   映射配置文件的位置
     * @return     map，包含了获取的唯一标识，（key：dao的全限定类名和方法名组成）
     *                   以及执行所需的必要信息，（value：一个mapper对象，存放要执行的sql语句和要封装的实体类全限定类名）
     * @throws Exception
     */
    private static Map<String,Mapper> loadMapperConfiguration(String mapperPath) throws Exception{
        InputStream in = null;
        try {
            //定义返回值对象
            Map<String,Mapper> mappers = new HashMap<String, Mapper>();
            //1,根据路径获取字节输入流
            in = Resources.getResourceAsStream(mapperPath);
            //2，根据字节输入流获取Document对象
            SAXReader reader = new SAXReader();
            Document document = reader.read(in);
            //3,获取跟节点
            Element root = document.getRootElement();
            //4,获取根节点的namespace属性
            String namespace = root.attributeValue("namespace");
            //5,获取所有的select节点
            List<Element> selectElements = root.selectNodes("//select");
            //6,遍历select节点
            for(Element selectElement:selectElements){
                //取出id属性的值，组成map中key部分
                String id = selectElement.attributeValue("id");
                //取出resultType属性的值，组成map中value部分
                String resultType = selectElement.attributeValue("resultType");
                //取出文本内容，组成map中value部分
                String queryString = selectElement.getText();
                //创建key
                String key = namespace+"."+id;
                //创建value
                Mapper mapper = new Mapper();
                mapper.setResultType(resultType);
                mapper.setQuerySql(queryString);
                //key和value存入mapper中
                mappers.put(key,mapper);
            }
            return mappers;
        }catch (Exception e){
            throw  new RuntimeException(e);
        }
    }

    public static Map<String,Mapper> loadMapperAnnotation(String daoClassPath)throws Exception{

        //定义返回值对象
        Map<String,Mapper> mappers = new HashMap<String, Mapper>();
        //1,得到dao接口的字节码对象
        Class daoClass = Class.forName(daoClassPath);
        //2,得到dao接口中的方法数组
        Method[] methods = daoClass.getMethods();
        //遍历方法数组
        for(Method method:methods){
            //每取出一个方法，判断是否有select注解
            boolean isAnnotated = method.isAnnotationPresent(Select.class);
            if (isAnnotated) {
                //创建Mapper对象
                Mapper mapper = new Mapper();
                //取出注解的value属性值
                Select selectAnno = method.getAnnotation(Select.class);
                String queryString = selectAnno.value();
                mapper.setQuerySql(queryString);
                //获取当前方法返回值，必须带有泛型信息(Generic)
                Type type = method.getGenericReturnType();//List<User>
                //判断type是不是参数化的类型(ParameterizedType)
                if (type instanceof ParameterizedType){
                    //强转
                    ParameterizedType pType = (ParameterizedType) type;
                    //得到参数化类型中的实际类型参数(ActualTypeArguments)
                    Type[] types = pType.getActualTypeArguments();
                    //取出第一个
                    Class domainClass = (Class) types[0];
                    //获取domainClass的类名
                    String resultType = domainClass.getName();
                    //给mapper赋值
                    mapper.setResultType(resultType);
                }
                //组装key
                //获取方法名称
                String methodName = method.getName();
                String className = method.getDeclaringClass().getName();
                String key = className+"."+methodName;
                //给map赋值
                mappers.put(key,mapper);
            }
        }
        System.out.println(mappers.size());
        return mappers;
    }

}
