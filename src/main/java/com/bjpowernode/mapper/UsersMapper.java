package com.bjpowernode.mapper;

import com.bjpowernode.pojo.Users;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *  数据访问层的接口，规定数据库可进行的各种操作
 */
public interface UsersMapper {
    List<Users> getAll();         //查询全部用户信息
    Users getById(Integer id);      //根据ID查询

    //List<Users> getByName(String name);     //根据name模糊查询
    List<Users> getByNameGood(String name);     //优化模糊查询

    int update(Users users);        //用户更新

    int insert(Users users);        //用户新增

    int delete(int id);             //根据主键删除用户
    List<Users> getByNameOrAddress(
            @Param("columnName")
                String columnName,
            @Param("columnValue")
                String columnValue);        //模糊用户名和地址查询

    List<Users> getByCondition(Users users);    //动态SQL查询

    int updateBySet(Users users);       //有选择的更新

    List<Users> getByIds(Integer[] arr);            //查询多个指定ID的用户
    int deleteBatch(Integer[] arr);        //批量删除
    int insertBatch(List<Users> users);         //批量增加

    List<Users> getByMap(Map map);    //查询指定日期范围内的用户
}
