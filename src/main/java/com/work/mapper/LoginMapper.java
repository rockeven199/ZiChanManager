package com.work.mapper;

import com.work.entity.userItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LoginMapper {
    @Select("select * from test_user")
    public List<userItem> selectAll();

    @Select("select token from test_user where username=#{username} and password=#{password} and task_id=#{task_id}")
    public List<userItem> Login(String username,String password,Integer task_id);
}