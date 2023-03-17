package com.kaos.walnut.api.logic.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kaos.walnut.core.type.MediaType;
import com.kaos.walnut.core.type.annotations.ApiName;
import com.kaos.walnut.core.util.ObjectUtils;

@Validated
@CrossOrigin
@RestController
@RequestMapping("/api/heart")
public class HeartController {
    /**
     * 校验token
     * 
     * @param reqBody
     * @return
     */
    @ApiName("心跳")
    @RequestMapping(value = "beat", method = RequestMethod.POST, produces = MediaType.JSON)
    Object check() throws Exception {
        return ObjectUtils.EMPTY;
    }
}
