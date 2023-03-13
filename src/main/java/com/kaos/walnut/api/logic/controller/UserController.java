package com.kaos.walnut.api.logic.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import com.kaos.walnut.api.logic.service.TokenService;
import com.kaos.walnut.api.logic.service.UserService;
import com.kaos.walnut.core.frame.entity.User;
import com.kaos.walnut.core.type.MediaType;
import com.kaos.walnut.core.type.annotations.ApiName;
import com.kaos.walnut.core.type.annotations.PassToken;
import com.kaos.walnut.core.util.ObjectUtils;
import com.kaos.walnut.core.util.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;

@Validated
@RestController
@RequestMapping("/api/user")
public class UserController {
    /**
     * 用户业务接口
     */
    @Autowired
    UserService userService;

    /**
     * 令牌业务接口
     */
    @Autowired
    TokenService tokenService;

    /**
     * 登陆系统
     * 
     * @param reqBody
     * @return
     */
    @ApiName("登陆")
    @PassToken
    @RequestMapping(value = "signIn", method = RequestMethod.POST, produces = MediaType.JSON)
    SignIn.RspBody signIn(@RequestBody @Valid SignIn.ReqBody reqBody) {
        // 构造响应
        var result = new SignIn.RspBody();
        result.setRoles(this.userService.signInCheck(reqBody.getUsername(), reqBody.getPassword()));
        result.setToken(this.tokenService.genToken(reqBody.getUsername()));
        return result;
    }

    static class SignIn {
        /**
         * 请求参数
         */
        @Data
        static class ReqBody {
            /**
             * 用户名
             */
            @NotBlank(message = "用户名不能为空")
            String username;

            /**
             * 密码
             */
            @NotBlank(message = "密码不能为空")
            String password;
        }

        /**
         * 响应参数
         */
        @Data
        static class RspBody {
            /**
             * 角色列表
             */
            List<String> roles;

            /**
             * 令牌
             */
            String token;
        }
    }

    /**
     * 登出系统
     */
    @ApiName("登出")
    @RequestMapping(value = "signOut", method = RequestMethod.POST, produces = MediaType.JSON)
    Object signOut() {
        // do nothing
        return ObjectUtils.EMPTY;
    }

    @ApiName("改密码")
    @RequestMapping(value = "changePassword", method = RequestMethod.POST, produces = MediaType.JSON)
    Object changePassword(@RequestBody @Valid ChangePassword.ReqBody reqBody) {
        // 同密码校验
        if (StringUtils.equals(reqBody.getOldPassword(), reqBody.getNewPassword())) {
            throw new RuntimeException("新密码与旧密码相同");
        }

        // 修改密码
        var username = User.currentUser().getUid();
        var oldPassword = reqBody.getOldPassword();
        var newPassword = reqBody.getNewPassword();
        this.userService.changePassword(username, oldPassword, newPassword);

        // 响应一个空对象
        return ObjectUtils.EMPTY;
    }

    static class ChangePassword {
        /**
         * 请求参数
         */
        @Data
        static class ReqBody {
            /**
             * 用户名
             */
            @NotBlank(message = "旧密码不能为空")
            String oldPassword;

            /**
             * 密码
             */
            @NotBlank(message = "新密码不能为空")
            String newPassword;
        }
    }
}
