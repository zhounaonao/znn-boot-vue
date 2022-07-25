package com.znn.demo.admin.controller.system;

import com.znn.demo.common.utils.security.SecurityUtils;
import com.znn.demo.dao.bo.SysMenuBO;
import com.znn.demo.dao.bo.SysRoleBO;
import com.znn.demo.domain.AjaxResult;
import com.znn.demo.domain.entity.SysMenu;
import com.znn.demo.domain.entity.SysUser;
import com.znn.demo.domain.model.LoginBody;
import com.znn.demo.service.LoginService;
import com.znn.demo.dao.bo.SysUserBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author 周闹闹
 * @version 1.0
 */
@Profile("dev")
@RestController
public class LoginControllerDev {

    @Autowired
    LoginService loginService;

    @Autowired
    SysUserBO sysUserBO;

    @Autowired
    SysRoleBO sysRoleBO;

    @Autowired
    SysMenuBO sysMenuBO;

    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody){
        return loginService.login(loginBody);
    }

    @GetMapping("/getInfo")
    public AjaxResult getInfo(){
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        // 角色集合
        List<String> roles = sysRoleBO.selectRolePermissionByUserId(sysUser);
        // 权限集合
        Set<String> perms = sysMenuBO.getPerms(sysUser);
        AjaxResult ajaxResult = AjaxResult.success();
        ajaxResult.put("user", sysUser);
        ajaxResult.put("roles", roles);
        ajaxResult.put("permissions", perms);

        return ajaxResult;
    }

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("/getRouters")
    public AjaxResult getRouters(){
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        List<SysMenu> menus = sysMenuBO.selectMenuTreeByUserId(sysUser);
        return AjaxResult.success(sysMenuBO.buildMenus(menus));
    }

    @PostMapping("/logout")
    public AjaxResult logout(){
        return loginService.logout();
    }

    @PostMapping("/register")
    public AjaxResult register(@RequestBody SysUser sysUser){
        return loginService.register(sysUser);
    }

    @GetMapping({"/register/{userName}"})
    public AjaxResult getUserByUserName(@PathVariable String userName){
        SysUser sysUser = sysUserBO.selectByUserName(userName);
        if (Objects.isNull(sysUser)){
            return AjaxResult.success();
        }
        return AjaxResult.error();
    }

}
