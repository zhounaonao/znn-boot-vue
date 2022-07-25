package com.znn.demo.dao.bo;

import com.znn.demo.domain.entity.SysMenu;
import com.znn.demo.domain.entity.SysUser;
import com.znn.demo.domain.entity.vo.RouterVo;

import java.util.List;
import java.util.Set;

/**
 * @author 周闹闹
 * @version 1.0
 */
public interface SysMenuBO {

    /**
     * 根据用户userId获取权限集合
     * @param sysUser
     * @return
     */
    Set<String> getPerms(SysUser sysUser);



    List<SysMenu> selectMenuTreeByUserId(SysUser sysUser);

    /**
     * 构建前端路由所需要的菜单
     *
     * @param menus 菜单列表
     * @return 路由列表
     */
    public List<RouterVo> buildMenus(List<SysMenu> menus);
}
