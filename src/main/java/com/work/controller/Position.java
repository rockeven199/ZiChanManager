package com.work.controller;

import com.work.entity.positionItem;
import com.work.mapper.PositionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class Position {
    @Autowired
    private PositionMapper mapper;

    @RequestMapping("/add_Position")
    public String addPosition(@RequestParam("position_name") String position_name, @RequestParam("position_status") String position_status) {
        if (mapper.addPosition(position_name, position_status)) {
            return "success";
        } else {
            return "error";
        }
    }

    @RequestMapping("/del_Position")
    public String delPosition(@RequestParam("delName") String del_name) {
        if (mapper.delPosition(del_name)) {
            return "success";
        } else {
            return "error";
        }
    }

    @RequestMapping("/search_Position")
    public List<positionItem> searchPosition(@RequestParam("search_position_name") String search_name, @RequestParam("search_position_status") String search_status) {
        return mapper.searchPosition(search_name, search_status);
    }

    @RequestMapping("/search_all_Position")
    public List<positionItem> searchAllPosition() {
        return mapper.searchAllPosition();
    }

    @RequestMapping("/search_all_Position_By_Name")
    public List<positionItem> searchAllPositionByName(@RequestParam("search_position_name") String search_position_name) {
        return mapper.searchAllPositionByName(search_position_name);
    }

    @RequestMapping("/search_all_Position_By_Status")
    public List<positionItem> searchAllPositionByStatus(@RequestParam("search_position_status") String search_position_status) {
        return mapper.searchAllPositionByStatus(search_position_status);
    }

    @RequestMapping("/update_Position")
    public void updatePosition(@RequestParam("search_position_name") String search_position_name, @RequestParam("search_position_status") String search_position_status, @RequestParam("search_position_desc") String search_position_desc, @RequestParam("search_position_id") Integer search_position_id) {
        mapper.updatePosition(search_position_name, search_position_status, search_position_desc, search_position_id);
    }

    @RequestMapping("/data_page_group")
    public Object dataPageGroup(@RequestParam("pages_num") Integer pages_num) {
        ArrayList temp = new ArrayList();
        ArrayList result = new ArrayList();
        int totalSize = mapper.searchAllPosition().size();
        for (int count = 0; count < totalSize; count++) {
            if (count % 7 == 0 && count != 0) {
                result.add(temp);
                temp = new ArrayList();
                temp.add(mapper.searchAllPosition().get(count));
            } else {
                temp.add(mapper.searchAllPosition().get(count));
            }
        }

        ArrayList temp2 = new ArrayList();
        for (int nextCount = 0; nextCount < totalSize - (result.size() * 7); nextCount++) {
            temp2.add(mapper.searchAllPosition().get(result.size() * 7 + nextCount));
        }

        result.add(temp2);
        if (pages_num > totalSize % 7) {
            return false;
        } else {
            return result.get(pages_num - 1);
        }
        //        for (int count = 0; count<mapper.searchAllPosition().size()+1 ; count++) {
//            if (count % 5 == 0 && count != 0 ) {
//                result.add(temp);
//                temp=new ArrayList();
//            }
//
//            if(count == 15){
//                temp.add(mapper.searchAllPosition().get(count-1));
//            }else{
//                temp.add(mapper.searchAllPosition().get(count));
//            }
//        }
//        if(pages>mapper.searchAllPosition().size()/5){
//            return null;
//        }else{
//            return result.get(pages-1);
//        }
    }
}
