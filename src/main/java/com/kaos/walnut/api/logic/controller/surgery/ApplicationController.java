package com.kaos.walnut.api.logic.controller.surgery;

import java.util.Map;

import javax.validation.constraints.NotBlank;

import com.google.common.collect.Maps;
import com.kaos.walnut.api.data.mapper.MetOpsApplyMapper;
import com.kaos.walnut.core.type.MediaType;
import com.kaos.walnut.core.type.annotations.ApiName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/surgery/application")
public class ApplicationController {
    /**
     * 手术申请表接口
     */
    @Autowired
    MetOpsApplyMapper metOpsApplyMapper;

    @ApiName("获取手术申请信息")
    @RequestMapping(value = "getInfo", method = RequestMethod.GET, produces = MediaType.JSON)
    Map<String, Object> getInfo(@RequestParam @NotBlank(message = "申请单号不能为空") String applyNo) {
        // 检索数据库
        var application = this.metOpsApplyMapper.selectById(applyNo);
        if (application == null) {
            return null;
        }

        // 声明结果
        Map<String, Object> result = Maps.newHashMap();
        result.put("applyNo", application.getApplyNo());
        result.put("icuFlag", application.getIcuFlag());

        return result;
    }
}
