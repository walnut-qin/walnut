package com.kaos.walnut.api.logic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kaos.walnut.api.logic.service.TokenService;
import com.kaos.walnut.core.frame.entity.User;
import com.kaos.walnut.core.type.MediaType;
import com.kaos.walnut.core.type.annotations.ApiName;

@Validated
@CrossOrigin
@RestController
@RequestMapping("/api/heart")
public class HeartController {
    /**
     * 令牌业务
     */
    @Autowired
    TokenService tokenService;

    /**
     * 校验token
     * 
     * @param reqBody
     * @return
     */
    @ApiName("心跳")
    @RequestMapping(value = "beat", method = RequestMethod.POST, produces = MediaType.JSON)
    Beat.RspBody beat() throws Exception {
        // 构造响应
        var result = new Beat.RspBody();
        result.token = this.tokenService.genToken(User.currentUser().getUid());
        return result;
    }

    static class Beat {
        static class RspBody {
            /**
             * 令牌
             */
            String token;
        }
    }
}
