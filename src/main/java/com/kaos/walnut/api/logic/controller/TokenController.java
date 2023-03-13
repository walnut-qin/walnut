package com.kaos.walnut.api.logic.controller;

import javax.validation.Valid;

import com.kaos.walnut.api.logic.service.TokenService;
import com.kaos.walnut.core.frame.entity.User;
import com.kaos.walnut.core.type.MediaType;
import com.kaos.walnut.core.type.annotations.ApiName;
import com.kaos.walnut.core.type.annotations.PassToken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.Builder;
import lombok.Data;

@Validated
@RestController
@RequestMapping("/api/token")
public class TokenController {
    /**
     * token服务
     */
    @Autowired
    TokenService tokenService;

    /**
     * 校验token
     * 
     * @param reqBody
     * @return
     */
    @PassToken
    @ApiName("校验token")
    @RequestMapping(value = "check", method = RequestMethod.POST, produces = MediaType.JSON)
    Check.RspBody check(@RequestBody @Valid Check.ReqBody reqBody) throws Exception {
        // 调用服务
        var info = this.tokenService.parseToken(reqBody.getToken());
        if (info == null) {
            throw new RuntimeException("检查token失败");
        }

        // 构造响应
        var rspBuilder = Check.RspBody.builder();
        rspBuilder.user(info.getFirst());
        rspBuilder.token(info.getSecond());
        return rspBuilder.build();
    }

    /**
     * 实体
     */
    static class Check {
        /**
         * 请求body
         */
        @Data
        static class ReqBody {
            /**
             * 待校验token
             */
            String token;
        }

        /**
         * 响应body
         */
        @Builder
        static class RspBody {
            /**
             * 用户
             */
            User user;

            /**
             * 新token
             */
            String token;
        }
    }
}
