package com.znn.demo.admin.controller;

import com.znn.demo.domain.AjaxResult;
import com.znn.demo.domain.entity.SysUser;
import com.znn.demo.dao.bo.SysUserBO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author 周闹闹
 * @version 1.0
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    SysUserBO sysUserBO;

    @GetMapping({"/{userName}"})
//    @PreAuthorize("hasAuthority('system:user:list')")
    public AjaxResult getUserByUserName(@PathVariable String userName){
        Logger logger = LoggerFactory.getLogger(UserController.class);
        SysUser sysUser = sysUserBO.selectByUserName(userName);
        if (Objects.isNull(sysUser)){
            return AjaxResult.success(sysUser);
        }
        logger.info("user日志" + sysUser.toString());
        return AjaxResult.success(sysUser);
    }

    @GetMapping()
    @PreAuthorize("@ss.hasPermi('system:user:list')")
    public AjaxResult getAllPage(@RequestParam Integer pageNum,
                                 @RequestParam Integer pageSize){
        List<SysUser> sysUsers = sysUserBO.selectAll(pageNum, pageSize);
        Map<String, Object> map = new HashMap<>();
        map.put("table", sysUsers);
        map.put("total", sysUserBO.selectTotal());
        return AjaxResult.success(map);
    }
}
