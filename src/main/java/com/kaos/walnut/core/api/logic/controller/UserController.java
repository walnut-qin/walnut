package com.kaos.walnut.core.api.logic.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import com.google.common.collect.Maps;
import com.kaos.walnut.core.api.data.cache.KaosUserCache;
import com.kaos.walnut.core.api.data.cache.KaosUserRoleCache;
import com.kaos.walnut.core.api.data.entity.KaosUser;
import com.kaos.walnut.core.api.logic.service.TokenService;
import com.kaos.walnut.core.tool.Environment;
import com.kaos.walnut.core.type.MediaType;
import com.kaos.walnut.core.type.annotations.ApiName;
import com.kaos.walnut.core.type.annotations.PassToken;
import com.kaos.walnut.core.util.ObjectUtils;

import org.springframework.beans.factory.annotation.Autowired;
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
    Environment env;

    /**
     * 用户接口
     */
    @Autowired
    KaosUserCache kaosUserCache;

    /**
     * 用户角色
     */
    @Autowired
    KaosUserRoleCache kaosUserRoleCache;

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
    @RequestMapping(value = "signIn", method = RequestMethod.POST, produces = MediaType.JSON)
    Login.RspBody signIn(@RequestBody @Valid Login.ReqBody reqBody) {
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
    Map<String, Object> getInfo() throws Exception {
        // 获取登入账号信息
        var kaosUser = KaosUser.getCurrentUser();

        // 检索角色信息
        var kaosUserRoles = kaosUserRoleCache.get(kaosUser.getUserCode());

        // 构造响应
        Map<String, Object> result = Maps.newHashMap();
        result.put("name", kaosUser.getUserName());
        result.put("avatar", env.getLocalAddress().concat("/api/user/getAvatar?user=").concat(kaosUser.getUserCode()));
        result.put("roles", kaosUserRoles.stream().map(x -> {
            return x.getRole();
        }).toList());
        return result;
    }

    /**
     * 展示系统缓存统计信息
     * 
     * @return
     */
    @ApiName("注销")
    @RequestMapping(value = "signOut", method = RequestMethod.POST, produces = MediaType.JSON)
    Object signOut() {
        return ObjectUtils.EMPTY;
    }

    /**
     * 展示系统缓存统计信息
     * 
     * @return
     */
    @PassToken
    @ApiName("获取头像")
    @RequestMapping(value = "getAvatar", method = RequestMethod.GET, produces = MediaType.PNG)
    BufferedImage getAvatar(@NotBlank(message = "用户编码不能为空") String user) throws IOException {
        // 读取用户
        var kaosUser = kaosUserCache.get(user);
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
