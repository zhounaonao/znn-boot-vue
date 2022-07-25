package com.znn.demo.dao;

import com.znn.demo.domain.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 周闹闹
 * @version 1.0
 */
@Mapper
public interface SysMenuDAO {

    /**
     * 根据用户userId获取权限集合 (已废弃)
     * @param userId
     * @return
     */
    @Deprecated
    List<String> getPerms(Long userId);

    /**
     * 根据用户userId获取权限集合
     * @param userId
     * @return
     */
    List<String> selectMenuPermsByUserId(Long userId);

    /**
     * 根据用户ID查询菜单
     *
     * @return 菜单列表
     */
    List<SysMenu> selectMenuTreeAll();

    /**
     * 根据用户ID查询菜单
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    List<SysMenu> selectMenuTreeByUserId(Long userId);


}
