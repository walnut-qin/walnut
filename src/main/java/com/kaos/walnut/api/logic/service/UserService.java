package com.kaos.walnut.api.logic.service;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.kaos.walnut.api.data.entity.KaosUserAccess;
import com.kaos.walnut.api.data.entity.KaosUserRole;
import com.kaos.walnut.api.data.mapper.KaosUserAccessMapper;
import com.kaos.walnut.api.data.mapper.KaosUserMapper;
import com.kaos.walnut.api.data.mapper.KaosUserRoleMapper;
import com.kaos.walnut.core.util.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

@Service
public class UserService {
    /**
     * 用户接口
     */
    @Autowired
    KaosUserMapper kaosUserMapper;

    /**
     * 用户密码接口
     */
    @Autowired
    KaosUserAccessMapper kaosUserAccessMapper;

    /**
     * 用户角色接口
     */
    @Autowired
    KaosUserRoleMapper kaosUserRoleMapper;

    /**
     * 校验密码
     * 
     * @param username
     * @param password
     */
    private void checkPassword(String username, String password) {
        // 读取密码
        var access = this.kaosUserAccessMapper.selectById(username);
        if (access == null) {
            throw new RuntimeException("用户密码异常");
        }

        // 明文加密
        var cipher = DigestUtils.md5DigestAsHex(password.getBytes()).toUpperCase();
        if (!StringUtils.equals(cipher, access.getPassword())) {
            throw new RuntimeException("密码错误");
        }
    }

    /**
     * 登录检查
     * 
     * @param username
     * @param password
     */
    @Transactional
    public List<String> signInCheck(String username, String password) {
        // 读取用户实体
        var user = this.kaosUserMapper.selectById(username);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        // 校验密码
        this.checkPassword(username, password);

        // 检索角色
        var wrapper = new QueryWrapper<KaosUserRole>().lambda();
        wrapper.eq(KaosUserRole::getUserCode, username);
        var roles = this.kaosUserRoleMapper.selectList(wrapper);
        if (roles == null) {
            return null;
        }

        // 转换为角色列表
        var roleStrings = roles.stream().map(x -> {
            return x.getRole();
        }).toList();

        return roleStrings;
    }

    /**
     * 修改密码
     * 
     * @param username
     * @param oldPassword
     * @param newPassword
     */
    @Transactional
    public void changePassword(String username, String oldPassword, String newPassword) {
        // 读取用户实体
        var user = this.kaosUserMapper.selectById(username);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        // 校验旧密码
        this.checkPassword(username, oldPassword);

        // 更新密码
        var cipher = DigestUtils.md5DigestAsHex(newPassword.getBytes()).toUpperCase();
        var wrapper = new UpdateWrapper<KaosUserAccess>().lambda();
        wrapper.set(KaosUserAccess::getPassword, cipher);
        wrapper.eq(KaosUserAccess::getUserCode, user.getUserCode());
        this.kaosUserAccessMapper.update(null, wrapper);
    }
}
