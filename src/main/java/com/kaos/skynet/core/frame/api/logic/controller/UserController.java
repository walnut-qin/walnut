package com.kaos.skynet.core.frame.api.logic.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import com.kaos.skynet.core.type.MediaType;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Validated
@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class UserController {
    /**
     * 展示系统缓存统计信息
     * 
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.POST, produces = MediaType.JSON)
    String test(@RequestBody @Valid Tests v) {
        return "test";
        // throw new RuntimeException();
    }

    static class Tests {
        @NotBlank(message = "用户编码不能为空")
        String userCode;

        String password;
    }
}
