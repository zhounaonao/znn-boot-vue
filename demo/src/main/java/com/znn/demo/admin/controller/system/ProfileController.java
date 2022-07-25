package com.znn.demo.admin.controller.system;

import com.znn.demo.common.constant.UserConstants;
import com.znn.demo.common.core.controller.BaseController;
import com.znn.demo.common.enums.BusinessType;
import com.znn.demo.common.exception.file.InvalidExtensionException;
import com.znn.demo.common.utils.file.FileUploadUtils;
import com.znn.demo.common.utils.file.MimeTypeUtils;
import com.znn.demo.common.utils.text.StringUtils;
import com.znn.demo.config.MainConfig;
import com.znn.demo.dao.bo.SysUserBO;
import com.znn.demo.domain.AjaxResult;
import com.znn.demo.domain.entity.SysUser;
import com.znn.demo.domain.model.LoginUser;
import com.znn.demo.framework.aspectj.annotation.Log;
import com.znn.demo.service.impl.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 周闹闹
 * @version 1.0
 * 个人信息 业务处理
 */
@RestController
@RequestMapping("/system/user/profile")
public class ProfileController extends BaseController {

    @Autowired
    SysUserBO sysUserBO;

    @Autowired
    TokenService tokenService;

    @Log(title = "获取信息")
    @GetMapping
    public AjaxResult profile() {
        LoginUser loginUser = getLoginUser();
        SysUser user = loginUser.getSysUser();
        AjaxResult ajaxResult = AjaxResult.success(user);
        ajaxResult.put("roleGroup", sysUserBO.selectUserRoleGroup(user.getUserName()));
        ajaxResult.put("postGroup", sysUserBO.selectUserPostGroup(user.getUserName()));

        return ajaxResult;
    }

    /**
     * 修改用户信息
     * @return
     */
    @Log(title = "修改用户", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult updateProfile(@RequestBody SysUser user) {
        LoginUser loginUser = getLoginUser();
        SysUser sysUser = loginUser.getSysUser();
        user.setUserName(sysUser.getUserName());
        // 电话号码非空，并且电话号码没有重复
        if (StringUtils.isNotEmpty(user.getPhonenumber())
                && UserConstants.NOT_UNIQUE.equals(sysUserBO.checkPhoneUnique(user))){
            return AjaxResult.error("修改用户'" + user.getUserName() + "'失败，手机号码已存在");
        }
        if (StringUtils.isNotEmpty(user.getEmail())
                && UserConstants.NOT_UNIQUE.equals(sysUserBO.checkEmailUnique(user))){
            return AjaxResult.error("修改用户'" + user.getUserName() + "'失败，电子邮件已存在");
        }
        user.setUserId(sysUser.getUserId());
        // mybatis中写了判断，当password为空时，不更新password
        user.setPassword(null);
        if (sysUserBO.updateUserProfile(user) > 0) {
            // 更新查询的缓存信息
            sysUserBO.updateCache(user.getUserName());
            // 更新用户缓存信息
            sysUser.setNickName(user.getNickName());
            sysUser.setPassword(user.getPassword());
            sysUser.setEmail(user.getEmail());
            sysUser.setSex(user.getSex());
            tokenService.setLoginUser(loginUser);
            return AjaxResult.success();
        }
        return AjaxResult.error("修改个人信息异常，请联系管理员");
    }

    @PostMapping("/avatar")
    public AjaxResult avatar(@RequestParam("avatarfile") MultipartFile file) throws Exception {
        if (file.isEmpty()){
            return AjaxResult.error("上传图片异常，请联系管理员");
        }
        LoginUser loginUser = getLoginUser();
        // 返回的是 /profile/avatar/年/月/日/blob_YYYYMMDDHHMMSSA001.jpeg
        String avatar = FileUploadUtils.upload(MainConfig.getAvatarPath(), file, MimeTypeUtils.IMAGE_EXTENSION);
        if (!sysUserBO.updateUserAvatar(loginUser.getUsername(), avatar)) {
            return AjaxResult.error("上传图片异常，请联系管理员");
        }
        sysUserBO.updateCache(loginUser.getUsername());
        AjaxResult ajaxResult = AjaxResult.success();
        ajaxResult.put("imgUrl", avatar);
        // 更新缓存用户头像
        loginUser.getSysUser().setAvatar(avatar);
        tokenService.setLoginUser(loginUser);
        return ajaxResult;
    }



}
