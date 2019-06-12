package com.lxm.elasticjob.controller;


import com.alibaba.fastjson.JSONObject;
import com.lxm.elasticjob.model.User;
import com.lxm.elasticjob.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("user")
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "queryById", method = RequestMethod.GET)
    @ResponseBody
    public String queryById(@RequestParam("id") Integer id) {
        User user = userService.queryById(id);
        return JSONObject.toJSONString(user);
    }
}
