package com.work.controller;

import com.work.entity.userItem;
import com.work.mapper.LoginMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@CrossOrigin(origins = "*")
@RestController
public class Login  {
    @Autowired private LoginMapper mapper;

    @RequestMapping ("/selectAll")
    public List<userItem> selectAll(){
        return mapper.selectAll();
    }

    @RequestMapping("/authUser")
    public String Login(@RequestParam("Username") String username, @RequestParam("Password") String password,  @RequestParam("taskID") Integer task_id,HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setHeader("goUrl","http://127.0.0.1:5173/controlCenter");
        response.setHeader("Access-Control-Expose-Headers","goUrl");
        if(mapper.Login(username, password,task_id).isEmpty()==false){
            List<userItem> resultData=mapper.Login(username, password,task_id);
            return "http://127.0.0.1:5173/controlCenter,"+resultData.get(0).getToken().toString();
        }else{
            return "No";
        }
    }
}
