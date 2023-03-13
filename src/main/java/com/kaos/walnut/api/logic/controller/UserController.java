package com.kaos.walnut.api.logic.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import com.kaos.walnut.api.logic.service.TokenService;
import com.kaos.walnut.api.logic.service.UserService;
import com.kaos.walnut.core.type.MediaType;
import com.kaos.walnut.core.type.annotations.ApiName;
import com.kaos.walnut.core.type.annotations.PassToken;

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
}
