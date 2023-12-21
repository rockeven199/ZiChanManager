package com.work.controller;

import com.work.mapper.ControlCenterMapper;
import com.work.mapper.LoginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
public class controlCenter {
    @Autowired
    private ControlCenterMapper mapper;

    @RequestMapping("/initCenterUser")
    public Object initCenterUser(@RequestParam("token") String token){
        Object data=mapper.initCenterUser(token).get(0);
        return  data;
    }
}
