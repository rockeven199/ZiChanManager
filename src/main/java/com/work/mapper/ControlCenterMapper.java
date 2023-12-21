package com.work.mapper;

import com.work.entity.userItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ControlCenterMapper {
    @Select("select level,username,header_img from test_user where token=#{token}")
    public List<userItem> initCenterUser(String token);
}
