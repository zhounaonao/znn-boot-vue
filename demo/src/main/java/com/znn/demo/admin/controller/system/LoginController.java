package com.znn.demo.admin.controller.system;

import com.znn.demo.domain.AjaxResult;
import com.znn.demo.domain.model.LoginBody;
import com.znn.demo.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 周闹闹
 * @version 1.0
 */
@Profile("prod")
@RestController
public class LoginController {

    @Autowired
    LoginService loginService;

    /**
     * post的数据存在请求体中所以@RequestBody
     * @param loginBody
     * @return
     */
    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody){
        return loginService.login(loginBody);
    }

    @GetMapping("/logout")
    public String logout(){
        return "prod退出登录";
    }
}
