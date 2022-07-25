package com.znn.demo.dao.bo;



import com.znn.demo.domain.entity.SysUser;

import java.util.List;

/**
 * @author 周闹闹
 * @version 1.0
 */
public interface SysRoleBO {
    List<String> selectRolePermissionByUserId(SysUser sysUser);
}
