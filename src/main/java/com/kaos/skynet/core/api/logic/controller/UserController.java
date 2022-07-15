package com.kaos.skynet.core.api.logic.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import com.google.common.collect.Maps;
import com.kaos.skynet.core.api.data.entity.KaosUser;
import com.kaos.skynet.core.api.data.mapper.KaosUserMapper;
import com.kaos.skynet.core.api.data.mapper.KaosUserRoleMapper;
import com.kaos.skynet.core.api.logic.service.TokenService;
import com.kaos.skynet.core.type.MediaType;
import com.kaos.skynet.core.type.annotations.ApiName;
import com.kaos.skynet.core.type.annotations.PassToken;
import com.kaos.skynet.core.util.ObjectUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.Builder;
import lombok.Cleanup;
import net.coobird.thumbnailator.Thumbnails;

import java.awt.image.BufferedImage;

@Validated
@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class UserController {
    /**
     * 环境
     */
    @Autowired
    Environment environment;

    /**
     * 用户接口
     */
    @Autowired
    KaosUserMapper kaosUserMapper;

    /**
     * 用户角色
     */
    @Autowired
    KaosUserRoleMapper kaosUserRoleMapper;

    /**
     * token操作业务
     */
    @Autowired
    TokenService tokenService;

    /**
     * 展示系统缓存统计信息
     * 
     * @return
     */
    @PassToken
    @ApiName("登陆系统")
    @RequestMapping(value = "login", method = RequestMethod.POST, produces = MediaType.JSON)
    Login.RspBody login(@RequestBody @Valid Login.ReqBody reqBody) {
        // 校验并生成token
        String token = tokenService.genToken(reqBody.username, reqBody.password);

        // 生成响应
        var builder = Login.RspBody.builder();
        builder.token(token);
        return builder.build();
    }

    static class Login {
        static class ReqBody {
            /**
             * 用户ID
             */
            @NotBlank(message = "用户名不能为空")
            String username;

            /**
             * 用户密码
             */
            @NotBlank(message = "密码不能为空")
            String password;
        }

        @Builder
        static class RspBody {
            /**
             * 用户密码
             */
            String token;
        }
    }

    /**
     * 展示系统缓存统计信息
     * 
     * @return
     */
    @ApiName("获取用户信息")
    @RequestMapping(value = "getInfo", method = RequestMethod.GET, produces = MediaType.JSON)
    Info.RspBody getInfo() throws Exception {
        // 获取登入账号信息
        var kaosUser = KaosUser.getCurrentUser();

        // 检索角色信息
        Map<String, Object> key = Maps.newHashMap();
        key.put("USER_CODE", kaosUser.getUserCode());
        var kaosUserRoles = kaosUserRoleMapper.selectByMap(key);

        // 构造响应
        var rspBuilder = Info.RspBody.builder();
        rspBuilder.name(kaosUser.getUserName());
        rspBuilder.avatar(String.format("http://%s:%s/api/user/avatar?userCode=%s",
                Inet4Address.getLocalHost().getHostAddress(),
                environment.getProperty("local.server.port"),
                kaosUser.getUserCode()));
        rspBuilder.roles(kaosUserRoles.stream().map(x -> {
            return x.getRole();
        }).toList());
        return rspBuilder.build();
    }

    static class Info {
        @Builder
        static class RspBody {
            /**
             * 用户密码
             */
            String name;

            /**
             * 头像
             */
            String avatar;

            /**
             * 角色
             */
            List<String> roles;
        }
    }

    /**
     * 展示系统缓存统计信息
     * 
     * @return
     */
    @ApiName("注销")
    @RequestMapping(value = "logout", method = RequestMethod.POST, produces = MediaType.JSON)
    Object logout() {
        return ObjectUtils.EMPTY;
    }

    /**
     * 展示系统缓存统计信息
     * 
     * @return
     */
    @PassToken
    @ApiName("获取头像")
    @RequestMapping(value = "avatar", method = RequestMethod.GET, produces = MediaType.PNG)
    BufferedImage avatar(@NotBlank(message = "用户编码不能为空") String userCode) throws IOException {
        // 读取用户
        var kaosUser = kaosUserMapper.selectById(userCode);
        if (kaosUser == null || kaosUser.getAvatar() == null || kaosUser.getAvatar().length == 0) {
            return null;
        }

        // 创建读取流
        @Cleanup
        var imageStream = new ByteArrayInputStream(kaosUser.getAvatar());

        // 创建响应
        return Thumbnails.of(imageStream).scale(1.0f).asBufferedImage();
    }
}
