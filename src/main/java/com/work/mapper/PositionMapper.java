package com.work.mapper;

import com.work.entity.positionItem;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PositionMapper {
    @Insert("INSERT INTO test_position VALUES (null,#{add_position_info},#{position_status})")
    public boolean addPosition(String add_position_info,String position_status);

    @Delete("delete from test_position where position_name=#{position_name}")
    public boolean delPosition(String position_name);

    @Select("select * from test_position where position_name=#{search_name} and position_status=#{search_status}")
    public List<positionItem> searchPosition(String search_name,String search_status);

    @Select("select * from test_position;")
    public List<positionItem> searchAllPosition();

    @Select("select * from test_position where position_name=#{search_position_name}")
    public List<positionItem> searchAllPositionByName(String search_position_name);

    @Select("select * from test_position where position_status=#{search_position_status}")
    public List<positionItem> searchAllPositionByStatus(String search_position_status);

    @Update("update test_position set position_name=#{search_position_name},position_status=#{search_position_status},position_address=#{search_position_desc} where position_id=#{position_id}")
    public boolean updatePosition(String search_position_name, String search_position_status, String search_position_desc,Integer position_id);
}
