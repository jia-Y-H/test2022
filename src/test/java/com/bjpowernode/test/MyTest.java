package com.bjpowernode.test;

import com.bjpowernode.mapper.UsersMapper;
import com.bjpowernode.pojo.Users;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MyTest {
    SqlSession sqlSession;
    UsersMapper uMapper;
    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
    @Before
    public void openSqlSession() throws IOException {
        //1.读取核心配置文件
        InputStream in = Resources.getResourceAsStream("SqlMapConfig.xml");
        //2.创建工厂对象
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        //3.取出sqlSession
        sqlSession = factory.openSession();
//取出动态代理的对象，完成接口中方法的调用，实则是调用xml文件中相应的标签的功能。
        uMapper = sqlSession.getMapper(UsersMapper.class);
    }
    @After
    public void closeSqlSession(){
        sqlSession.close();
    }


    @Test
    public void testGetAll(){
        System.out.println(uMapper.getClass());
        //就是在调用接口的方法,mybatis框架已经为我们把功能代理出来了
        List<Users> list = uMapper.getAll();
        list.forEach(System.out::println);
    }

    @Test
    public void testGetById(){
        Users users = uMapper.getById(3);
        System.out.println(users);
    }

    @Test
    public void testGetByName(){
        UsersMapper uMapper = sqlSession.getMapper(UsersMapper.class);
        List<Users> list = uMapper.getByNameGood("小");
        list.forEach(System.out::println);
    }
    @Test
    public void testUpdate() throws ParseException {
        Users users = new Users(7,"懒大王",sf.parse("2002-01-23"),"1","邯郸市");
        int num = uMapper.update(users);
        sqlSession.commit();
    }

    @Test
    public void testInsert() throws ParseException {
        Users users = new Users("橙留香",sf.parse("2001-03-21"),"2","果宝世界");
        uMapper.insert(users);
        sqlSession.commit();
        System.out.println(users);
    }

    @Test
    public void testDelete(){
        uMapper.delete(27);
        sqlSession.commit();
    }

    @Test
    public void testGetByNameOrAddress(){
        List<Users> byNameOrAddress = uMapper.getByNameOrAddress("address", "市");
        byNameOrAddress.forEach(System.out::println);
    }

    @Test
    public void testGetByCondition(){
        Users users = new Users();
        users.setAddress("市");
        //users.setSex("2");
        users.setUserName("三");
        List<Users> byNameOrAddress = uMapper.getByCondition(users);
        byNameOrAddress.forEach(System.out::println);
    }

    @Test
    public void testUpdateBySet(){
        Users users = new Users();
        users.setId(29);
        users.setUserName("景天");
        int num = uMapper.updateBySet(users);
        sqlSession.commit();
    }

    @Test
    public void testGetByIds(){
        Integer[] arr = {2,4,6};
        List<Users> byIds = uMapper.getByIds(arr);
        byIds.forEach(System.out::println);
    }

    @Test
    public void testDeleteBatch(){
        Integer[] arr = {28,29};
        int num = uMapper.deleteBatch(arr);
        sqlSession.commit();
    }

    @Test
    public void testInsertBatch() throws ParseException {
        List<Users> list = new ArrayList<>();
        Users users1 = new Users("龙葵",sf.parse("1345-04-21"),"2","姜国");
        Users users2 = new Users("龙阳",sf.parse("1342-08-04"),"1","姜国");
        list.add(users1);
        list.add(users2);
        int num = uMapper.insertBatch(list);
        sqlSession.commit();
    }

    @Test
    public void testGetByMap() throws ParseException {
        Date begin = sf.parse("1000-01-01");
        Date end = sf.parse("1999-12-31");
        Map map = new HashMap<>();
        map.put("birthdayBegin",begin);
        map.put("birthdayEnd", end);
        List<Users> list = uMapper.getByMap(map);
        list.forEach(System.out::println);
    }
}
