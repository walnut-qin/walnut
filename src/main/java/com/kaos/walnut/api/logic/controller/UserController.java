package com.kaos.walnut.api.logic.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import com.kaos.walnut.core.type.MediaType;
import com.kaos.walnut.core.type.annotations.ApiName;
import com.kaos.walnut.core.type.annotations.PassToken;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/user")
public class UserController {
    /**
     * 登陆系统
     */
    @ApiName("登陆")
    @PassToken
    @RequestMapping(value = "signIn", method = RequestMethod.POST, produces = MediaType.JSON)
    SignIn.RspBody signIn(@RequestBody @Valid SignIn.ReqBody reqBody) {
        return null;
    }

    static class SignIn {
        /**
         * 请求参数
         */
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
        static class RspBody {
            /**
             * 令牌
             */
            String token;
        }
    }
}
