package com.kaos.walnut.api.logic.service;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kaos.walnut.api.data.entity.KaosUserRole;
import com.kaos.walnut.api.data.mapper.KaosUserAccessMapper;
import com.kaos.walnut.api.data.mapper.KaosUserMapper;
import com.kaos.walnut.api.data.mapper.KaosUserRoleMapper;
import com.kaos.walnut.core.util.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
     * 登录检查
     * 
     * @param username
     * @param password
     */
    public List<String> signInCheck(String username, String password) {
        // 读取用户实体
        var user = this.kaosUserMapper.selectById(username);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        // 读取密码
        var access = this.kaosUserAccessMapper.selectById(username);
        if (access == null) {
            throw new RuntimeException("用户密码异常");
        }

        // 明文加密
        var cipher = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!StringUtils.equals(cipher, access.getPassword())) {
            throw new RuntimeException("密码错误");
        }

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
}
