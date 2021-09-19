package com.yk.controller;

import com.yk.model.ResultInfo;
import com.yk.utils.AssertUtil;
import com.yk.utils.JwtUtil;
import com.yk.entity.User;
import com.yk.model.LoginInfo;
import com.yk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yk
 * @version 1.0
 * @date 2021/3/31 23:32
 */

@Controller
public class LoginController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    @ResponseBody
    public ResultInfo login(@RequestBody LoginInfo loginInfo){
        //通过userName和password查询数据库
        User user = userService.queryByUserNameAndPassword(loginInfo.getUsername(),loginInfo.getPassword());
        //判断是否有登录权限
        if(user == null||!"ROLE_admin".equals(user.getRole())){
            //无权限
            AssertUtil.isTrue(true,"无权限!");
        }

        user.setPassword(null);

        String jwt = JwtUtil.generateToken("admin:" + user.getUserName());
        Map<String,Object> map = new HashMap<>();
        map.put("user",user);
        map.put("token",jwt);
        return ResultInfo.createResult("登录成功!",map);
    }

    @PostMapping("admin/login")
    @ResponseBody
    public ResultInfo adminLogin(@RequestBody LoginInfo loginInfo){
        //通过userName和password查询数据库
        User user = userService.queryByUserNameAndPassword(loginInfo.getUsername(),loginInfo.getPassword());
        //判断是否有登录权限

        AssertUtil.isTrue(user == null||!"ROLE_admin".equals(user.getRole()),"无权限!");

        user.setPassword(null);

        String jwt = JwtUtil.generateToken("admin:" + user.getUserName());
        Map<String,Object> map = new HashMap<>();
        map.put("user",user);
        map.put("token",jwt);
        return ResultInfo.createResult("登录成功!",map);
    }
   /*@PostMapping("/login")
   public Result login(@RequestBody LoginInfo loginInfo) {
       User user = userService.queryByUserNameAndPassword(loginInfo.getUsername(), loginInfo.getPassword());
       if (!"ROLE_admin".equals(user.getRole())) {
           return Result.create(403, "无权限");
       }
       user.setPassword(null);
       String jwt = JwtUtil.generateToken("admin:" + user.getUserName());
       Map<String, Object> map = new HashMap<>();
       map.put("user", user);
       map.put("token", jwt);
       return Result.ok("登录成功", map);
   }*/

}
