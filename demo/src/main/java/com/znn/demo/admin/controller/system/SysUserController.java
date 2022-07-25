package com.znn.demo.admin.controller.system;

import com.znn.demo.domain.AjaxResult;
import com.znn.demo.domain.entity.SysUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 周闹闹
 * @version 1.0
 */
@RestController
@RequestMapping("/system/user")
public class SysUserController {


    /**
     * 获取用户列表
     */
    @GetMapping("list")
    public AjaxResult list(){



        return AjaxResult.success();
    }


}
